-- 实验nginx： openresty http://openresty.org/cn/index.html

--------安装--------------
-- https://github.com/bungle/lua-resty-template
--cp lua-resty-template/lib/resty/template.lua /usr/local/lib/nginx/openresty/lualib/resty
--cp -r lua-resty-template/lib/resty/template /usr/local/lib/nginx/openresty/lualib/resty

----------------------

local template = require "resty.template"
-- Using template string
template.render([[
<!DOCTYPE html>
<html>
<body>
  <h1>{{message}}</h1>
</body>
</html>]], { message = "Hello, World! template string " })

