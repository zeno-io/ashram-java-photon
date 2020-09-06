/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
// for support servlet
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@GraphQLTest
public class GraphQLQueryTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void hello() throws Exception {
        final GraphQLResponse response = graphQLTestTemplate.postForResource("HelloQuery.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals("{\"data\":{\"hello\":\"Query hello world\"}}", response.getRawResponse().getBody());
    }

    @Test
    public void bookByIdQuery() throws Exception {
        final GraphQLResponse response = graphQLTestTemplate.postForResource("bookByIdQuery.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals(
            "{\"data\":{\"bookById\":{\"id\":\"1\",\"name\":\"Harry Potter and the Philosopher's Stone\",\"createdAt\":\"2020-09-01 00:00:00\",\"author\":{\"firstName\":\"Joanne\",\"lastName\":\"Rowling\"}}}}",
            response.getRawResponse().getBody());
    }

    @Test
    public void bookByInputQuery() throws Exception {
        final GraphQLResponse response = graphQLTestTemplate.postForResource("bookByInputQuery.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals(
            "{\"data\":{\"booksByInput\":[{\"id\":\"1\",\"name\":\"Harry Potter and the Philosopher's Stone\",\"pageCount\":223}]}}",
            response.getRawResponse().getBody());
    }

    // TODO 解决 N+1问题，服务器实现可以采用 DataLoader
    @Test
    public void books() throws Exception {
        final GraphQLResponse response = graphQLTestTemplate.postForResource("booksQuery.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals(
            "{\"data\":{\"books\":{\"edges\":[{\"cursor\":\"c2ltcGxlLWN1cnNvcjA=\",\"node\":{\"id\":\"1\",\"name\":\"Harry Potter and the Philosopher's Stone\",\"pageCount\":223,\"author\":{\"id\":\"1\",\"firstName\":\"Joanne\",\"lastName\":\"Rowling\"}}},{\"cursor\":\"c2ltcGxlLWN1cnNvcjE=\",\"node\":{\"id\":\"2\",\"name\":\"Moby Dick\",\"pageCount\":635,\"author\":{\"id\":\"2\",\"firstName\":\"Herman\",\"lastName\":\"Melville\"}}},{\"cursor\":\"c2ltcGxlLWN1cnNvcjI=\",\"node\":{\"id\":\"3\",\"name\":\"Interview with the vampire\",\"pageCount\":371,\"author\":{\"id\":\"3\",\"firstName\":\"Anne\",\"lastName\":\"Rice\"}}}],\"pageInfo\":{\"hasPreviousPage\":false,\"hasNextPage\":false}}}}",
            response.getRawResponse().getBody());
    }

}