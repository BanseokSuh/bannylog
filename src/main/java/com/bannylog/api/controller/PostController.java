package com.bannylog.api.controller;

import com.bannylog.api.request.PostCreate;
import com.bannylog.api.request.PostEdit;
import com.bannylog.api.request.PostSearch;
import com.bannylog.api.response.PostResponse;
import com.bannylog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     * 여러 글 조회
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    // 서비스 정책에 맞는 응답 클래스 생성하자
//    @GetMapping("/posts/{postId}/rss")
//    public Post getRss(@PathVariable(name = "postId") Long id) {
//        return postService.getRss(id);
//    }
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

}
