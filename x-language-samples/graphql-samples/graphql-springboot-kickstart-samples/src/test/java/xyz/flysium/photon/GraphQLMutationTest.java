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
public class GraphQLMutationTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void hello() throws Exception {
        // POST
        final GraphQLResponse response = graphQLTestTemplate.postForResource("HelloMutation.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals("{\"data\":{\"hello\":\"Mutation hello world\"}}", response.getRawResponse().getBody());
    }

    @Test
    public void book() throws Exception {
        GraphQLResponse response = graphQLTestTemplate.postForResource("mutationCreateBook.graphqls");
        Assert.assertTrue(response.isOk());
        Assert
            .assertEquals("{\"data\":{\"createBook\":{\"id\":\"4\",\"name\":\"Thinking in Java\",\"pageCount\":108}}}",
                response.getRawResponse().getBody());

        response = graphQLTestTemplate.postForResource("mutationUpdateBook.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals(
            "{\"data\":{\"updateBook\":{\"id\":\"4\",\"name\":\"Thinking in Java Edition 4\",\"pageCount\":0}}}",
            response.getRawResponse().getBody());

        response = graphQLTestTemplate.postForResource("mutationRemoveBook.graphqls");
        Assert.assertTrue(response.isOk());
        Assert.assertEquals("{\"data\":{\"removeBook\":true}}", response.getRawResponse().getBody());
    }

}