package com.example.petshop.repository;

import com.example.petshop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Integer> {
    Optional<Comment> findById(Integer id);
    List<Comment> findByUserId(Integer userId);
    List<Comment> findByPostId(Integer postId);
}
