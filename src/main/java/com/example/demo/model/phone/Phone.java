package com.example.demo.model.phone;


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
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    private String serialNumber;

    @NonNull
    private Brand brand;

    @NotNull
    private Available available;

    @NotNull
    private String model;

    @Min(value = 1)
    @Max(value = 256)
    private int memory;

    @Min(value = 16)
    @Max(value = 1024)
    private int storage;

    @NotNull
    @OneToOne
    private Camera camera;

    @NotNull
    private boolean microCD;

    @NotNull
    private boolean miniJack;

    private boolean faceId;

    @NotNull
    private boolean fastCharge;

    @NotNull
    private boolean wirelessCharge;

    @NotNull
    private Material material;

    @Min(value = 1000)
    @Max(value = 100000)
    private int batteryCapacity;

    @NotNull
    private int warrantyYears;

}
