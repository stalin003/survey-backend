package com.example.survey.repos;

import com.example.survey.DTO.ResidentRespondDTO;
import com.example.survey.model.AnsweredQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AQRepo extends JpaRepository<AnsweredQuestions, Integer> {

    Optional<AnsweredQuestions> findByUserIdAndQuestionId(UUID userId, int questionId);
    Optional<List<AnsweredQuestions>> findByQuestionId(int questionId);

    @Query("SELECT AQ.questionId, count(AQ.id) FROM AnsweredQuestions AQ group by AQ.questionId order by AQ.questionId")
    List<String> residentRespondedQuestion();

    @Query(value = "SELECT option_id, count(*) as votes FROM survey.answered_questions where question_id = :id group by option_id order by option_id;", nativeQuery = true)
    List<String> getOptionVotes(@Param("id") String questionId);
}
