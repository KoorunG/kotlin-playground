package com.koorung.kotlinplayground.service.post

import com.koorung.kotlinplayground.domain.post.Post
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class PostServiceTest @Autowired constructor(
    private val postRepository: PostRepository,
    private val postService: PostService,
){

    @Test
    fun `스프링 추상화 테스트`() {
        // given
        postRepository.save(Post("테스트 제목", "테스트 내용"))

        // when
        val posts = postService.findAll()

        // then
        assertThat(posts).hasSize(1)
        assertThat(posts).extracting("title").containsExactlyInAnyOrder("테스트 제목")
        assertThat(posts).extracting("content").containsExactlyInAnyOrder("테스트 내용")
    }
}