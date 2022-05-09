package com.example.payroll.application.order;

import com.example.payroll.api.order.OrderController;
import com.example.payroll.core.order.Order;
import com.example.payroll.core.order.Status;
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
                linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModer.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));
            orderModer.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId())).withRel("complete"));
        }

        return orderModer;
    }
}
