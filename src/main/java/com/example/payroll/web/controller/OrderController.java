package com.example.payroll.web.controller;

import com.example.payroll.web.error.MethodNotAllowedException;
import com.example.payroll.web.error.NotEnoughUserBalanceException;
import com.example.payroll.web.error.OrderNotFoundException;
import com.example.payroll.web.assembler.OrdersCollectionAssembler;
import com.example.payroll.persistence.model.Order;
import com.example.payroll.web.assembler.OrderModelAssembler;
import com.example.payroll.service.OrderService;
import com.example.payroll.persistence.dao.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderModelAssembler orderAssembler;
    private final OrdersCollectionAssembler ordersCollectionAssembler;

    public OrderController(OrderService orderService, OrderRepository orderRepository, OrderModelAssembler orderAssembler, OrdersCollectionAssembler ordersCollectionAssembler) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderAssembler = orderAssembler;
        this.ordersCollectionAssembler = ordersCollectionAssembler;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> getAllOrders() {

        List<EntityModel<Order>> orders = orderService.findAll().stream()
                .map(orderAssembler::toModel)
                .collect(Collectors.toList());

        return ordersCollectionAssembler.toModel(orders);
    }

    @GetMapping("/users/{userId}/orders")
    public CollectionModel<EntityModel<Order>> getAllOrdersByUserId(@PathVariable Long userId) {

        return ordersCollectionAssembler.toModel(orderRepository.findAllByUserId(userId).stream()
                        .map(orderAssembler::toModel)
                        .collect(Collectors.toList()),
                userId);
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> getOrderById(@PathVariable Long id) {

        Order order = orderService.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        return orderAssembler.toModel(order);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order, @PathVariable Long userId) {

        try {
            Order newOrder = orderService.newOrder(order, userId);
            log.info("New order " + newOrder);

            return ResponseEntity
                    .created(linkTo(methodOn(OrderController.class).getOrderById(newOrder.getId())).toUri())
                    .body(orderAssembler.toModel(newOrder));
        } catch (NotEnoughUserBalanceException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes)
                    .body(Problem.create()
                            .withTitle("Creation not allowed")
                            .withDetail(ex.getMessage()));
        }

    }

    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {

        Order order = orderService.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        try {
            return ResponseEntity.ok(orderAssembler.toModel(orderService.cancel(order)));
        } catch (MethodNotAllowedException ex) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail(ex.getMessage()));
        }
    }

    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long id) {

        Order order = orderService.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        try {
            return ResponseEntity.ok(orderAssembler.toModel(orderService.complete(order)));
        } catch (MethodNotAllowedException ex) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail(ex.getMessage()));
        }
    }

    @DeleteMapping("/orders")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
