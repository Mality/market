package com.example.payroll.web.assembler;

import com.example.payroll.web.controller.OrderRestController;
import com.example.payroll.persistence.model.Order;
import com.example.payroll.persistence.model.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderModer = EntityModel.of(order,
                linkTo(methodOn(OrderRestController.class).getOrderById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderRestController.class).getAllOrders()).withRel("orders"));

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModer.add(linkTo(methodOn(OrderRestController.class).cancelOrder(order.getId())).withRel("cancel"));
            orderModer.add(linkTo(methodOn(OrderRestController.class).completeOrder(order.getId())).withRel("complete"));
        }

        return orderModer;
    }
}
