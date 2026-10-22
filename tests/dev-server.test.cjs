const assert = require('node:assert/strict');
const { spawn } = require('node:child_process');
const { once } = require('node:events');
const http = require('node:http');
const { test } = require('node:test');

test('development server serves all extension bundles and proxies API requests', { timeout: 60000 }, async t => {
  const backend = http.createServer(async (req, res) => {
    let body = '';
    for await (const chunk of req) body += chunk;
    res.setHeader('content-type', 'application/json');
    res.end(JSON.stringify({ url: req.url, method: req.method, body }));
  });
  backend.listen(0, '127.0.0.1');
  await once(backend, 'listening');
  t.after(() => new Promise(resolve => backend.close(resolve)));

  // Reserve a free port before launching the child process.
  const reservation = http.createServer();
  reservation.listen(0, '127.0.0.1');
  await once(reservation, 'listening');
  const port = reservation.address().port;
  await new Promise(resolve => reservation.close(resolve));
  const child = spawn(process.execPath, ['scripts/start.js'], {
    env: { ...process.env, HOST: '127.0.0.1', PORT: String(port), PROXY_URL: `http://127.0.0.1:${backend.address().port}` },
    stdio: ['ignore', 'pipe', 'pipe']
  });
  let output = '';
  child.stdout.on('data', data => { output += data; });
  child.stderr.on('data', data => { output += data; });
  t.after(async () => {
    if (child.exitCode === null && child.signalCode === null) {
      const exited = once(child, 'exit');
      child.kill('SIGTERM');
      await exited;
    }
  });

  const base = `http://127.0.0.1:${port}`;
  let ready = false;
  for (let attempt = 0; attempt < 200; attempt++) {
    assert.equal(child.exitCode, null, output);
    try {
      const response = await fetch(`${base}/static/example/global_page.js`);
      if (response.ok && (await response.text()).includes('example/global_page')) {
        ready = true;
        break;
      }
    } catch {}
    await new Promise(resolve => setTimeout(resolve, 100));
  }
  assert.ok(ready, output);
  for (const page of ['project_page', 'portfolio_page', 'admin_page']) {
    const response = await fetch(`${base}/static/example/${page}.js`);
    assert.equal(response.status, 200, output);
    assert.ok((await response.text()).includes(`example/${page}`));
  }
  const response = await fetch(`${base}/api/example?branch=main`, { method: 'POST', body: 'example=42' });
  assert.deepEqual(await response.json(), { url: '/api/example?branch=main', method: 'POST', body: 'example=42' });
  const page = await fetch(`${base}/project/extension/example/project_page?id=example`);
  assert.equal((await page.json()).url, '/project/extension/example/project_page?id=example');
});
