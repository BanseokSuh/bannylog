package com.bannylog.api.controller;

import com.bannylog.api.request.PostCreate;
import com.bannylog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // Case1. 저장한 데이터 Entity -> respons로 응답
        // Case2. postId만 리턴 => Client에서는 해당 id로 글 조회 api를 통해 데이터 수신
        // Case3. 응답 필요 없음 (best), Clent에서 데이터를 잘 관리함
        postService.write(request);
    }
}
