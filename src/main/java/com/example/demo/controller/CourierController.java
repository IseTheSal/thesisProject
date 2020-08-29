package com.example.demo.controller;

import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.users.Courier;
import com.example.demo.model.users.User;
import com.example.demo.service.CourierService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Data
@RestController
@RequestMapping(path = "/delivery")
public class CourierController {

    private final CourierService courierService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<Courier> authCourier(@RequestParam String login,
                                               @RequestParam String password,
                                               HttpSession httpSession) {
        if (courierService.authCourier(login, password).equals(HttpStatus.OK)) {
            httpSession.setAttribute("courierAuth", true);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/changeToDelivered", method = RequestMethod.PUT)
    public ResponseEntity<ShopOrder> changeStatusDelivered(@RequestParam String phoneNumber) {
        return new ResponseEntity<>(courierService.changeStatusToDelivered(phoneNumber));
    }

    @RequestMapping(value = "/changeAllToDelivered", method = RequestMethod.PUT)
    public ResponseEntity<ShopOrder> changeAllStatusDelivered() {
        return new ResponseEntity<>(courierService.changeAllStatusToDelivered());
    }

    @RequestMapping(value = "/changeToInProcess", method = RequestMethod.PUT)
    public ResponseEntity<ShopOrder> changeStatusInProcess(@RequestParam String phoneNumber) {
        return new ResponseEntity<>(courierService.changeStatusToInProcess(phoneNumber));
    }

    @RequestMapping(value = "/changeAllToInProcess", method = RequestMethod.PUT)
    public ResponseEntity<ShopOrder> changeAllStatusInProcess() {
        return new ResponseEntity<>(courierService.changeAllStatusToInProcess());
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.PUT)
    public ResponseEntity<ShopOrder> createOrder(@Valid @RequestBody User user, @RequestParam String serialNumber) {
        return new ResponseEntity<>(courierService.createOrder(user, serialNumber));
    }

    @RequestMapping(value = "/addToOrder", method = RequestMethod.PUT)
    public ResponseEntity<ShopOrder> addToOrder(@RequestParam String phoneNumber, @RequestParam String serialNumber) {
        return new ResponseEntity<>(courierService.addToOrder(phoneNumber, serialNumber));
    }

    @RequestMapping(value = "/changeUserNumber", method = RequestMethod.PUT)
    public ResponseEntity<User> changeUserPhoneNumber(@RequestParam String phoneNumber,
                                                      @RequestParam String newPhoneNumber) {
        return new ResponseEntity<>(courierService.changeUserPhoneNumber(phoneNumber, newPhoneNumber));
    }

    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    public ShopOrder getOrder(@RequestParam String phoneNumber) {
        return courierService.getOrderByPhoneNumber(phoneNumber);
    }


    @RequestMapping(value = "/removeFromOrder", method = RequestMethod.DELETE)
    public ResponseEntity<ShopOrder> removeFromOrders(@RequestParam String phoneNumber,
                                                      @RequestParam String serialNumber) {
        return new ResponseEntity<>(courierService.removeFromOrder(phoneNumber, serialNumber));
    }


    @RequestMapping(value = "/deleteOrder", method = RequestMethod.DELETE)
    public ResponseEntity<ShopOrder> deleteOrder(@RequestParam String phoneNumber) {
        return new ResponseEntity<>(courierService.deleteOrderByPhoneNumber(phoneNumber));
    }


}
