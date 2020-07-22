ngx.req.read_body()
local ups = ngx.req.get_body_data()

if ups == nil then
    ngx.say("usage: POST /_ups, json body from consul")
    ngx.exit(ngx.HTTP_BAD_REQUEST)
    return
end

if ups == nil then
    ngx.say("usage: POST /_ups, json body from consul")
    return
end

local dynamic_upstreams = require "dynamic_upstreams";
local newUpstreamString = dynamic_upstreams:set_upstreams(ups);
ngx.say("new upstreams: ", newUpstreamString)

