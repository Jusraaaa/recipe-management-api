package com.example.demo.service;

import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.model.CategoryEntity;
import com.example.demo.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    // helper: vendos id pa setId (reflection)
    private CategoryEntity categoryWithId(Long id, String name) {
        CategoryEntity c = new CategoryEntity();
        c.setName(name);
        try {
            Field f = CategoryEntity.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(c, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    @Test
    void create_shouldSaveAndReturn() {
        CategoryEntity input = new CategoryEntity("Desserts");
        CategoryEntity saved = categoryWithId(1L, "Desserts");

        when(categoryRepository.save(input)).thenReturn(saved);

        CategoryEntity result = categoryService.create(input);

        assertNotNull(result);
        assertEquals("Desserts", result.getName());
        assertEquals(1L, result.getId());
        verify(categoryRepository).save(input);
    }

    @Test
    void getAll_shouldReturnList() {
        when(categoryRepository.findAll())
                .thenReturn(List.of(categoryWithId(1L, "A"), categoryWithId(2L, "B")));

        List<CategoryEntity> result = categoryService.getAll();

        assertEquals(2, result.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void getById_shouldReturnCategory_whenExists() {
        CategoryEntity cat = categoryWithId(1L, "Desserts");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));

        CategoryEntity result = categoryService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Desserts", result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getById_shouldThrow_whenNotExists() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getById(99L));

        verify(categoryRepository).findById(99L);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void update_shouldChangeNameAndSave() {
        CategoryEntity existing = categoryWithId(1L, "Old");
        CategoryEntity updated = new CategoryEntity("New");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(any(CategoryEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        CategoryEntity result = categoryService.update(1L, updated);

        assertEquals(1L, result.getId());
        assertEquals("New", result.getName());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(existing);
    }

    @Test
    void delete_shouldCallRepository() {
        categoryService.delete(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
