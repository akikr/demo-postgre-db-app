package io.akikr.demopostgredbapp.bookmark;

import io.akikr.demopostgredbapp.PostgreTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD)
class BookmarkControllerIntegrationTest extends PostgreTestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllBookmarks() throws Exception {
        mockMvc.perform(get("/v1/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());

        mockMvc.perform(get("/v1/bookmarks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetBookmarkById() throws Exception {
        long id = 1L;
        mockMvc.perform(get("/v1/bookmarks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    void testCreateBookmark() throws Exception {
        mockMvc.perform(post("/v1/bookmarks")
                        .contentType("application/json")
                        .content("""
                        {
                          "title":"Test",
                          "url":"https://test.com"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void testUpdateBookmark() throws Exception {
        mockMvc.perform(put("/v1/bookmarks")
                        .contentType("application/json")
                        .content("""
                        {
                          "id":"1",
                          "title":"Test3",
                          "url":"https://test3.com"
                        }
                        """))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void testDeleteBookmark() throws Exception {
        long id = 1L; // Assuming this ID exists in the test data
        mockMvc.perform(delete("/v1/bookmarks/" + id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }
}
