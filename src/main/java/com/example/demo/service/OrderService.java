package com.example.demo.service;

import com.example.demo.model.order.DeliveryStatus;
import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.phone.Available;
import com.example.demo.model.phone.Brand;
import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.User;
import com.example.demo.repsitory.OrderRepository;
import com.example.demo.repsitory.PhoneRepository;
import com.example.demo.repsitory.UserRepository;
import lombok.Data;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;

    public Logger logger = LoggerFactory.getLogger(OrderService.class);

    public void saveOrder(ShopOrder shopOrder) {
        orderRepository.save(shopOrder);
    }

    public HttpStatus createForCourier(User user, String serialNumber) {
        if (userRepository.existsUserByLoginAndPassword(user.getLogin(), user.getPassword())) {
            logger.error("User with that login already exist");
            return HttpStatus.BAD_REQUEST;
        } else {
            userRepository.save(user);
            createOrder(user, serialNumber);
            logger.info("Order created successfully");
            return HttpStatus.OK;
        }
    }

    public void createOrder(User user, String serialNumber) {
        Phone phone = phoneRepository.findBySerialNumber(serialNumber);
        if (phone != null && phone.getAvailable().equals(Available.InStock)) {
            if (orderRepository.existsShopOrderByUser_LoginAndUser_Password(user.getLogin(), user.getPassword())) {
                ShopOrder userOrder = orderRepository.findByUser_Login(user.getLogin());
                if (userOrder == null) userOrder = new ShopOrder();
                List<Phone> phoneList = userOrder.getPhoneList();
                if (phoneList == null) phoneList = new ArrayList<>();
                phoneList.add(phone);
                userOrder.setPhoneList(phoneList);
                userOrder.setDeliveryStatus(DeliveryStatus.Created);
                orderRepository.save(userOrder);
            } else {
                ShopOrder currentOrder = new ShopOrder();
                currentOrder.setUser(user);
                List<Phone> phoneList = new ArrayList<>();
                phoneList.add(phone);
                currentOrder.setPhoneList(phoneList);
                currentOrder.setDeliveryStatus(DeliveryStatus.Created);
                orderRepository.save(currentOrder);
            }
            phone.setAvailable(Available.Ordered);
            phoneRepository.save(phone);
        }
    }

    public HttpStatus addToOrder(String phoneNumber, String serialNumber) {
        Phone phone = phoneRepository.findBySerialNumber(serialNumber);
        User user = userRepository.getByPhoneNumber(phoneNumber);
        if (phone != null && user != null) {
            ShopOrder order = orderRepository.getByUser_PhoneNumber(phoneNumber);
            List<Phone> phoneList = order.getPhoneList();
            if (phoneList == null) phoneList = new ArrayList<>();
            phoneList.add(phone);
            order.setPhoneList(phoneList);
            order.setDeliveryStatus(DeliveryStatus.Created);
            phone.setAvailable(Available.Ordered);
            phoneRepository.save(phone);
            saveOrder(order);
            logger.info(phone + " was added to order from" + order.getUser().getName());
            return HttpStatus.OK;
        }
        logger.error("Such phone or user doesn`t exist");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus deleteFromOrder(String serialNumber, String login) {
        ShopOrder currentOrder = orderRepository.findByUser_Login(login);
        return removeFromPhoneList(serialNumber, currentOrder);
    }

    private HttpStatus removeFromPhoneList(String serialNumber, ShopOrder currentOrder) {
        if (currentOrder != null) {
            List<Phone> phoneList = currentOrder.getPhoneList();
            if (phoneList.removeIf(phone -> phone.getSerialNumber().equals(serialNumber))) {
                orderRepository.save(currentOrder);
                logger.info("Successfully removed");
                return HttpStatus.OK;
            }
        }
        logger.error("Unexpected error");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus deleteFromOrder(String phoneNumber, Phone phone) {
        ShopOrder shopOrder = orderRepository.getByUser_PhoneNumber(phoneNumber);
        List<Phone> phoneList = shopOrder.getPhoneList();
        if (phoneList != null) {
            if (phoneList.remove(phone)) {
                saveOrder(shopOrder);
                logger.info("Successfully deleted");
                return HttpStatus.OK;
            }
        }
        logger.error("Unexpected error");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus changePhoneNumber(String phoneNumber, String newPhoneNumber) {
        ShopOrder order = orderRepository.getByUser_PhoneNumber(phoneNumber);
        if (order != null) {
            User user = order.getUser();
            user.setPhoneNumber(newPhoneNumber);
            userRepository.save(user);
            logger.info("Successfully changed");
            return HttpStatus.OK;
        }
        logger.error("User with phoneNumber - " + phoneNumber + " doesn`t exist");
        return HttpStatus.BAD_REQUEST;
    }


    public HttpStatus removeFromOrder(String phoneNumber, String serialNumber) {
        ShopOrder order = orderRepository.getByUser_PhoneNumber(phoneNumber);
        return removeFromPhoneList(serialNumber, order);
    }


    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }


    public HttpStatus changeAllToAccepted() {
        List<ShopOrder> all = orderRepository.findAll();
        for (ShopOrder shopOrder : all) {
            if (shopOrder.getDeliveryStatus().equals(DeliveryStatus.Created)) {
                shopOrder.setDeliveryStatus(DeliveryStatus.Accepted);
                saveOrder(shopOrder);
            }
        }
        return HttpStatus.OK;
    }

    public HttpStatus changeAllStatusTo(DeliveryStatus deliveryStatus) {
        List<ShopOrder> all = orderRepository.findAll();
        for (ShopOrder shopOrder : all) {
            shopOrder.setDeliveryStatus(deliveryStatus);
            orderRepository.save(shopOrder);
        }
        return HttpStatus.OK;
    }

    public HttpStatus changeAllStatusToCreated() {
        return changeAllStatusTo(DeliveryStatus.Created);
    }

    public HttpStatus changeAllStatusToAccepted() {
        return changeAllStatusTo(DeliveryStatus.Accepted);
    }

    public HttpStatus changeAllStatusToInProgress() {
        return changeAllStatusTo(DeliveryStatus.InProcess);
    }

    public HttpStatus changeAllStatusToDelivered() {
        return changeAllStatusTo(DeliveryStatus.Delivered);
    }

    public HttpStatus changeStatusByPhoneNumber(String phoneNumber, DeliveryStatus deliveryStatus) {
        ShopOrder byUser_phoneNumber = orderRepository.getByUser_PhoneNumber(phoneNumber);
        if (byUser_phoneNumber != null) {
            byUser_phoneNumber.setDeliveryStatus(deliveryStatus);
            orderRepository.save(byUser_phoneNumber);
            logger.info("Status changed successfully");
            return HttpStatus.OK;
        }
        logger.error("User with phoneNumber - " + phoneNumber + " doesn`t exist");
        return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus changeStatusToInProcess(String phoneNumber) {
        return changeStatusByPhoneNumber(phoneNumber, DeliveryStatus.InProcess);
    }

    public HttpStatus changeStatusToDelivered(String phoneNumber) {
        return changeStatusByPhoneNumber(phoneNumber, DeliveryStatus.Delivered);
    }

    public HttpStatus changeStatusToAccepted(String phoneNumber) {
        return changeStatusByPhoneNumber(phoneNumber, DeliveryStatus.Accepted);
    }

    public HttpStatus changeStatusToCreated(String phoneNumber) {
        return changeStatusByPhoneNumber(phoneNumber, DeliveryStatus.Created);
    }

    public HttpStatus changeDeliveryStatusToAcceptedByUserLogin(String login) {
        ShopOrder byUser_login = orderRepository.getByUser_Login(login);
        if (byUser_login != null) {
            byUser_login.setDeliveryStatus(DeliveryStatus.Accepted);
            saveOrder(byUser_login);
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.BAD_REQUEST;
    }


    @Transactional
    public HttpStatus deleteOrderByLogin(String login) {
        if (orderRepository.deleteByUser_Login(login) > 0) {
            logger.info("Successfully deleted");
            return HttpStatus.OK;
        } else {
            logger.error("Unexpected error");
            return HttpStatus.BAD_REQUEST;
        }
    }

    @Transactional
    public HttpStatus deleteOrderByPhoneNumber(String phoneNumber) {
        if (orderRepository.deleteByUser_PhoneNumber(phoneNumber) > 0) {
            logger.info("Successfully deleted");
            return HttpStatus.OK;
        } else {
            logger.error("Unexpected error");
            return HttpStatus.BAD_REQUEST;
        }
    }


    public void printAllOrders() {
        List<ShopOrder> all = orderRepository.findAll();
        for (ShopOrder shopOrder : all) {
            System.out.println(shopOrder);
        }
    }

    public List<ShopOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<ShopOrder> getOrdersByStatus(DeliveryStatus deliveryStatus) {
        return orderRepository.getAllByDeliveryStatus(deliveryStatus);
    }

    public ShopOrder getOrderByUserLogin(String login) {
        return orderRepository.findByUser_Login(login);
    }

    public ShopOrder getOrderByUserPhoneNumber(String phoneNumber) {
        return orderRepository.getByUser_PhoneNumber(phoneNumber);
    }

}
