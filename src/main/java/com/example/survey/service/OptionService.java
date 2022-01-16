package com.example.survey.service;

import com.example.survey.model.Options;
import com.example.survey.model.Questions;
import com.example.survey.repos.OptionsRepo;
import com.example.survey.repos.QuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionService {

    @Autowired
    private OptionsRepo optionsRepo;

    @Autowired
    private QuestionsRepo questionsRepo;

    public Options createOption(Options options) {

        Optional<Questions> questions = questionsRepo.findById(options.getQuestions().getId());

        if (questions.isPresent()) {
            if (!questions.get().isAnswered()) {
                optionsRepo.save(options);

            }
        }
        
        return null;
    }

    public List<Options> getAllOptions() {
        return optionsRepo.findAll();
    }

    public Options findOptionById(int id) {
        Optional<Options> options = optionsRepo.findById(id);

        return options.orElse(null);
    }

    public boolean deleteOption(int id) {

        Options options = findOptionById(id);

        if (options != null) {
            Optional<Questions> questions  = questionsRepo.findById(options.getQuestions().getId());

            if (questions.isPresent()) {
                questions.get().getOptions().remove(options);

                optionsRepo.deleteById(id);

                return true;
            }


            return false;
        }

        return false;
    }

    public Options updateOption(Options o) {
        Options option = findOptionById(o.getId());

        if (option != null) {

            Optional<Questions> questions = questionsRepo.findById(option.getQuestions().getId());

            if (questions.isPresent()) {
                if (!questions.get().isAnswered()) {

                    option.setOptionContent(o.getOptionContent());

                    optionsRepo.save(option);

                }
            }

        }

        return option;
    }

}
