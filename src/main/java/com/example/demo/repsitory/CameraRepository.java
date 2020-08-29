package com.example.demo.repsitory;

import com.example.demo.model.phone.Camera.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraRepository extends JpaRepository<Camera, Integer> {

}
