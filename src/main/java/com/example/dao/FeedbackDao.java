package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Feedback;

public interface FeedbackDao extends JpaRepository<Feedback, Integer> {

}