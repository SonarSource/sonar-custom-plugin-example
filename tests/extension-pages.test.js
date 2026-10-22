const fs = require('node:fs');
const path = require('node:path');
const React = require('react');
const ReactDOM = require('react-dom');

let extensions;
let root;

beforeEach(() => {
  extensions = {};
  document.body.innerHTML = '<div id="root"></div>';
  root = document.getElementById('root');
  window.React = React;
  window.ReactDOM = ReactDOM;
  window.registerExtension = (name, callback) => { extensions[name] = callback; };
  window.alert = jest.fn();
  window.SonarRequest = { getJSON: jest.fn() };
});

afterEach(() => {
  ReactDOM.unmountComponentAtNode(root);
  document.body.innerHTML = '';
});

function load(page) {
  // Exercise the shipped production bundle, including loaders and externals.
  window.eval(fs.readFileSync(path.join(__dirname, `../target/classes/static/${page}.js`), 'utf8'));
  expect(extensions[`example/${page}`]).toEqual(expect.any(Function));
  return extensions[`example/${page}`];
}

test('vanilla page registers, handles a click and cleans up on shutdown', () => {
  const stop = load('global_page')({ el: root });
  const button = root.querySelector('button');
  button.click();
  expect(window.alert).toHaveBeenCalledWith('Told you so');
  stop();
  expect(root.innerHTML).toBe('');
  window.alert.mockClear();
  button.click();
  expect(window.alert).not.toHaveBeenCalled();
});

test.each([{ name: 'feature/example' }, { key: '123' }])('Backbone page renders branch/PR context and removes delegated events: %j', branchLike => {
  const stop = load('project_page')({ el: root, branchLike });
  expect(root.textContent).toContain(branchLike.name || branchLike.key);
  const button = root.querySelector('button');
  button.click();
  expect(window.alert).toHaveBeenCalledWith('Gotcha');
  stop();
  expect(root.querySelector('button')).toBeNull();
  window.alert.mockClear();
  button.click();
  expect(window.alert).not.toHaveBeenCalled();
});

test('admin page uses host React and renders API statistics after loading', async () => {
  const responses = {
    '/api/qualityprofiles/search': { profiles: [{}, {}] },
    '/api/qualitygates/list': { qualitygates: [{}] },
    '/api/issues/search': { total: 42 },
    '/api/projects/search': { components: [{}, {}, {}] }
  };
  window.SonarRequest.getJSON.mockImplementation(url => Promise.resolve(responses[url]));
  ReactDOM.render(load('admin_page')(), root);
  expect(root.textContent).toContain('Loading...');
  await new Promise(resolve => setTimeout(resolve, 0));
  const rows = Array.from(root.querySelectorAll('td'), node => node.textContent);
  expect(rows).toEqual([
    'example.admin_page.we_have_x_y.2.example.admin_page.quality_profiles',
    'example.admin_page.we_have_x_y.1.example.admin_page.quality_gates',
    'example.admin_page.we_have_x_y.42.example.admin_page.issues',
    'example.admin_page.we_have_x_y.3.example.admin_page.projects'
  ]);
});

test('portfolio page requests project history and renders metric values and ratings', async () => {
  const date = '2026-10-22T10:00:00+0000';
  const metrics = { alert_status: 'ERROR', bugs: '7', vulnerabilities: '2', sqale_index: '45', reliability_rating: '3', security_rating: '2', sqale_rating: '1' };
  window.SonarRequest.getJSON.mockImplementation(url => Promise.resolve(
    url === '/api/project_analyses/search'
      ? { analyses: [{ date }] }
      : { measures: Object.entries(metrics).map(([metric, value]) => ({ metric, history: [{ date, value }] })) }
  ));
  ReactDOM.render(load('portfolio_page')({ component: { key: 'my-project' } }), root);
  await new Promise(resolve => setTimeout(resolve, 0));
  expect(window.SonarRequest.getJSON).toHaveBeenCalledWith('/api/project_analyses/search', { project: 'my-project', p: 1, ps: 500 });
  expect(window.SonarRequest.getJSON).toHaveBeenCalledWith('/api/measures/search_history', expect.objectContaining({ component: 'my-project' }));
  expect(Array.from(root.querySelectorAll('tbody td'), node => node.textContent.trim())).toEqual(['ERROR', '7', 'C', '2', 'B', '45', 'A']);
});
