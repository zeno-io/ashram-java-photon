
### nginx.conf

```nginx
    # 第一次客户端连接初始化
    init_by_lua_block {
        # ngx.log(ngx.ERR, "init pre client.....")  
        local dynamic_upstreams = require "dynamic_upstreams";
        local tmp_upstreams = dynamic_upstreams:get_upstreams();
        if tmp_upstreams ~= nil then
           return; 
        end
        ngx.log(ngx.ERR, "init first by pulling from consul.....") 
        dynamic_upstreams:update_upstreams();
    }

    # 定时拉取配置
    init_worker_by_lua_block {
        local dynamic_upstreams = require "dynamic_upstreams";
        local handle = nil;


        handle = function ()
            -- TODO:控制每次只有一个worker执行
            dynamic_upstreams:update_upstreams();
            ngx.timer.at(5, handle);
        end
        ngx.timer.at(5, handle);
    }
   upstream hello_service_providers {
       server 0.0.0.1 down; #占位server


       balancer_by_lua_block {
            local balancer = require "ngx.balancer";
            local dynamic_upstreams = require "dynamic_upstreams";    
            local tmp_upstreams = dynamic_upstreams:get_upstreams();
            local ip_port = tmp_upstreams[math.random(1, table.getn(tmp_upstreams))];
            balancer.set_current_peer(ip_port.ip, ip_port.port);
        }
     }


server {
    listen      80;
    server_name  localhost;
    charset utf-8;

    # 提供更新 upstream 
    location = /_ups {
        allow 127.0.0.1;
        allow 192.168.2.0/24;
        deny all;
        default_type text/html;
        content_by_lua_file /usr/local/lib/nginx/by-lua/dynamic_upstream/update_upstreams.lua;
    }
    
    
    location /hello/ {
        proxy_set_header Host $host;
        proxy_pass http://hello_service_providers/ ;
    }
}
```

### 待完善
* init_worker_by_lua是每个Nginx Worker进程都会执行的代码，所以实际实现时可考虑使用锁机制，保证一次只有一个人处理配置拉取。
* 主动推送方式: 另外ngx.timer.at是定时轮询，不是走的长轮询，有一定的时延。有个解决方案，是在Nginx上暴露HTTP API （`/_ups`），通过主动推送的方式解决。
  * 注意限制 IP , 以保证安全
  
### 更完善的方案
Consul + Nginx + 微博的 Upsync （https://github.com/weibocom/nginx-upsync-module）



