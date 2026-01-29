package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired private ObjectMapper objectMapper;


    private User userWithId(Long id, String fullName) {
        User u = new User();
        u.setFullName(fullName);

        try {
            Field f = User.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(u, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return u;
    }

    @Test
    void create_shouldReturn201() throws Exception {
        User body = new User();
        body.setFullName("Jusra Ferati");

        Mockito.when(userService.create(any(User.class)))
                .thenReturn(userWithId(1L, "Jusra Ferati"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_invalidBody_shouldReturn400() throws Exception {
        // fullName mungon -> @NotBlank -> 400
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        Mockito.when(userService.getAll())
                .thenReturn(List.of(
                        userWithId(1L, "A"),
                        userWithId(2L, "B")
                ));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        Mockito.when(userService.getById(1L))
                .thenReturn(userWithId(1L, "Jusra"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturn200() throws Exception {
        User body = new User();
        body.setFullName("Updated Name");

        Mockito.when(userService.update(eq(1L), any(User.class)))
                .thenReturn(userWithId(1L, "Updated Name"));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).delete(1L);
    }
}
