package com.bookstore.v1;

import com.bookstore.v1.controllers.ReviewController;
import com.bookstore.v1.data.ReviewRepository;
import com.bookstore.v1.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class V1ApplicationTests {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewController reviewController;

    @Test
    void contextLoads() {
        assertThat(reviewRepository).isNotNull();
        assertThat(reviewService).isNotNull();
        assertThat(reviewController).isNotNull();
    }

}
