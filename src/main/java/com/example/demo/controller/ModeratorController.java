package com.example.demo.controller;

import com.example.demo.model.order.DeliveryStatus;
import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.Moderator;
import com.example.demo.model.users.User;
import com.example.demo.service.ModeratorService;
import com.example.demo.service.PhoneService;
import lombok.Data;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Data
@RestController
@RequestMapping(path = "/moderator")
public class ModeratorController {

    private final PhoneService phoneService;
    private final ModeratorService moderatorService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<Moderator> auth(@RequestParam String login,
                                          @RequestParam String password) {
        return new ResponseEntity<>(moderatorService.auth(login, password));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<Moderator> logout(HttpSession httpSession) {
        return new ResponseEntity<>(moderatorService.logout(httpSession));
    }

    @RequestMapping(value = "/createPhone", method = RequestMethod.PUT)
    public ResponseEntity<Phone> createPhone(@Valid @RequestBody Phone phone) {
        return new ResponseEntity<>(phoneService.createPhone(phone));
    }

    @RequestMapping(value = "/createPhones", method = RequestMethod.PUT)
    public ResponseEntity<Phone> createListPhones(@Valid @RequestBody List<Phone> phones) {
        return new ResponseEntity<>(phoneService.createPhone(phones));
    }

    @RequestMapping(value = "/getOrdersByStatus", method = RequestMethod.GET)
    public List<ShopOrder> getOrdersByStatus(@RequestParam DeliveryStatus deliveryStatus,
                                             HttpSession httpSession) {
        List<ShopOrder> ordersByStatus = moderatorService.getOrdersByStatus(deliveryStatus);
        httpSession.setAttribute("ordersByStatus", ordersByStatus);
        return ordersByStatus;
    }

    ///
    @RequestMapping(value = "/changeUserPhoneNumber", method = RequestMethod.POST)
    public ResponseEntity<User> changeUserPhoneNumber(@RequestParam String phoneNumber,
                                                      @RequestParam String newPhoneNumber) {
        return new ResponseEntity<>(moderatorService.changeUserPhoneNumber(phoneNumber, newPhoneNumber));
    }

    @RequestMapping(value = "/getOrder", method = RequestMethod.GET)
    public ShopOrder getOrderByUser(@RequestParam String login,
                                    HttpSession httpSession) {
        ShopOrder orderByUserLogin = moderatorService.getOrderByUserLogin(login);
        if (orderByUserLogin != null) {
            httpSession.setAttribute("orderByUser", orderByUserLogin);
            return orderByUserLogin;
        } else return null;
    }

    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public List<ShopOrder> getAllOrders(HttpSession httpSession) {
        List<ShopOrder> allOrders = moderatorService.getAllOrders();
        httpSession.setAttribute("allOrders", allOrders);
        for (ShopOrder shopOrder : allOrders) {
            System.out.println(shopOrder);
        }
        return allOrders;
    }

    @RequestMapping(value = "/acceptAll", method = RequestMethod.POST)
    public ResponseEntity<ShopOrder> changeAllOrdersToAccepted() {
        return new ResponseEntity<>(moderatorService.changeAllToAccepted());
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    public ResponseEntity<ShopOrder> changeStatusToAcceptedByLogin(@RequestParam String login) {
        return new ResponseEntity<>(moderatorService.changeDeliveryStatusToAcceptedByUserLogin(login));
    }

    @RequestMapping(value = "/changeAllStatus", method = RequestMethod.POST)
    public ResponseEntity<ShopOrder> changeAllStatus(@RequestParam DeliveryStatus deliveryStatus) {
        return new ResponseEntity<>(moderatorService.changeAllStatusTo(deliveryStatus));
    }

    @RequestMapping(value = "/deleteFromOrder", method = RequestMethod.DELETE)
    public ResponseEntity<ShopOrder> deleteFromOrder(@RequestParam String serialNumber,
                                                     @RequestParam String login) {
        return new ResponseEntity<>(moderatorService.deleteFromUserOrder(serialNumber, login));
    }

    @RequestMapping(value = "/deleteOrderByLogin", method = RequestMethod.DELETE)
    public ResponseEntity<ShopOrder> deleteOrder(@RequestParam String login) {
        return new ResponseEntity<>(moderatorService.deleteOrderByUserLogin(login));
    }


}
