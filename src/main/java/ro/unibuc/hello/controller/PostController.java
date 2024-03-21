package ro.unibuc.hello.controller;

import javax.management.ConstructorParameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import ro.unibuc.hello.data.PostEntity;
import ro.unibuc.hello.dto.PostDto;
import ro.unibuc.hello.service.PostService;
import org.springframework.http.HttpStatus;
import ro.unibuc.hello.dto.PostDto;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/getPost/{id}")
    @ResponseBody
    public PostEntity getPost(@PathVariable String id) {
        try {
            return postService.getPost(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/addPost")
    @ResponseBody
    public String addPost(@RequestBody PostDto postDto) {
        try {
            return postService.addPost(postDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/registerUserToPost/{userId}/{postId}")
    @ResponseBody
    public String registerUserToPost(@PathVariable String userId, @PathVariable String postId) {
        try {
            return postService.registerUserToPost(userId, postId);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/deletePost/{id}")
    @ResponseBody
    public String deletePostById(@PathVariable String id) {
        try {
            return postService.deletePostById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}