package com.example.bookstore;

import com.example.bookstore.dto.CustomerDTO;
import com.example.bookstore.model.Customer;
import com.example.bookstore.repository.CustomerRepository;
import com.example.bookstore.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<CustomerDTO> result = customerService.findAll();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("john@example.com", result.get(0).getEmail());
    }

    @Test
    void testFindById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        customer.setEmail("alice@example.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDTO dto = customerService.findById(1L);

        assertEquals(1L, dto.getId());
        assertEquals("Alice", dto.getName());
        assertEquals("alice@example.com", dto.getEmail());
    }

    @Test
    void testSaveCustomer() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName("Bob");
        dto.setEmail("bob@example.com");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(2L);
        savedCustomer.setName("Bob");
        savedCustomer.setEmail("bob@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO result = customerService.save(dto);

        assertEquals(2L, result.getId());
        assertEquals("Bob", result.getName());
        assertEquals("bob@example.com", result.getEmail());
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 3L;

        customerService.delete(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }
}