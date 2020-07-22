-- 实验nginx： openresty http://openresty.org/cn/index.html

--------安装--------------
-- https://github.com/bungle/lua-resty-template
--cp lua-resty-template/lib/resty/template.lua /usr/local/lib/nginx/openresty/lualib/resty
--cp -r lua-resty-template/lib/resty/template /usr/local/lib/nginx/openresty/lualib/resty

-------nginx.conf---------------
--  set $template_root /home/svenaugustus/source/local/photon/x-language-samples/nginx-samples/by-lua/templates;
--  location /lua {
--	 default_type text/html;
--	 content_by_lua_file /home/svenaugustus/source/local/photon/x-language-samples/nginx-samples/by-lua/resty_template/lua-resty-template-2-render.lua ;
--  }
----------------------

local template = require "resty.template"
-- Using template.render  
template.render("body.html", { message = "Hello, World! template.render" })

