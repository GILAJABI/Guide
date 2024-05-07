package com.guide.ex.service;

import com.guide.ex.domain.post.Post;
import com.guide.ex.dto.post.PostDTO;
import com.guide.ex.repository.search.AllPostSearch;
import com.guide.ex.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@Log4j2
//@Transactional    롤백을 적용함으로써 보다 각 테스트 별 독립적인 실행을 보장
public class PostServiceTests {

    @Autowired
    private PostService postService;

}
