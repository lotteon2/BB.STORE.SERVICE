package kr.bb.store.domain.question.controller.response;

import kr.bb.store.domain.question.dto.QuestionForOwnerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsForOwnerPagingResponse {
    private List<QuestionForOwnerDto> data;
    private Long totalCnt;
}