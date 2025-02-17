package com.cspi.notionboard.module.comment.controller;

import com.cspi.notionboard.module.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<Map<String, Object>>> selectAllComments(@PathVariable Long postId) {
        List<Map<String, Object>> allComments = commentService.selectAllComments(postId);
        return ResponseEntity.ok(allComments);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> insertComment(@PathVariable Long postId, @RequestBody Map<String, Object> comment) {
        commentService.insertComment(postId, comment);
        return ResponseEntity.ok("Comment is Created");
    }

    @PostMapping("/{postId}/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Map<String, Object> comment) {
        commentService.updateComment(postId, commentId, comment);
        return ResponseEntity.ok("Comment is Patched");
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody String password) {
        commentService.deleteComment(postId, commentId, password);
        return ResponseEntity.ok("Comment is Deleted");
    }
}
