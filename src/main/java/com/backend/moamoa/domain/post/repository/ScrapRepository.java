package com.backend.moamoa.domain.post.repository;

import com.backend.moamoa.domain.post.entity.Post;
import com.backend.moamoa.domain.post.entity.Scrap;
import com.backend.moamoa.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndPost(User user, Post post);

}
