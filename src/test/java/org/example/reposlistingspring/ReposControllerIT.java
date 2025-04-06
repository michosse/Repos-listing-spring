package org.example.reposlistingspring;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = ReposListingSpringApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "github.api.base=http://localhost:8086")
public class ReposControllerIT {
    @Autowired
    private MockMvc mockMvc;
    int wireMockPort = 8086;
    private WireMockServer wireMockServer;
    private final String exampleValidUser = "octocat";
    private final String notFoundUser = "notfounduser";

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(wireMockPort);
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.get("/users/"+ exampleValidUser +"/repos")
                .willReturn(WireMock.okJson("""
[
  {
    "name": "test",
    "owner": {
      "login": "michosse"
    },
    "fork": false
  }
]
""")));
        wireMockServer.stubFor(WireMock.get("/repos/"+exampleValidUser+"/test/branches")
                .willReturn(WireMock.okJson("""
[
  {
       "name": "test",
       "commit": {
         "sha": "f1eb94d5925d19290520a9b7f0000000a0916a4f"
       }
 }
]
""")));
        wireMockServer.stubFor(WireMock.get("/users/"+notFoundUser+"/repos")
                .willReturn(WireMock.aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
{
  "message": "Not Found",
  "documentation_url": "https://docs.github.com/rest/repos/repos#list-repositories-for-a-user",
  "status": "404"
}
                                """)));

    }
    @AfterEach
    public void teardown() {
        if(wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    public void testGetAllUserReposHappyPath() throws Exception {
        mockMvc.perform(get("/"+exampleValidUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userRepos", not(empty())))
                .andExpect(jsonPath("userRepos[0].branches", not(empty())));
    }

    @Test
    public void testGetUserReposNotFound() throws Exception {
        mockMvc.perform(get("/"+notFoundUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
