package com.example.petshop.repository;

import com.example.petshop.model.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository <UserDetailsModel, Integer> {
    Optional<UserDetailsModel> findById(Integer id);
    Optional<UserDetailsModel> findByUserId(Integer userId);
    Optional<UserDetailsModel> findByPhoneNumber(String phoneNumber);
}
