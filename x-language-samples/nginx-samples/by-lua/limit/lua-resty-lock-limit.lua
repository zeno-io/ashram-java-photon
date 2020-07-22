-- 实验nginx： openresty http://openresty.org/cn/index.html

-------nginx.conf---------------
--lua_shared_dict locks 10m;
--lua_shared_dict limit_counter 10m;
--
--location /limit2 {
--    default_type text/html;
--    content_by_lua_file /home/svenaugustus/source/local/photon/x-language-samples/nginx-samples/by-lua/limit/lua-resty-lock-limit.lua ;
--}
----------------------

local locks = require "resty.lock"

local function acquire()
    local lock = locks:new("locks")
    local elapsed, err = lock:lock("limit_key") --互斥锁 保证原子特性
    local limit_counter = ngx.shared.limit_counter --计数器

    local key = "ip:" .. os.time()
    local limit = 5 --限流大小
    local current = limit_counter:get(key)

    if current ~= nil and current + 1 > limit then
        --如果超出限流大小
        lock:unlock()
        return 0
    end
    if current == nil then
        limit_counter:set(key, 1, 1) --第一次需要设置过期时间，设置key的值为1，过期时间为1秒
    else
        limit_counter:incr(key, 1) --第二次开始加1即可
    end
    lock:unlock()
    return 1
end

ngx.say(acquire())
