-- 实验nginx： openresty http://openresty.org/cn/index.html
----------------------

-- ngx.say('<p>hello, lua!</p>')
-- if ngx.var.arg_a ~=nil then
--	ngx.say('hello,'..ngx.var.arg_a)
-- else
--	ngx.say('hello, lua!')
-- end

local uri_args = ngx.req.get_uri_args()
local headers = ngx.req.get_headers()

for k, v in pairs(uri_args) do
    if type(v) == "table" then
        ngx.say(k, " : ", table.concat(v, ", "), "<br/>")
    else
        ngx.say(k, ": ", v, "<br/>")
    end
end

ngx.say('--------<br/>')

for k, v in pairs(headers) do
    if type(v) == "table" then
        ngx.say(k, " : ", table.concat(v, ","), "<br/>")
    else
        ngx.say(k, " : ", v, "<br/>")
    end
end

