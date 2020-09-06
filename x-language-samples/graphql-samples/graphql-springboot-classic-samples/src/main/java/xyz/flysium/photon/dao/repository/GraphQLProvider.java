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

package xyz.flysium.photon.dao.repository;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
public class GraphQLProvider {

  @Autowired
  private GraphQLDataFetchers graphQLDataFetchers;

  private GraphQL graphQL;

  @Value("${application.graphql.schema}")
  private String schema;
  @Autowired
  private ResourceLoader resourceLoader;

  @PostConstruct
  public void init() throws IOException {
    final Resource resource = resourceLoader.getResource(schema);
    String sdl = null;
    try (InputStream inputStream = resource.getInputStream()) {
      sdl = FileCopyUtils.copyToString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
    GraphQLSchema graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    RuntimeWiring runtimeWiring = buildWiring();
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
        // 仅仅是体验Mutation这个功能,返回一个字符串
        .type("Mutation",
            builder -> builder.dataFetcher("hello", new StaticDataFetcher("Mutation hello world")))
        // 返回字符串
        .type("Query",
            builder -> builder.dataFetcher("hello", new StaticDataFetcher("Query hello world")))
        // 通过id查询book
        .type(TypeRuntimeWiring.newTypeWiring("Query")
            .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
        // 查询book中的author信息
        .type(
            TypeRuntimeWiring.newTypeWiring("Book")
                .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
        // 通过参数查询 Book
        .type(TypeRuntimeWiring.newTypeWiring("Query")
            .dataFetcher("booksByInput", graphQLDataFetchers.getBooksDataFetcher()))
        .build();
  }

  // 执行GraphQL语言的入口
  @Bean
  public GraphQL graphQL() {
    return graphQL;
  }
}