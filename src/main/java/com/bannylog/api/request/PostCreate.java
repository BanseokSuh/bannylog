package com.bannylog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public final String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    public final String content;


    // @Builder 같은 경우는 클래스보다는 생성자 위에 다는 것이 좋다. 다른 어노테이션, final 키워드와 혼용 시 모순이 발생할 수 있기 때문이다.
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 빌더의 장점
     * - 가독성 좋음
     * - 필요한 값만 받을 수 있음 (빌더 없으면 생성자 오버로딩을 통해 인스턴스 생성 -> 오버로딩 가능한 조건 찾아보기)
     * - **객체의 불변성
     */

    public PostCreate changeTitle(String title) {
        return PostCreate.builder()
                .title(title)
                .content(content)
                .build();
    }
}
