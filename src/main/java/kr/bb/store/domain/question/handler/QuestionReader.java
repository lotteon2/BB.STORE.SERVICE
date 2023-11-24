package kr.bb.store.domain.question.handler;

import kr.bb.store.domain.question.dto.AnswerDto;
import kr.bb.store.domain.question.controller.response.QuestionDetailInfoResponse;
import kr.bb.store.domain.question.entity.Answer;
import kr.bb.store.domain.question.entity.Question;
import kr.bb.store.domain.question.exception.QuestionNotFoundException;
import kr.bb.store.domain.question.repository.AnswerRepository;
import kr.bb.store.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QuestionReader {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionDetailInfoResponse readDetailInfo(Long questionId, String nickname, String productName) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
        question.check();

        Answer answer = answerRepository.findById(questionId)
                .orElse(null);

        return QuestionDetailInfoResponse.builder()
                .title(question.getTitle())
                .nickname(nickname)
                .createdAt(question.getCreatedAt())
                .productName(productName)
                .content(question.getContent())
                .isReplied(answer != null)
                .answer(answer == null ? null : AnswerDto.fromEntity(answer))
                .build();
    }
}