package com.example.petshop.repository;

import com.example.petshop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findById(Integer id);
    List<Message> findBySenderId(Integer userId);
    List<Message> findByReceiverId(Integer userId);
    List<Message> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);
}