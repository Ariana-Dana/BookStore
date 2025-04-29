package com.example.bookstore;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Customer;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CustomerRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder_shouldReturnSavedOrderDTO() {
        OrderItemDTO itemDTO = new OrderItemDTO(null, 2, BigDecimal.ZERO, 1L);
        OrderDTO inputDTO = new OrderDTO();
        inputDTO.setCustomerId(1L);
        inputDTO.setItems(List.of(itemDTO));

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(19.99));

        Order savedOrder = new Order();
        savedOrder.setId(100L);
        savedOrder.setCustomer(customer);
        savedOrder.setOrderDate(java.time.LocalDateTime.now());
        savedOrder.setTotal(BigDecimal.valueOf(39.98));

        OrderItem savedItem = new OrderItem();
        savedItem.setId(200L);
        savedItem.setBook(book);
        savedItem.setQuantity(2);
        savedItem.setPrice(BigDecimal.valueOf(39.98));
        savedItem.setOrder(savedOrder);
        savedOrder.setItems(List.of(savedItem));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderDTO result = orderService.createOrder(inputDTO);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(1L, result.getCustomerId());
        assertEquals(1, result.getItems().size());
        assertEquals(200L, result.getItems().get(0).getId());
        assertEquals(BigDecimal.valueOf(39.98), result.getTotal());

        verify(customerRepository).findById(1L);
        verify(bookRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }
}