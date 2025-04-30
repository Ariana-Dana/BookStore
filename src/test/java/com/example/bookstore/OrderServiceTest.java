package com.example.bookstore;

import com.example.bookstore.dto.OrderDTO;
import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.model.*;
import com.example.bookstore.repository.*;
import com.example.bookstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    void testCreateOrder() {
        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(2L);
        book.setPrice(new BigDecimal("50.00"));

        OrderItemDTO itemDTO = new OrderItemDTO(null, 2, null, 2L);
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(1L);
        dto.setItems(List.of(itemDTO));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        OrderDTO result = orderService.createOrder(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(1L, result.getCustomerId());
        assertEquals(1, result.getItems().size());
        assertEquals(new BigDecimal("100.00"), result.getTotal());
    }

    @Test
    void testFindById() {
        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(2L);

        OrderItem item = new OrderItem();
        item.setId(5L);
        item.setBook(book);
        item.setQuantity(1);
        item.setPrice(new BigDecimal("30.00"));

        Order order = new Order();
        order.setId(7L);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(new BigDecimal("30.00"));
        order.setItems(List.of(item));

        when(orderRepository.findById(7L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.findById(7L);

        assertNotNull(result);
        assertEquals(7L, result.getId());
        assertEquals(1L, result.getCustomerId());
        assertEquals(1, result.getItems().size());
    }

    @Test
    void testFindAll() {
        Order order = new Order();
        order.setId(11L);
        order.setCustomer(new Customer(1L, "Test", "test@example.com", null));
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(new BigDecimal("50.00"));
        order.setItems(List.of());

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDTO> result = orderService.findAll();

        assertEquals(1, result.size());
        assertEquals(11L, result.get(0).getId());
    }

    @Test
    void testFindByCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);

        Order order = new Order();
        order.setId(12L);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(new BigDecimal("70.00"));
        order.setItems(List.of());

        when(orderRepository.findByCustomerId(1L)).thenReturn(List.of(order));

        List<OrderDTO> result = orderService.findByCustomer(1L);

        assertEquals(1, result.size());
        assertEquals(12L, result.get(0).getId());
    }

    @Test
    void testDelete() {
        when(orderRepository.existsById(5L)).thenReturn(true);

        orderService.delete(5L);

        verify(orderRepository, times(1)).deleteById(5L);
    }

    @Test
    void testDeleteNotFound() {
        when(orderRepository.existsById(99L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.delete(99L));
        assertEquals("Order not found with id: 99", ex.getMessage());
    }
}