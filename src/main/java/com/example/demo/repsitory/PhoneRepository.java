package com.example.demo.repsitory;

import com.example.demo.model.phone.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    Phone findBySerialNumber(@NotNull String serialNumber);

    Boolean existsBySerialNumber(@NotNull String serialNumber);

    Boolean existsAllBySerialNumber(@NotNull String serialNumber);

    Integer deletePhoneBySerialNumber(@NotNull String serialNumber);

}
