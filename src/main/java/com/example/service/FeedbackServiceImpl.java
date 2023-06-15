package com.example.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dao.FeedbackDao;
import com.example.entities.Feedback;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{

    private final FeedbackDao feedbackDao;

@Override
@Transactional
public Feedback saveFeedback(Feedback feedback) {
    
    return feedbackDao.save(feedback);

}




@Override

public Feedback findByFeedbackId(int id) {

return feedbackDao.findById(id).get();

}




@Override

public List<Feedback> findAll() {

return feedbackDao.findAll();

}


}


