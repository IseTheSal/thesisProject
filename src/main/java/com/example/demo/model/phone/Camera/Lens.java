package com.example.demo.model.phone.Camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "lens",schema = "finalshop")
public class Lens {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "lens_id", insertable = false, updatable = false)
    private UUID id;


    @Min(value = 3)
    @Max(value = 256)
    @Column(name = "megapixels")
    private int megaPixels;

    @Min(value = 1)
    @Column(name = "maximumzoom")
    private int maximumZoom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "photoresolution")
    private Resolution photoResolution;
//
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "videoresolution")
    private Resolution videoResolution;

    @NotNull
    @Column(name = "nightmode")
    private boolean nightMode;

//        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "lens_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Camera camera;

}
