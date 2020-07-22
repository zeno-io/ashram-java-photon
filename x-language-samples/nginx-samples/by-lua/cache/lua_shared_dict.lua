-- 实验nginx： openresty http://openresty.org/cn/index.html

-------nginx.conf---------------
-- lua_shared_dict my_shared_data 1m;
----------------------

local shared_data = ngx.shared.my_shared_data
local i = shared_data:get("i")

if not i then
    i = 1
    shared_data:set("i", i)
    ngx.say("lazy set i ", i, "<br/>")
end

i = shared_data:incr("i", 1)
ngx.say("i=", i, "<br/>")