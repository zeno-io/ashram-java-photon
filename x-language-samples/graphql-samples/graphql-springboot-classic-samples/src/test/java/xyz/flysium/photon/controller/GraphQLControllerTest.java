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

package xyz.flysium.photon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphQLControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  public static final String URL_TEMPLATE = "/graphql";
  public static final String APPLICATION_GRAPHQL = "application/graphql";

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  public Map<?, ?> readMap(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, Map.class);
  }

  @Test
  public void graphql1() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
        .contentType(APPLICATION_GRAPHQL)
        // 请求body
        .content("mutation{hello}")
    )
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.request()
            // 响应
            .asyncResult(readMap("{\"data\":{\"hello\":\"Mutation hello world\"}}"))
        );
  }


  @Test
  public void graphql2() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
        .contentType(APPLICATION_GRAPHQL)
        // 请求body
        .content("{hello}")
    )
        .andExpect(MockMvcResultMatchers.status()
            .isOk())
        .andExpect(MockMvcResultMatchers.request()
            // 响应
            .asyncResult(readMap("{\"data\":{\"hello\":\"Query hello world\"}}"))
        );
  }

  @Test
  public void graphql3() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
        .contentType(APPLICATION_GRAPHQL)
        // 请求body
        .content("{\n"
            + "  bookById(id : \"book-1\"){\n"
            + "     id\n"
            + "     name\n"
            + "     author{\n"
            + "       firstName\n"
            + "       lastName\n"
            + "     }"
            + "  }\n"
            + "}")
    )
        .andExpect(MockMvcResultMatchers.status()
            .isOk()
        ).andExpect(MockMvcResultMatchers.request()
        // 响应
        .asyncResult(
            readMap(
                "{\"data\":{\"bookById\":{\"id\":\"book-1\",\"name\":\"Harry Potter and the Philosopher's Stone\",\"author\":{\"firstName\":\"Joanne\",\"lastName\":\"Rowling\"}}}}"))
    );

  }

  @Test
  public void graphql4() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE)
        .contentType(APPLICATION_GRAPHQL)
        // 请求body
        .content("{\n"
            + "  booksByInput(book: {name: \"Harry\"}){"
            + "    id"
            + "    name"
            + "    pageCount"
            + "  }"
            + "}")
    )
        .andExpect(MockMvcResultMatchers.status()
            .isOk()
        ).andExpect(MockMvcResultMatchers.request()
        // 响应
        .asyncResult(
            readMap(
                "{\"data\":{\"booksByInput\":[{\"id\":\"book-1\",\"name\":\"Harry Potter and the Philosopher's Stone\",\"pageCount\":223}]}}"))
    );
  }
}