package com.books.books.page;

import com.books.books.MVCservice.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ClientService.class, BookPagesController.class})
@WebMvcTest(BookPagesController.class)
public class BookPagesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(
            username = "admin",
            roles = {"admin"}
    )
    @Test
    public void testAuthenticated() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }
}
