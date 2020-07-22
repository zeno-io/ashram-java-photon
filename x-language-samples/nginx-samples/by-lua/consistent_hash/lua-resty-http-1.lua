-- 实验nginx： openresty

--------安装--------------
-- https://github.com/ledgetech/lua-resty-http
--cp lua-resty-http/lib/resty/*.lua /usr/local/lib/nginx/openresty/lualib/resty

-------nginx.conf---------------
-- # nginx.conf http模块加入
-- resolver 8.8.8.8;
----------------------

local http = require "resty.http"
local httpc = http.new()

local resp, err = httpc:request_uri("http://www.sogou.com", {
    method = "GET",
    path = "/sogou?query=resty.http",
    headers = {
        ["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.36"
    }
})
if not resp then
    ngx.say("request error :", err)
    return
end

ngx.status = resp.status

for k, v in pairs(resp.headers) do
    if k ~= "Transfer-Encoding" and k ~= "Connection" then
        ngx.header[k] = v
    end
end
ngx.say(resp.body)

httpc:close()

