package com.example.demo.model.order;

import com.example.demo.model.phone.Phone;
import com.example.demo.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoporder", schema = "finalshop")
public class ShopOrder {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "order_id", insertable = false, updatable = false)
    private UUID id;


    //    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_user_id")
    private User user;

    //    @NotNull
    @OneToMany(
            mappedBy = "shopOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Phone> phoneList;

    @NotNull
    @Column(name = "deliverystatus")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
