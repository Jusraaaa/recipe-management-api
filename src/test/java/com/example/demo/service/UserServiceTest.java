package com.example.demo.service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


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
    void create_shouldSaveAndReturn() {
        User input = new User("Jusra Ferati");
        User saved = userWithId(1L, "Jusra Ferati");

        when(userRepository.save(input)).thenReturn(saved);

        User result = userService.create(input);

        assertNotNull(result);
        assertEquals("Jusra Ferati", result.getFullName());
        assertEquals(1L, result.getId());
        verify(userRepository).save(input);
    }

    @Test
    void getAll_shouldReturnList() {
        when(userRepository.findAll())
                .thenReturn(List.of(
                        userWithId(1L, "A"),
                        userWithId(2L, "B")
                ));

        List<User> result = userService.getAll();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getById_shouldReturnUser_whenExists() {
        User user = userWithId(1L, "Jusra");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Jusra", result.getFullName());
        verify(userRepository).findById(1L);
    }

    @Test
    void getById_shouldThrow_whenNotExists() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getById(99L));

        verify(userRepository).findById(99L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void update_shouldChangeFullNameAndSave() {
        User existing = userWithId(1L, "Old Name");
        User updated = new User("New Name");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.update(1L, updated);

        assertEquals(1L, result.getId());
        assertEquals("New Name", result.getFullName());
        verify(userRepository).findById(1L);
        verify(userRepository).save(existing);
    }

    @Test
    void delete_shouldCallRepository() {
        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }
}
