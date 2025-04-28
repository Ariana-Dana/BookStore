package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderItemDTO;
import com.example.bookstore.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orderitems")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItemDTO getOrderItemById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemDTO createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
        return orderItemService.save(orderItemDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.delete(id);
    }
}
