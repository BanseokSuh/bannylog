package com.bannylog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostEdit {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    public String content;

    // @Builder 같은 경우는 클래스보다는 생성자 위에 다는 것이 좋다. 다른 어노테이션, final 키워드와 혼용 시 모순이 발생할 수 있기 때문이다.
    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
