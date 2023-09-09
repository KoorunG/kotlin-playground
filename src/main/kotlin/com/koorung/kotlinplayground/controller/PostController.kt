package com.koorung.kotlinplayground.controller

import com.koorung.kotlinplayground.service.post.PostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/posts")
class PostController (
    val postService: PostService
) {
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) = postService.findById(UUID.fromString(id))
}