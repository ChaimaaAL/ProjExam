package com.app.restaurant.controllers;

import com.app.restaurant.model.*;
import com.app.restaurant.repository.CustomerRepository;
import com.app.restaurant.repository.MealRepository;
import com.app.restaurant.repository.OrderRepository;
import com.app.restaurant.service.OrderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MealRepository mealRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("order") != null) {
            Order order = (Order) session.getAttribute("order");
            if(order.getMeals().size() == 0 ){
                return "order/emptyCart";
            }
            model.addAttribute("meal", orderService.getAllMealsForOrder(order));
            return "order/myOrder";
        }
        return "menu/allMeals";
    }

   @GetMapping("/add{id}")
    public String addMealToOrder(@RequestParam("id") Long id, HttpSession session) {
        {
            if (session.getAttribute("order") == null) {
                Order order = new Order();
                List<Meal> mealList = new ArrayList<>();
                mealList.add(mealRepository.findById(id).orElseThrow(NullPointerException::new));
                order.setMeals(mealList);
                order.setRealizationDate(LocalDateTime.now());
                order.setPriority(0);
                orderRepository.save(order);
                session.setAttribute("order", order);
            } else {
                Order order = (Order) session.getAttribute("order");
                List<Meal> mealList = order.getMeals();
                mealList.add(mealRepository.findById(id).orElseThrow(NullPointerException::new));
                order.setMeals(mealList);
                order.setRealizationDate(LocalDateTime.now());
                order.setPriority(0);
                orderRepository.save(order);
                session.setAttribute("order", order);
            }
            return "redirect:/order";
        }
    }

    @GetMapping("/complete")
    public String completeYourOrder(HttpSession session, Model model) {
        if (session.getAttribute("order") != null) {
            Order order = (Order) session.getAttribute("order");
            if (order.getMeals().size() == 0) {
                session.removeAttribute("order");
                return "redirect:/menu";
            }
            model.addAttribute("customer", new Customer());

            return "customer/addCustomer";
        }

        return "customer/addCustomer";
    }

    @PostMapping("/complete")
    public String completeYourOrderPOST(HttpSession session, @ModelAttribute Customer customer) {
        if (session.getAttribute("order") != null) {
            Order order = (Order) session.getAttribute("order");
            order.setCustomer(customer);

            customerRepository.save(customer);
            orderRepository.save(order);

            return "/order/Done";
        }
        return "redirect:/order";
    }
    
    /*
    @GetMapping("/payment")
    public String paymentGET(HttpSession session, Model model) {
        if (session.getAttribute("order") != null) {
            Order order = (Order) session.getAttribute("order");
            model.addAttribute("payment", new PaymentDetail());
            model.addAttribute("order", order);
            model.addAttribute("price", orderService.priceForOrder(order));

            return "order/completeOrder";
        }
        return "order/completeOrder";
    }

   

    @GetMapping("/dotpay")
    public String moveToDotpay() {
        return "redirect:/order/Done";
    }*/
    

    @PostMapping("/delete{id}")
    public String deleteMealFromOrder(@RequestParam("id") Long id, HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        List<Meal> mealList = order.getMeals();
        mealList.remove(mealRepository.findById(id).orElseThrow(NullPointerException::new));
        orderRepository.save(order);
        session.setAttribute("order", order);
        return "redirect:/order";
    }

}
