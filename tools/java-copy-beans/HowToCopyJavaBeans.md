
# How To Copy Java Beans ?

* 特性

| 类库 | 自动映射 | 手动映射 | 集合  | 复制对象 | 社区| 其他 |
| --- | --- | --- |--- |--- |--- |--- |
| Apache BeanUtils | √ | × |√ |  √ |   |  |
| Apache PropertyUtils  | √ | × |√ |  √   |  |  转换需要类型一致，否则抛错  |
| Spring BeanUtils | √ | 仅支持忽略 |√ | √   | | 类型不一致的会被忽略  |
| Cglib BeanCopier | √ | 支持Converter<br>不支持不同名的转换  | √ |  √   | | 转换需要类型一致，否则抛错，但可以通过自定义 Converter解决 | 
| Dozer / **DozerMapper**  | √ |  √, <br>通过xml或API  |  √ | |  Jul, 2020, 活跃度高 | 转换需要类型一致，否则抛错  |
| **Orika** | √ |  √,<br>可以自定义Mapper |  √ |  | Feb, 2019 |   |
| JMapper | √ | √  |  √ | | Apr, 2016, 活跃度低  |  |
| **MapStruct** | √| √, <br>并支持 多对一 映射 | √ |  | Sep, 2019, 活跃度中  |  |

推荐 **MapStruct**

* 性能

TODO

推荐 **MapStruct**

## Apache BeanUtils
Apache Common BeanUtils 的工具类： `org.apache.commons.beanutils.BeanUtils`


```xml
    <dependency>
      <groupId>commons-beanutils</groupId>
      <artifactId>commons-beanutils</artifactId>
      <version>1.9.4</version>
    </dependency>
```
```java
Car car = new Car("Morris", 5, CarType.SEDAN, new Date());
CarDto carDto = new CarDto();
org.apache.commons.beanutils.BeanUtils.copyProperties(carDto, car);
```

## Apache PropertyUtils
Apache Common BeanUtils 的工具类： `org.apache.commons.beanutils.PropertyUtils`

同 `Apache BeanUtils`
```java
Car car = new Car("Morris", 5, CarType.SEDAN, new Date());
CarDto carDto = new CarDto();
org.apache.commons.beanutils.PropertyUtils.copyProperties(carDto, car);
```

## Spring BeanUtils
Spring自带的工具类： `org.springframework.beans.BeanUtils`

```xml
<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>5.2.8.RELEASE</version>
</dependency>
```
```java
Car car = new Car("Morris", 5, CarType.SEDAN, new Date());
CarDto carDto = new CarDto();
// 可设置忽略属性
org.springframework.beans.BeanUtils.copyProperties(car, carDto, "email");
```

## Cglib BeanCopier

```xml
    <dependency>
      <groupId>cglib</groupId>
      <artifactId>cglib</artifactId>
      <version>3.3.0</version>
    </dependency>
```

```java
Car car = new Car("Morris", 5, CarType.SEDAN, new Date());
CarDto carDto = new CarDto();
org.springframework.cglib.beans.BeanCopier.create(Car.class, CarDto.class, true).copy(car, carDto, (o, aClass, o1) -> {
    if ("setType".equals(o1)) {
        if (o instanceof Enum) {
            return ((Enum<?>) o).name();
        }
    }
    return o;
});
```

## Dozer
Dozer 是一个映射框架，它使用递归将数据从一个对象复制到另一个对象。框架不仅能够在 bean 之间复制属性，还能够在不同类型之间自动转换。该框架允许不同的配置方式:基于 XML 或基于 API。

- 老版本：http://dozer.sourceforge.net/documentation/gettingstarted.html 

- https://github.com/DozerMapper/dozer

### 使用

```xml
<!-- 老版本 -->
<dependency>
    <groupId>net.sf.dozer</groupId>
    <artifactId>dozer</artifactId>
    <version>5.5.1</version>
</dependency>

 <dependency>
      <groupId>com.github.dozermapper</groupId>
      <artifactId>dozer-core</artifactId>
      <version>6.5.0</version>
    </dependency>
```

## Orika
Orika 是一个 bean 到 bean 的映射框架，它递归地将数据从一个对象复制到另一个对象。

Orika 的工作原理与 Dozer 相似。两者之间的主要区别是 Orika 使用字节码生成。这允许以最小的开销生成更快的映射器。

- https://orika-mapper.github.io/orika-docs/

### 使用

```xml
<dependency>
    <groupId>ma.glasnost.orika</groupId>
    <artifactId>orika-core</artifactId>
    <version>1.5.4</version>
</dependency>
```

## JMapper
JMapper 是一个映射框架，旨在提供易于使用的、高性能的 Java bean 之间的映射。该框架旨在使用注释和关系映射应用 DRY 原则。该框架允许不同的配置方式:基于注释、XML 或基于 API。
- https://github.com/jmapper-framework/jmapper-core/wiki

```xml
<dependency>
    <groupId>com.googlecode.jmapper-framework</groupId>
    <artifactId>jmapper-core</artifactId>
    <version>1.6.0.1</version>
</dependency>
```

## MapStruct 
- https://github.com/mapstruct/mapstruct.org 

### 使用

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.3.1.Final</version>
</dependency>
    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct-processor</artifactId>
      <version>1.3.1.Final</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.inject</groupId>
      <artifactId>javax.inject</artifactId>
      <version>1</version>
    </dependency>
```

### 详解

- `@Mapper` 只有在接口加上这个注解， MapStruct 才会去实现该接口
    @Mapper 里有个 `componentModel` 属性，主要是指定实现类的类型，一般用到两个
    - default：默认，可以通过 Mappers.getMapper(Class) 方式获取实例对象
    - spring：在接口的实现类上自动添加注解 @Component，可通过 @Autowired 方式注入
- `@Mapping`：属性映射，若源对象属性与目标对象名字一致，会自动映射对应属性
    - `source`：源属性
    - `target`：目标属性
    - `dateFormat`：String 到 Date 日期之间相互转换，通过 SimpleDateFormat，该值为 SimpleDateFormat              的日期格式
    - `ignore`: 忽略这个字段
- `@Mappings`：配置多个@Mapping
- `@MappingTarget` 用于更新已有对象
- `@InheritConfiguration` 用于继承配置

