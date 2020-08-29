package com.example.demo.service;

import com.example.demo.model.order.DeliveryStatus;
import com.example.demo.model.order.ShopOrder;
import com.example.demo.repsitory.ModeratorRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Data
@Service
public class ModeratorService {

    private final OrderService orderService;
    private final ModeratorRepository moderatorRepository;

    public HttpStatus auth(String login, String password) {
        return moderatorRepository.existsByLoginAndPassword(login, password) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    public HttpStatus logout(HttpSession httpSession) {
        if (httpSession.getAttribute("currentModer") != null) {
            httpSession.invalidate();
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus changeAllToAccepted() {
        return orderService.changeAllToAccepted();
    }

    public HttpStatus changeAllStatusTo(DeliveryStatus deliveryStatus) {
        return orderService.changeAllStatusTo(deliveryStatus);
    }

    public HttpStatus changeDeliveryStatusToAcceptedByUserLogin(String login) {
        HttpStatus httpStatus = orderService.changeDeliveryStatusToAcceptedByUserLogin(login);
        if (httpStatus.equals(HttpStatus.ACCEPTED)) {
            return HttpStatus.OK;
        } else return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus deleteFromUserOrder(String serialNumber, String login) {
        return orderService.deleteFromOrder(serialNumber, login);
    }

    public HttpStatus changeUserPhoneNumber(String phoneNumber, String newPhoneNumber) {
        return orderService.changePhoneNumber(phoneNumber, newPhoneNumber);
    }


    public List<ShopOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    public ShopOrder getOrderByUserLogin(String login) {
        return orderService.getOrderByUserLogin(login);
    }

    public List<ShopOrder> getOrdersByStatus(DeliveryStatus deliveryStatus) {
        return orderService.getOrdersByStatus(deliveryStatus);
    }

    public HttpStatus deleteOrderByUserLogin(String login) {
        return orderService.deleteOrderByLogin(login);
    }
}
