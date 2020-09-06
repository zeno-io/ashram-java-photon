- https://graphql.cn/learn/serving-over-http/

## URI 和路由

HTTP 通常与 REST 相关联，REST 使用“资源”作为其核心概念。
相比之下，GraphQL 的概念模型是一个实体图。
因此，GraphQL 中的实体无法通过 URL 识别。
相反，GraphQL 服务器在单个 URL /入口端点（通常是 `/graphql`）上运行，并且所有提供服务的 GraphQL 请求都应被导向此入口端点。

## GET
在收到一个 HTTP GET 请求时，应当在 “query” 查询字符串（query string）中指定 GraphQL 查询。例如，如果我们要执行以下 GraphQL 查询：

```graphql
{
  me {
    name
  }
}
```


此请求可以通过 HTTP GET 发送，如下所示：

```http
http://myapi/graphql?query={me{name}}
```

查询变量可以作为 JSON 编码的字符串发送到名为 `variables` 的附加查询参数中。

如果查询包含多个具名操作，则可以使用一个 `operationName` 查询参数来控制哪一个应当执行。

## POST
标准的 GraphQL POST 请求应当使用 application/json 内容类型（content type），并包含以下形式 JSON 编码的请求体：

```graphql
{
  "query": "...",
  "operationName": "...",
  "variables": { "myVariable": "someValue", ... }
}
```

`operationName` 和 `variables` 是可选字段。仅当查询中存在多个操作时才需要 operationName。

除了上边这种请求之外，我们还建议支持另外两种情况：
* 如果存在 “query” 这一查询字符串参数（如上面的 GET 示例中），则应当以与 HTTP GET 相同的方式进行解析和处理。
*  如果存在 “application/graphql” Content-Type 头，则将 HTTP POST 请求体内容视为 GraphQL 查询字符串。


## 响应

无论使用任何方法发送查询和变量，响应都应当以 JSON 格式在请求正文中返回。如规范中所述，查询结果可能会是一些数据和一些错误，并且应当用以下形式的 JSON 对象返回：

```json
{
  "data": { ... },
  "errors": [ ... ]
}
```
如果没有返回错误，响应中不应当出现 "errors" 字段。如果没有返回数据，则 根据 GraphQL 规范，只能在执行期间发生错误时才能包含 "data" 字段。




