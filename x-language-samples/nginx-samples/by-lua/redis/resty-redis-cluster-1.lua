-- 实验nginx： openresty http://openresty.org/cn/index.html

--------安装--------------
--https://github.com/steve0511/resty-redis-cluster
--cp resty-redis-cluster/lib/resty/rediscluster.lua /usr/local/lib/nginx/openresty/lualib/
--cp resty-redis-cluster/lib/resty/xmodem.lua /usr/local/lib/nginx/openresty/lualib/resty

-------nginx.conf---------------
--# dict_name： redis_cluster_slot_locks
--lua_shared_dict redis_cluster_slot_locks 100k;
----------------------

local config = {
    dict_name = "redis_cluster_slot_locks", --shared dictionary name for locks
    name = "testCluster", --rediscluster name
    serv_list = {                           --redis cluster node list(host and port),
        { ip = "127.0.0.1", port = 7001 },
        { ip = "127.0.0.1", port = 7002 },
        { ip = "127.0.0.1", port = 7003 }
    },
    keepalive_timeout = 60000, --redis connection pool idle timeout
    keepalive_cons = 1000, --redis connection pool size
    connection_timeout = 1000, --timeout while connecting
    max_redirection = 5, --maximum retry attempts for redirection
    max_connection_attempts = 1             --maximum retry attempts for connection
}

local redis_cluster = require "rediscluster"
local red_c = redis_cluster:new(config)

local v, err = red_c:get("a")

if v == ngx.null then
    ngx.say("a not found.")
else
    if not v or err then
        ngx.say("failed to get xxx: ", err)
    else
        ngx.say("val: ", v)
    end
end
