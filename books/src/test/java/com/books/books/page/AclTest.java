package com.books.books.page;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AclTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user1")
    public void user1ShouldGetBook1() throws Exception {
        mockMvc.perform(
                get("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1")
    public void user1ShouldNotGetBook4() throws Exception {
        mockMvc.perform(
                get("/api/books/4"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user2")
    public void user2ShouldGetBook4() throws Exception {
        mockMvc.perform(
                get("/api/books/4"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    public void adminShouldGetAllBooks() throws Exception {
        mockMvc.perform(
                get("/api/books"))
                .andExpect(status().isOk());
    }
}
