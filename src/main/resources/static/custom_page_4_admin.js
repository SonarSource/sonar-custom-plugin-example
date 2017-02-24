window.registerExtension('example/custom_page_4_admin', function (options) {
  options.el.textContent = 'This is my page only for Admins!';
  return function () {
    options.el.textContent = '';
  };
});