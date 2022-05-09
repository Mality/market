package com.example.payroll.web.assembler;

import com.example.payroll.web.controller.OrderController;
import com.example.payroll.persistence.model.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrdersCollectionAssembler implements RepresentationModelAssembler<List<EntityModel<Order>>, CollectionModel<EntityModel<Order>>> {

    @Override
    public CollectionModel<EntityModel<Order>> toModel(List<EntityModel<Order>> orders) {
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
    }

    public CollectionModel<EntityModel<Order>> toModel(List<EntityModel<Order>> orders, Long userId) {
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAllOrdersByUserId(userId)).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));
    }
}
