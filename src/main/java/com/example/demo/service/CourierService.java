package com.example.demo.service;

import com.example.demo.model.order.DeliveryStatus;
import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.User;
import com.example.demo.repsitory.CourierRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class CourierService {

    private final OrderService orderService;

    private final CourierRepository courierRepository;

    private Logger logger = LoggerFactory.getLogger(CourierService.class);

    public HttpStatus authCourier(String login, String password) {
        if (courierRepository.existsByLoginAndPassword(login, password)) {
            logger.info("Courier logged in");
            return HttpStatus.OK;
        } else {
            logger.info("Courier didn`t logged in");
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus changeStatusToDelivered(String phoneNumber) {
        return orderService.changeStatusToDelivered(phoneNumber);
    }

    public HttpStatus changeAllStatusToDelivered() {
        return orderService.changeAllStatusTo(DeliveryStatus.Delivered);
    }

    public HttpStatus changeStatusToInProcess(String phoneNumber) {
        return orderService.changeStatusToInProcess(phoneNumber);
    }

    public HttpStatus changeAllStatusToInProcess() {
        return orderService.changeAllStatusTo(DeliveryStatus.InProcess);
    }

    public HttpStatus createOrder(User user, String serialNumber) {
        return orderService.createForCourier(user, serialNumber);
    }

    public HttpStatus addToOrder(String phoneNumber, String serialNumber) {
        return orderService.addToOrder(phoneNumber, serialNumber);
    }


    public ShopOrder getOrderByPhoneNumber(String phoneNumber) {
        return orderService.getOrderByUserPhoneNumber(phoneNumber);
    }

    public HttpStatus removeFromOrder(String phoneNumber, String serialNumber) {
        return orderService.removeFromOrder(phoneNumber, serialNumber);
    }

    public HttpStatus changeUserPhoneNumber(String phoneNumber, String newPhoneNumber) {
        return orderService.changePhoneNumber(phoneNumber, newPhoneNumber);
    }


    public HttpStatus deleteOrderByPhoneNumber(String phoneNumber) {
        return orderService.deleteOrderByPhoneNumber(phoneNumber);
    }
}
