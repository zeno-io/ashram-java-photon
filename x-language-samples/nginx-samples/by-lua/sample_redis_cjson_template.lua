-- 实验nginx： openresty http://openresty.org/cn/index.html

--------安装--------------
-- https://github.com/bungle/lua-resty-template
--cp lua-resty-template/lib/resty/template.lua /usr/local/lib/nginx/openresty/lualib/resty
--cp -r lua-resty-template/lib/resty/template /usr/local/lib/nginx/openresty/lualib/resty

-------nginx.conf---------------
--  set $template_root /home/svenaugustus/source/local/photon/x-language-samples/nginx-samples/by-lua/templates;
--  location /lua {
--	 default_type text/html;
--	 content_by_lua_file /home/svenaugustus/source/local/photon/x-language-samples/nginx-samples/by-lua/sample_redis_cjson_template.lua ;
--  }

----------------------
--redis 中 a的数据：
--[{"id":"1","name":"Emma","age":"19"},{"id":"2","name":"James","age":"29"},{"id":"3","name":"Nicholas","age":"24"},{"id":"4","age":"17"}]
----------------------


local lrucache = require "resty.lrucache"
local redis = require "resty.redis"
local cjson = require("cjson")
local template = require("resty.template")
local html = require "resty.template.html"
--创建lru缓存实例，并指定最多缓存多少条目
local c, err = lrucache.new(200)
if not c then
    ngx.log(ngx.ERR, "create cache error : ", err)
end
-- redis实例
red, err = redis.new()
-- template设置是否缓存
template.caching(true)

-- 定义redis读取函数
function redisGet(key)
    local ok, err = red:connect("127.0.0.1", 6379)
    if not ok then
        ngx.say("failed to connect: ", err)
        return nil
    end

    local v, err = red:get(key)
    if v == ngx.null then
        ngx.say(key .. "not found.")
    else
        if not v or err then
            ngx.say("failed to get " .. key .. ": ", err)
        else
            return v
        end
    end
    return nil
end

-- 从redis读取数据，并解json为对象
local str = redisGet("a")
local obj = {}
if str then
    obj = cjson.decode(str)
end

-- 模板渲染
template.render("table.html", obj)


