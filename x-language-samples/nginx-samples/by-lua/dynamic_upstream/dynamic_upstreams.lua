-- 实验nginx： openresty http://openresty.org/cn/index.html

----------------------

-- 需要nginx建 dict upstream_list
-- lua_shared_dict upstream_list 10m;
-- Consul Server 的 IP，端口
local consulServer = "127.0.0.1:8500"
-- Consul 服务名，对应 Spring Cloud 的配置项 spring.cloud.consul.discovery.service-name
local springCloudServiceName = "hello-service-provider"

local http = require "socket.http"
local ltn12 = require "ltn12"
local cjson = require "cjson"

local _M = {}
_M._VERSION = "0.1"

function _M:get_upstreams()
    local upstreams_str = ngx.shared.upstream_list:get(springCloudServiceName);
    if upstreams_str == nil then
        return nil
    end
    local tmp_upstreams = cjson.decode(upstreams_str);
    return tmp_upstreams;
end

function _M:set_upstreams(respString)
    if respString == nil then
        ngx.log(ngx.ERR, "respString is nil")
        return nil
    end
    local resp = cjson.decode(respString);

    local upstreams = {}
    for i, v in ipairs(resp) do
        upstreams[i] = { ip = v.Address, port = v.ServicePort }
    end

    local upstreamsString = cjson.encode(upstreams)

    ngx.log(ngx.ERR, "new upstreams of ", springCloudServiceName, " : ", upstreamsString)

    ngx.shared.upstream_list:set(springCloudServiceName, upstreamsString)

    return upstreamsString;
end

function _M:update_upstreams()
    local resp = {}
    local consul_url = 'http://' .. consulServer .. '/v1/catalog/service/' .. springCloudServiceName
    http.request {
        url = consul_url, sink = ltn12.sink.table(resp)
    }
    local resp = table.concat(resp);

    _M:set_upstreams(resp)
end

return _M
