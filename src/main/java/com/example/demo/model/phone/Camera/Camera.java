package com.example.demo.model.phone.Camera;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "camera", schema = "finalshop")
public class Camera {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "camera_id", insertable = false, updatable = false)
    private UUID id;


    //    @OneToMany(fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.JOIN)
    @OneToMany(
            mappedBy = "camera",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Lens> lensList;

    public List<Lens> getLens() {
        return lensList;
    }
}
