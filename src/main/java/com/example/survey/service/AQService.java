package com.example.survey.service;

import com.example.survey.DTO.AnsweredQuestionDTO;
import com.example.survey.DTO.OptionVotesDTO;
import com.example.survey.DTO.ResidentRespondDTO;
import com.example.survey.model.AnsweredQuestions;
import com.example.survey.model.Options;
import com.example.survey.model.Questions;
import com.example.survey.model.Users;
import com.example.survey.repos.AQRepo;
import com.example.survey.repos.OptionsRepo;
import com.example.survey.repos.QuestionsRepo;
import com.example.survey.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AQService {

    @Autowired
    private AQRepo repo;

    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private OptionsRepo optionsRepo;

    @Autowired
    private UserRepo userRepo;

    public AnsweredQuestions createAQ(AnsweredQuestionDTO answeredQuestionDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        Optional<Users> users = userRepo.findByEmail(email);

        AnsweredQuestions AQ = new AnsweredQuestions();

        if (users.isEmpty()) {
            return null;
        }

        AQ.setOptionId(answeredQuestionDTO.getOptionId());
        AQ.setQuestionId(answeredQuestionDTO.getQuestionId());
        AQ.setUserId(users.get().getId());


        log.info("logged in user: " + email);


        if (isAlreadyAnswered(AQ)) {
            return null;
        }

        Optional<Questions> questions = questionsRepo.findById(AQ.getQuestionId());
        Optional<Options> options = optionsRepo.findById(AQ.getOptionId());

        if (questions.isPresent() && options.isPresent()) {

            Questions q = questions.get();

            if (!q.isAnswered()) {
                q.setAnswered(true);

                questionsRepo.save(q);
            }

            return repo.save(AQ);
        }

        return null;

    }

    public boolean isAlreadyAnswered(AnsweredQuestions AQ) {
        return repo.findByUserIdAndQuestionId(AQ.getUserId(), AQ.getQuestionId()).isPresent();
    }

    public List<AnsweredQuestions> getAllAQ() {
        return repo.findAll();
    }

    public List<ResidentRespondDTO> getCountOfResidentResponse() {
        List<String> result = repo.residentRespondedQuestion();

        List<ResidentRespondDTO> respondDTOList = new ArrayList<>();

        for (int i=0; i < result.size(); i++) {
            List<String> elementList = Arrays.asList(result.get(i).replaceAll("\\s*,\\s*", ",").split(","));

            respondDTOList.add(new ResidentRespondDTO(Integer.parseInt(elementList.get(0)), Integer.parseInt(elementList.get(1))));
        }

        return respondDTOList;
    }

    public List<OptionVotesDTO> getOptionVotes(String questionId) {

        List<String> result = repo.getOptionVotes(questionId);

        List<OptionVotesDTO> optionVotesDTOList = new ArrayList<>();

        for (int i=0; i < result.size(); i++) {
            List<String> elementList = Arrays.asList(result.get(i).replaceAll("\\s*,\\s*", ",").split(","));

            optionVotesDTOList.add(new OptionVotesDTO(Integer.parseInt(elementList.get(0)), Integer.parseInt(elementList.get(1))));
        }

        return optionVotesDTOList;
    }

}
