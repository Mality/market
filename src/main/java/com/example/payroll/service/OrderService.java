package com.example.payroll.service;

import com.example.payroll.web.error.NotEnoughUserBalanceException;
import com.example.payroll.persistence.model.User;
import com.example.payroll.web.error.UserNotFoundException;
import com.example.payroll.web.error.MethodNotAllowedException;
import com.example.payroll.persistence.dao.UserRepository;
import com.example.payroll.persistence.dao.OrderRepository;
import com.example.payroll.persistence.model.Order;
import com.example.payroll.persistence.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order newOrder(Order order, Long userId) {

        User employee = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (employee.getFreeBalance() < order.getCost()) {
            throw new NotEnoughUserBalanceException("Employee " + employee.getName() + " free balance " + employee.getFreeBalance() + " less than order cost " + order.getCost());
        }

        employee.setProcessedBalance(employee.getProcessedBalance() + order.getCost());
        userRepository.save(employee);

        log.info(employee.toString());

        order.setStatus(Status.IN_PROGRESS);
        order.setUser(employee);
        return orderRepository.save(order);
    }

    public Order cancel(Order order) {

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELED);
            orderRepository.save(order);

            User employee = order.getUser();
            employee.setProcessedBalance(employee.getProcessedBalance() - order.getCost());
            userRepository.save(employee);

            return order;
        }

        throw new MethodNotAllowedException("You can't cancel an order that is in the " + order.getStatus() + " status");
    }

    public Order complete(Order order) {

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            orderRepository.save(order);

            User employee = order.getUser();
            employee.setProcessedBalance(employee.getProcessedBalance() - order.getCost());
            employee.setBalance(employee.getBalance() - order.getCost());
            userRepository.save(employee);

            return order;
        }

        throw new MethodNotAllowedException("You can't complete an order that is in the " + order.getStatus() + " status");
    }
}
