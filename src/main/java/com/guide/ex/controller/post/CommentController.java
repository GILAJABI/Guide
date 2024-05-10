package com.guide.ex.controller.post;

import com.guide.ex.dto.post.CommentDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/comment")
@Log4j2
public class CommentController {
    @ApiOperation(value = "Comment Post", notes = "POST 방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> register(
                @Valid @RequestBody CommentDTO commentDTO,
                BindingResult bindingResult)throws BindException {
        log.info("register: {}", commentDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("commentId", commentDTO.getCommentId());
        resultMap.put("postId", commentDTO.getPostId());
        resultMap.put("memberId", commentDTO.getMemberId());

        return resultMap;
    }
}
