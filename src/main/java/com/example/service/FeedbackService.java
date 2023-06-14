package com.example.service;

import java.util.List;
import com.example.entities.Feedback;

public interface FeedbackService {
    public List<Feedback> findAll();
    public Feedback saveFeedback(Feedback feedback);
    public Feedback findByFeedbackId(int id);

}
