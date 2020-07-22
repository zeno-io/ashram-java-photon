-- 实验nginx： openresty http://openresty.org/cn/index.html
----------------------

local lrucache = require "resty.lrucache"
local c, err = lrucache.new(200)

c:set("dog", 32)
c:set("cat", 56)
local dog, err1 = c:get("dog")
local cat, err2 = c:get("cat")

ngx.say("dog : ", dog)
ngx.say("cat : ", cat)

c:set("dog", { age = 10 }, 0.1)    -- expire in 0.1 sec
c:delete("dog")

