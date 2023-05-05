package com.bannylog.api.response;

import com.bannylog.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 클래스
 */

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    // 생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    // client 요구사항
    // json 응답에서 title 값의 길이를 최대 10글자로 해주세요. -> 응답 클래스 분리
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
