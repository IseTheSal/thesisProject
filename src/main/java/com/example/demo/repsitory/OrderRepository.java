package com.example.demo.repsitory;

import com.example.demo.model.order.DeliveryStatus;
import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface OrderRepository extends JpaRepository<ShopOrder, Integer> {
    Boolean existsShopOrderByUser_LoginAndUser_Password(@NotNull String user_login, @NotNull String user_password);

    ShopOrder getByUser_Login(@NotNull String user_login);

    ShopOrder findByUser_Login(@NotNull String user_login);

    ShopOrder getByUser_PhoneNumber(@NotNull String user_phoneNumber);

    List<ShopOrder> getAllByDeliveryStatus(@NotNull DeliveryStatus deliveryStatus);

    Integer deleteByUser_Login(@NotNull String user_login);

    Integer deleteByUser_PhoneNumber(@NotNull String user_phoneNumber);

    Boolean existsShopOrderByUser(User user);

    Boolean existsByUser(User user);

    ShopOrder getByUser(User user);

    ShopOrder findByUser(User user);

}
