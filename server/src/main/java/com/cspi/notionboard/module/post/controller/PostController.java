package com.cspi.notionboard.module.post.controller;

import com.cspi.notionboard.module.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> insertPost(@RequestBody Map<String, Object> post) {
        postService.insertPost(post);
        return ResponseEntity.ok("Post is Created");
    }

    @GetMapping
    public ResponseEntity<Page<Map<String, Object>>> getAllPosts(@PageableDefault(page = 0, size = 10)Pageable pageable) {
        Page<Map<String, Object>> allPosts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> selectPost(@PathVariable Long postId,
                                                          @PageableDefault(page = 0, size = 10)Pageable pageable) {
        Map<String, Object> post = postService.selectPost(postId, pageable);

        return ResponseEntity.ok(post);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<Page<Map<String, Object>>> searchedPosts(@PathVariable String keyword,
                                                             @PageableDefault(page = 0, size = 10)Pageable pageable) {
        Page<Map<String, Object>> posts = postService.searchedPosts(pageable, keyword);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody Map<String, Object> post, @PathVariable Long postId) {
        postService.updatePost(postId, post);
        return ResponseEntity.ok("Post is Patched");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestBody String password) {
        postService.deletePost(postId, password);
        return ResponseEntity.ok("Post is Deleted");
    }
}
