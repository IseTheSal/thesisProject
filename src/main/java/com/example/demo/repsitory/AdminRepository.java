package com.example.demo.repsitory;

import com.example.demo.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Boolean existsByLogin(@NotNull String login);

    Boolean existsAdminByLoginAndPassword(@NotNull String login, @NotNull String password);
}
