package kr.bb.store.domain.question.controller;

import kr.bb.store.domain.question.controller.request.AnswerCreateRequest;
import kr.bb.store.domain.question.controller.request.QuestionCreateRequest;
import kr.bb.store.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/questions")
    public ResponseEntity createQuestion(@RequestBody QuestionCreateRequest questionCreateRequest) {
        // TODO : header값으로 바꾸기
        Long userId = 1L;
        questionService.createQuestion(userId, questionCreateRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity getQuestionDetail(@PathVariable Long questionId) {
        return ResponseEntity.ok().body(questionService.getQuestionInfo(questionId));
    }

    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity createAnswer(@PathVariable Long questionId,
                                         @RequestBody AnswerCreateRequest answerCreateRequest) {
        questionService.createAnswer(questionId, answerCreateRequest.getContent());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{storeId}/questions/is-replied/{isReplied}")
    public ResponseEntity storeQuestions(@PathVariable Long storeId, @PathVariable Boolean isReplied, Pageable pageable) {
        return ResponseEntity.ok().body(questionService.getQuestionsForStoreOwner(storeId,isReplied,pageable));
    }

    @GetMapping("/questions/product/{productid}/is-replied/{isReplied}")
    public ResponseEntity productQuestions(@PathVariable Long productId, @PathVariable Boolean isReplied, Pageable pageable) {
        // TODO : header값으로 바꾸기
        Long userId = 1L;
        return ResponseEntity.ok().body(questionService.getQuestionsInProduct(userId, productId, isReplied, pageable));
    }

}
