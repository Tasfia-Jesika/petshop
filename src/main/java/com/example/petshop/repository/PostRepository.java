package com.example.petshop.repository;

import com.example.petshop.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository <Post, Integer> {
    Optional<Post> findById(Integer id);
    List<Post> findByUserId(Integer userId);
}
