package com.guide.ex.controller.post;

import com.guide.ex.dto.PageRequestDTO;
import com.guide.ex.dto.PageResponseDTO;
import com.guide.ex.dto.post.CommentDTO;
import com.guide.ex.service.CommentService;
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
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

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

        Long commentId = commentService.register(commentDTO);

        resultMap.put("commentId", commentId);

        return resultMap;
    }

    @ApiOperation(value = "Read Comment", notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentDTO(@PathVariable("commentId") Long commentId) {
        try {
            CommentDTO commentDTO = commentService.read(commentId);
            return ResponseEntity.ok(commentDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Modify Comment", notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping(value = "/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE )
    public Map<String,Long> remove( @PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO ){

        commentDTO.setCommentId(commentId); //번호를 일치시킴

        commentService.modify(commentDTO);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("commentId", commentId);

        return resultMap;
    }

    @ApiOperation(value = "Delete Comment", notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping("/{commentId}")
    public Map<String,Long> remove( @PathVariable("commentId") Long commentId ){

        commentService.remove(commentId);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("commentId", commentId);

        return resultMap;
    }
}
