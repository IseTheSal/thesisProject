package com.example.demo.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_moderator", schema = "finalshop")
public class Moderator {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "moderator_id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "login", insertable = false, updatable = false)
    private String login;

    @NotNull
    @Column(name = "login")
    private String password;
}
