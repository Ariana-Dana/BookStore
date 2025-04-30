package com.example.bookstore;

import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        OrderItemDTO dto = new OrderItemDTO(null, 2, new BigDecimal("49.99"), 1L);

        Book book = new Book();
        book.setId(1L);

        OrderItem saved = new OrderItem();
        saved.setId(10L);
        saved.setQuantity(2);
        saved.setPrice(new BigDecimal("49.99"));
        saved.setBook(book);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(saved);

        OrderItemDTO result = orderItemService.save(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(2, result.getQuantity());
        assertEquals(new BigDecimal("49.99"), result.getPrice());
        assertEquals(1L, result.getBookId());
    }

    @Test
    void testFindById() {
        Book book = new Book();
        book.setId(2L);

        OrderItem item = new OrderItem();
        item.setId(5L);
        item.setQuantity(1);
        item.setPrice(new BigDecimal("19.99"));
        item.setBook(book);

        when(orderItemRepository.findById(5L)).thenReturn(Optional.of(item));

        OrderItemDTO dto = orderItemService.findById(5L);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals(1, dto.getQuantity());
        assertEquals(new BigDecimal("19.99"), dto.getPrice());
        assertEquals(2L, dto.getBookId());
    }

    @Test
    void testFindAll() {
        OrderItem item = new OrderItem();
        item.setId(7L);
        item.setQuantity(3);
        item.setPrice(new BigDecimal("29.99"));

        when(orderItemRepository.findAll()).thenReturn(List.of(item));

        List<OrderItemDTO> result = orderItemService.findAll();

        assertEquals(1, result.size());
        assertEquals(7L, result.get(0).getId());
        assertEquals(3, result.get(0).getQuantity());
    }

    @Test
    void testDelete() {
        orderItemService.delete(8L);
        verify(orderItemRepository, times(1)).deleteById(8L);
    }
}