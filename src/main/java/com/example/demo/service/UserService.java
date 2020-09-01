package com.example.demo.service;

import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.users.User;
import com.example.demo.repsitory.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Data
@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public HttpStatus createUser(User user) {
        if (!userRepository.existsUserByLoginAndPassword(user.getLogin(), user.getPassword())) {
            userRepository.save(user);
            logger.info("User created successfully");
            return HttpStatus.OK;
        }
        logger.error("User doesn`t created");
        return HttpStatus.BAD_REQUEST;
    }

    public User authUser(String login, String password) {
        if (userRepository.existsUserByLoginAndPassword(login, password)) {
            return userRepository.getUserByLogin(login);
        } else return null;
    }

    public HttpStatus changePhoneNumber(User user, String newPhoneNumber) {
        if (user != null) {
            user.setPhoneNumber(newPhoneNumber);
            userRepository.save(user);
            logger.info("PhoneNumber was changed");
            return HttpStatus.OK;
        }
        logger.error("PhoneNumber doesn`t changed");
        return HttpStatus.BAD_REQUEST;
    }

    @Transactional
    public HttpStatus deleteByLogin(String login) {
        if (userRepository.deleteByLogin(login) > 0) {
            logger.info("User with login: " + login + " deleted successfully");
            return HttpStatus.OK;
        } else {
            logger.error("Unexpected error");
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus logout(HttpSession httpSession) {
        if (httpSession.getAttribute("authCheck") != null) {
            httpSession.setAttribute("authCheck", false);
            httpSession.setAttribute("currentUser", null);
            httpSession.invalidate();
            logger.info("Invalidated successfully");
            return HttpStatus.OK;
        }
        logger.error("Unexpected error");
        return HttpStatus.BAD_REQUEST;
    }

    public ShopOrder getUserOrder(String login) {
        return orderService.getOrderByUserLogin(login);
    }

    public HttpStatus deletePhone(String serialNumber, String login) {
        return orderService.deleteFromOrder(serialNumber, login);
    }


}
