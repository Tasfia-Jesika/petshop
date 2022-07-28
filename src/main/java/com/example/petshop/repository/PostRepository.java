package com.example.petshop.repository;

import com.example.petshop.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository <Post, Integer> {
    Optional<Post> findById(Integer id);
    Optional<Post> findByUserId(Integer userId);
}
