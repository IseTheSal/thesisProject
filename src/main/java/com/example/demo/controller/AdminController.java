package com.example.demo.controller;

import com.example.demo.model.users.Admin;
import com.example.demo.model.users.Courier;
import com.example.demo.model.users.Moderator;
import com.example.demo.model.users.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import lombok.Data;
import org.hibernate.validator.constraints.pl.REGON;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Data
@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;


    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    public ResponseEntity<Admin> reg(@Valid @RequestBody Admin admin, HttpSession httpSession) {
        return new ResponseEntity<>(adminService.createAdmin(admin));
    }


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<Admin> authAdmin(@RequestParam String login,
                                           @RequestParam String password,
                                           HttpSession httpSession) {
        if (httpSession.getAttribute("adminAuth") == null) {
            HttpStatus httpStatus = adminService.authAdmin(login, password);
            if (httpStatus.equals(HttpStatus.ACCEPTED)) {
                httpSession.setAttribute("adminAuth", true);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/createModerator", method = RequestMethod.PUT)
    public ResponseEntity<Moderator> createModerator(@Valid @RequestBody Moderator moderator) {
        return new ResponseEntity<>(adminService.createModerator(moderator));
    }

    @RequestMapping(value = "/getModerator", method = RequestMethod.GET)
    public Moderator getModerator(@RequestParam String login) {
        return adminService.getModeratorByLogin(login);
    }

    @RequestMapping(value = "/getAllModerators", method = RequestMethod.GET)
    public List<Moderator> getAllModerators() {
        return adminService.getAllModerators();
    }

    @RequestMapping(value = "/deleteModerator", method = RequestMethod.DELETE)
    public ResponseEntity<Moderator> deleteModeratorByLogin(@RequestParam String login) {
        return new ResponseEntity<>(adminService.deleteModeratorByLogin(login));
    }

    @RequestMapping(value = "/createCourier", method = RequestMethod.PUT)
    public ResponseEntity<Moderator> createCourier(@Valid @RequestBody Courier courier) {
        return new ResponseEntity<>(adminService.createCourier(courier));
    }

    @RequestMapping(value = "/getCourier", method = RequestMethod.GET)
    public Courier getCourier(@RequestParam String login) {
        return adminService.getCourierByLogin(login);
    }

    @RequestMapping(value = "/getAllCouriers", method = RequestMethod.GET)
    public List<Courier> getAllCouriers() {
        return adminService.getAllCouriers();
    }

    @RequestMapping(value = "/deleteCourier", method = RequestMethod.DELETE)
    public ResponseEntity<Courier> deleteCourierByLogin(@RequestParam String login) {
        return new ResponseEntity<>(adminService.deleteCourierByLogin(login));
    }


    @RequestMapping(value = "/createUser", method = RequestMethod.PUT)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(adminService.createUser(user));
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser(@RequestParam String login) {
        return adminService.getUser(login);
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestParam String login) {
        return new ResponseEntity<>(adminService.deleteUserByLogin(login));
    }

}
