package com.example.demo.repsitory;

import com.example.demo.model.users.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

    Boolean existsByLoginAndPassword(@NotNull String login, @NotNull String password);

    Courier getByLogin(@NotNull String login);

    Integer deleteByLogin(@NotNull String login);

}
