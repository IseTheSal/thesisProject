package com.example.demo.model.order;

import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrder {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    //@Column(name = "id", updatable = false, nullable = false)

    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    private User user;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Phone> phoneList;

    @NotNull
    private DeliveryStatus deliveryStatus;
}
