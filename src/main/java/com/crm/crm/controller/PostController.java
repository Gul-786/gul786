package com.crm.crm.controller;

import com.crm.crm.entity.Post;
import com.crm.crm.repository.CommentRepository;
import com.crm.crm.repository.PostRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public String createPost(
            @RequestBody Post post
    ) {
     postRepository.save(post);
     return "Post created successfully!";
    }





}
