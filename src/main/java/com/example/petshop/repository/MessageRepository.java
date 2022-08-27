package com.example.petshop.repository;

import com.example.petshop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Optional<Message> findById(Integer id);
    List<Message> findBySenderId(Integer userId);
    List<Message> findByReceiverId(Integer userId);
    List<Message> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);

    @Query(value="SELECT m FROM Message m WHERE (m.senderId = ?1 AND m.receiverId = ?2) OR (m.senderId = ?2 AND m.receiverId = ?1)")
    List<Message> findAllTheMessagesOfSpecificSenderAndReceiever(Integer senderId, Integer receiverId);
}