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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * API 测试
 *
 * @author Sven Augustus
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc // 注入MockMvc
public class ApiTest {

  @Autowired
  private MockMvc mvc;

  @org.junit.Test
  public void invoke() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .post("/test")
        .content("{\n"
            + "      \"appId\": \"ctgd678efh567hg6787\",\n"
            + "      \"tenantId\": \"ctg_hn\",\n"
            + "      \"timestamp\": \"20200218145939\",\n"
            + "      \"transactionId\": \"1009660380201506130728806387\",\n"
            + "      \"nonceStr\": \"C380BEC2BFD727A4B6845133519F3AD6\",\n"
            + "      \"sign\": \"e2ea300c63d3451f889ebfdb7be24466ab1accd4b4c4f3ebee9b4e058d82c6bb\",\n"
            + "      \"signType\": \"SHA256\",\n"
            + "      \"userType\": \"staff\",\n"
            + "      \"userId\": \"100098\"\n"
            + "    }")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.appId").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.tenantId").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.nonceStr").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.sign").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").exists());
  }

  @org.junit.Test
  public void reverseInvoke() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .post("/notify")
        .content("{}")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.returnCode").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.returnMsg").exists());
  }

}