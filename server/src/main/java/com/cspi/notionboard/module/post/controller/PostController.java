package com.cspi.notionboard.module.post.controller;

import com.cspi.notionboard.module.post.dto.PostDto;
import com.cspi.notionboard.module.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return ResponseEntity.ok("Post is Created");
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostDto patchDto, @PathVariable Long postId) {
        postService.patchPost(postId, patchDto);
        return ResponseEntity.ok("Post is Patched");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestBody String password) {
        postService.deletePost(postId, password);
        return ResponseEntity.ok("Post is Deleted");
    }
}
