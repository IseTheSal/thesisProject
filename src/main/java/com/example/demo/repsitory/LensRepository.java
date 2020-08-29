package com.example.demo.repsitory;

import com.example.demo.model.phone.Camera.Lens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LensRepository extends JpaRepository<Lens, Integer> {
}
