package com.guide.ex.repository;

import com.guide.ex.domain.Carrot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrotRepository extends JpaRepository<Carrot, Long> {

}
