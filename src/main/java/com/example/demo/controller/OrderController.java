package com.example.demo.controller;

import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.User;
import com.example.demo.service.OrderService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Data
@RestController
@RequestMapping(path = "/order")
public class OrderController {

    private final OrderService orderService;


    @RequestMapping(value = "/allPhones", method = RequestMethod.GET)
    public List<Phone> allPhones() {
        return orderService.getAllPhones();
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ResponseEntity<ShopOrder> createOrder(@Valid @RequestParam String serialNumber, HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if (currentUser != null) {
            orderService.createOrder(currentUser, serialNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
