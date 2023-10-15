package com.deann.demo.Answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
    @NotEmpty(message = "内容は必須です")
    /*
    Problem : 답변 등록을 할시 답변을 작성했으나,등록이안됌
    @NotEmpty를 주석처리하니까 답변이 등록이되나,등록한 답변이 보이질않음
    TODO : 등록한 답변이 보이게끔 할것
    */


    private String content;
}
