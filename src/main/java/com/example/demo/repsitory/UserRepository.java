package com.example.demo.repsitory;

import com.example.demo.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsUserByLoginAndPassword(@NotNull String login, @NotNull String password);

    User getUserByLogin(@NotNull String login);

    Integer deleteByLogin(@NotNull String login);

    Boolean existsByPhoneNumber(@NotNull String phoneNumber);

    User getByPhoneNumber(@NotNull String phoneNumber);

}
