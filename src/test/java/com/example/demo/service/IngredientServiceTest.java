package com.example.demo.service;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;
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
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    // helper: vendos id pa setId (reflection)
    private Ingredient ingredientWithId(Long id, String name) {
        Ingredient i = new Ingredient();
        i.setName(name);
        try {
            Field f = Ingredient.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(i, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    @Test
    void create_shouldSaveAndReturn() {
        Ingredient input = new Ingredient("Eggs");
        Ingredient saved = ingredientWithId(1L, "Eggs");

        when(ingredientRepository.save(input)).thenReturn(saved);

        Ingredient result = ingredientService.create(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Eggs", result.getName());
        verify(ingredientRepository).save(input);
    }

    @Test
    void getAll_shouldReturnList() {
        when(ingredientRepository.findAll())
                .thenReturn(List.of(
                        ingredientWithId(1L, "Salt"),
                        ingredientWithId(2L, "Sugar")
                ));

        List<Ingredient> result = ingredientService.getAll();

        assertEquals(2, result.size());
        verify(ingredientRepository).findAll();
    }

    @Test
    void getById_shouldReturnIngredient_whenExists() {
        Ingredient ing = ingredientWithId(1L, "Salt");
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ing));

        Ingredient result = ingredientService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Salt", result.getName());
        verify(ingredientRepository).findById(1L);
    }

    @Test
    void getById_shouldThrowRuntimeException_whenNotExists() {
        when(ingredientRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ingredientService.getById(99L));

        assertTrue(ex.getMessage().contains("id=99"));
        verify(ingredientRepository).findById(99L);
        verify(ingredientRepository, never()).save(any());
    }

    @Test
    void update_shouldChangeNameAndSave() {
        Ingredient existing = ingredientWithId(1L, "Old");
        Ingredient updated = new Ingredient("New");

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(inv -> inv.getArgument(0));

        Ingredient result = ingredientService.update(1L, updated);

        assertEquals(1L, result.getId());
        assertEquals("New", result.getName());
        verify(ingredientRepository).findById(1L);
        verify(ingredientRepository).save(existing);
    }

    @Test
    void delete_shouldCallRepository() {
        ingredientService.delete(1L);

        verify(ingredientRepository).deleteById(1L);
    }
}
