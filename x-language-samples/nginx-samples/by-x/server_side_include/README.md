nginx.conf:
```nginx
# 均可以放在http,server和location的作用域下。
ssi on;
# 默认值是off，开启后在处理SSI文件出错时不输出错误提示:"[an error occurred while processing the directive] "
ssi_silent_errors on; 
# 默认是ssi_types text/html，所以如果需要htm和html支持，则不需要设置这句，如果需要shtml支持，则需要设置：ssi_types text/shtml
ssi_types text/html;

location /ssi {
     alias /home/svenaugustus/source/local/photon/x-language-samples/nginx-samples/by-x/server_side_include  ;   
}
```

