package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    private Item item;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        item = new Item();
        item.setId(1L);
        item.setName("Widget");
        item.setPrice(BigDecimal.TEN);
        item.setDescription("Test item");
    }

    @Test
    void testGetItems() {
        when(itemRepository.findAll()).thenReturn(List.of(item));

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetItemById_found() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(item, response.getBody());
    }

    @Test
    void testGetItemById_notFound() {
        when(itemRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Item> response = itemController.getItemById(2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetItemsByName_found() {
        when(itemRepository.findByName("Widget")).thenReturn(List.of(item));

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Widget");

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetItemsByName_notFound() {
        when(itemRepository.findByName("Nonexistent")).thenReturn(List.of());

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Nonexistent");

        assertEquals(404, response.getStatusCodeValue());
    }
}
