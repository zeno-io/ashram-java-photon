-- 实验nginx： openresty http://openresty.org/cn/index.html

--------安装--------------
-- https://github.com/bungle/lua-resty-template
--cp lua-resty-template/lib/resty/template.lua /usr/local/lib/nginx/openresty/lualib/resty
--cp -r lua-resty-template/lib/resty/template /usr/local/lib/nginx/openresty/lualib/resty

----------------------

local template = require "resty.template"
local func = template.compile("body.html")
local content = func(
        { message = "Hello, World! template compile " }
)
ngx.say("xx:", content)
