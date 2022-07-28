package com.example.petshop.repository;

import com.example.petshop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Integer> {
    Optional<Comment> findById(Integer id);
    Optional<Comment> findByUserId(Integer userId);
    Optional<Comment> findByPostId(Integer postId);
}
