package com.example.demo.service;

import com.example.demo.model.phone.Phone;
import com.example.demo.repsitory.CameraRepository;
import com.example.demo.repsitory.LensRepository;
import com.example.demo.repsitory.PhoneRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final CameraRepository cameraRepository;
    private final LensRepository lensRepository;

    public HttpStatus createPhone(Phone phone) {
        if (!phoneRepository.existsBySerialNumber(phone.getSerialNumber())) {
            lensRepository.saveAll(phone.getCamera().getLens());
            cameraRepository.save(phone.getCamera());
            phoneRepository.save(phone);
            return HttpStatus.OK;
        } else return HttpStatus.BAD_REQUEST;
    }

    public HttpStatus createPhone(List<Phone> phones) {
        for (Phone phone : phones) {
            if (!phoneRepository.existsBySerialNumber(phone.getSerialNumber())) {
                lensRepository.saveAll(phone.getCamera().getLens());
                cameraRepository.save(phone.getCamera());
                phoneRepository.save(phone);
            }
        }
        return HttpStatus.OK;
    }
}