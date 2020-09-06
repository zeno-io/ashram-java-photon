/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class HelloGraphQL {

    public static void main(String[] args) {
        // 1. 定义Schema, 一般会定义在一个schema文件中
        String schema = "type Query{hello: String}";
        // 2. 解析Schema
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);
        // 为Schema 中hello方法绑定获取数据的方法
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            // 这里绑定的是最简单的静态数据数据获取器, 正常使用时,获取数据的方法返回一个DataFetcher实现即可
            .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
            .build();
        // 将TypeDefinitionRegistry与RuntimeWiring结合起来生成GraphQLSchema
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator
            .makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        // 实例化GraphQL, GraphQL为执行GraphQL语言的入口
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        // 执行查询
        ExecutionResult executionResult = graphQL.execute("{hello}");
        // 打印执行结果
        System.out.println(executionResult.getData().toString());
    }
}
