-- 实验nginx： openresty http://openresty.org/cn/index.html
----------------------

local redis = require "resty.redis"
red, err = redis.new()
local ok, err = red:connect("127.0.0.1", 6379)
if not ok then
    ngx.say("failed to connect: ", err)
    return
end

local v, err = red:get("a")
if v == ngx.null then
    ngx.say("a not found.")
else
    if not v or err then
        ngx.say("failed to get xxx: ", err)
    else
        ngx.say("val: ", v)
    end
end
