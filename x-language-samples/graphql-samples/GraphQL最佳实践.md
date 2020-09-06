- https://graphql.cn/learn/best-practices/

# 分页

### 分页和边
- https://graphql.cn/learn/pagination/

我们有很多种方法来实现分页：
* 我们可以像这样 friends(first:2 offset:2) 来请求列表中接下来的两个结果。
* 我们可以像这样 friends(first:2 after:$friendId), 来请求我们上一次获取到的最后一个朋友之后的两个结果。
* 我们可以像这样 friends(first:2 after:$friendCursor), 从最后一项中获取一个游标并使用它来分页。

### 参考
- `Relay` : https://www.graphql-java-kickstart.com/tools/

# 服务器端的批处理与缓存 
- `DataLoader`： https://github.com/graphql-java/java-dataloader


# 标量类型扩展

- graphql-java-extended-scalars : https://github.com/graphql-java/graphql-java-extended-scalars


- graphql-java-datetime : https://github.com/donbeave/graphql-java-datetime

# 参数校验
- https://github.com/graphql-java/graphql-java-extended-validation

# Directives
- https://www.graphql-java-kickstart.com/tools/directives/

