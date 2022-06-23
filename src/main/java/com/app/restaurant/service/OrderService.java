package com.app.restaurant.service;

import com.app.restaurant.model.Customer;
import com.app.restaurant.model.Meal;
import com.app.restaurant.model.Order;
import com.app.restaurant.repository.CustomerRepository;
import com.app.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    public List<Meal> getAllMealsForOrder(Order order) {
        List<Meal> retList = new ArrayList<>();
        for (Meal m : order.getMeals()) {
            retList.add(m);
        }
        return retList;
    }

    
    public BigDecimal priceForOrder(Order order) {
        BigDecimal price = new BigDecimal(0);
        for (Meal m : order.getMeals()) {
            price = price.add(m.getPrice());
        }
        return price;

    }

    public List<Order> getAllOrdersWithDoneStatus() {
        List<Order> filteredList = orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getPriority))
                .collect(Collectors.toList());
        return filteredList;

    }

    public List<Order> findAllByOrderStatusIsNotContaining(){
        List<Order> filteredList = orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getPriority).reversed())
                .collect(Collectors.toList());
        return filteredList;
    }


}
