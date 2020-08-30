package com.example.demo.model.phone;


import com.example.demo.model.order.ShopOrder;
import com.example.demo.model.phone.Camera.Camera;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "smartphone",schema = "finalshop")
public class Phone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "phone_id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "serialnumber")
    private String serialNumber;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private Brand brand;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "available")
    private Available available;

    @NotNull
    @Column(name = "model")
    private String model;

    @Min(value = 1)
    @Max(value = 256)
    @Column(name = "memory")
    private int memory;

    @Min(value = 16)
    @Max(value = 1024)
    @Column(name = "storage")
    private int storage;

    @NotNull
//    @Column(name = "camera")
    @OneToOne(fetch = FetchType.EAGER)
    private Camera camera;

    @NotNull
    @Column(name = "microcd")
    private boolean microCD;

    @NotNull
    @Column(name = "minijack")
    private boolean miniJack;

    @Column(name = "faceid")
    private boolean faceId;

    @NotNull
    @Column(name = "fastcharge")
    private boolean fastCharge;

    @NotNull
    @Column(name = "wirelesscharge")
    private boolean wirelessCharge;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "material")
    private Material material;

    @Min(value = 1000)
    @Max(value = 100000)
    @Column(name = "batterycapacity")
    private int batteryCapacity;

    @NotNull
    @Column(name = "warrantyyears")
    private int warrantyYears;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_order_user_user_id")
    private ShopOrder shopOrder;

}
