-- 实验nginx： openresty http://openresty.org/cn/index.html

--------安装--------------
-- https://github.com/bungle/lua-resty-template
--cp lua-resty-template/lib/resty/template.lua /usr/local/lib/nginx/openresty/lualib/resty
--cp -r lua-resty-template/lib/resty/template /usr/local/lib/nginx/openresty/lualib/resty

----------------------

local template = require("resty.template")
local html = require "resty.template.html"
template.caching(false)
template.render([[
<ul>
{% for _, person in ipairs(context) do %}
    {*html.li(person.name)*} --
{% end %}
</ul>
<table border=1>
{% for _, person in ipairs(context) do %}
    <tr data-sort="{{(person.name or ""):lower()}}">
        {*html.td{ id = person.id }(person.name)*}
	<td>{*(person.age)*}</td>
    </tr>
{% end %}
</table>]], {
    { id = 1, name = "Emma", age = 18 },
    { id = 2, name = "James", age = 29 },
    { id = 3, name = "Nicholas", age = 24 },
    { id = 4, age = 17 }
})
