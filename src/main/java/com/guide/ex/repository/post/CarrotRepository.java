package com.guide.ex.repository.post;

import com.guide.ex.domain.post.Carrot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarrotRepository extends JpaRepository<Carrot, Long> {

    @Query("SELECT c FROM Carrot c JOIN FETCH c.member JOIN FETCH c.postImages WHERE c.isDeleted = false")
    List<Carrot> findAllCarrotsWithPostDetails();
}
