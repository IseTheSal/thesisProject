package com.example.demo.repsitory;

import com.example.demo.model.users.Moderator;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    Boolean existsByLoginAndPassword(@NotNull String login, @NotNull String password);

    Integer deleteByLogin(@NotNull String login);

    Moderator getByLogin(@NotNull String login);
}
