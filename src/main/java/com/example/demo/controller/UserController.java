package com.example.demo.controller;

import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.User;
import com.example.demo.service.UserService;
import lombok.Data;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Data
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<User> userRegistration(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user));
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<User> userAuthorization(@RequestParam String login,
                                                  @RequestParam String password,
                                                  HttpSession httpSession) {
        if (httpSession.getAttribute("currentUser") == null) {
            User currentUser = userService.authUser(login, password);
            if (currentUser != null) {
                httpSession.setAttribute("authCheck", true);
                httpSession.setAttribute("currentUser", currentUser);
            } else httpSession.setAttribute("authCheck", false);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/myOrder", method = RequestMethod.GET)
    public List<Phone> userOrder(HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if (currentUser != null) {
            ShopOrder userOrder = userService.getUserOrder(currentUser.getLogin());
            return userOrder.getPhoneList();
        }
        return null;
    }

    @RequestMapping(value = "/deleteFromOrder", method = RequestMethod.DELETE)
    public ResponseEntity<ShopOrder> deleteFromOrder(@RequestParam String serialNumber,
                                                     HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        return new ResponseEntity<>(userService.deletePhone(serialNumber, currentUser.getLogin()));
    }


    @RequestMapping(value = "/changePhoneNumber", method = RequestMethod.PUT)
    public ResponseEntity<User> changePhoneNumber(@RequestParam String newPhoneNumber,
                                                  HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        return new ResponseEntity<>(userService.changePhoneNumber(currentUser, newPhoneNumber));
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public ResponseEntity<User> logout(HttpSession httpSession) {
        return new ResponseEntity<>(userService.logout(httpSession));
    }


    @RequestMapping(path = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if (currentUser != null) {
            userService.logout(httpSession);
            return new ResponseEntity<>(userService.deleteByLogin(currentUser.getLogin()));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
