package com.cspi.notionboard.module.post.controller;

import com.cspi.notionboard.module.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        List<Map<String, Object>> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> selectPost(@PathVariable Long postId) {
        Map<String, Object> post = postService.selectPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Map<String, Object>>> searchedPosts(@PathVariable String keyword) {
        List<Map<String, Object>> posts = postService.searchedPosts(keyword);
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
