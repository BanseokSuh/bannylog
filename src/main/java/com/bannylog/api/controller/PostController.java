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

    /**
     * @RestController
     * - @Controller에 @ResponseBody가 결합된 어노테이션
     * - 컨트롤러 클래스에 @RestController를 붙이면 하위 메서드에 @ResponseBody를 붙이지 않아도 문자열과 JSON 등을 전송할 수 있음
     * - 리턴 값으로 view를 찾지 않고 Json을 바로 전달한다.
     *
     * @RequiredArgsConstructor
     * - final 혹은 NotNull이 붙은 필드에 대해 자동으로 생성자를 만들어주는 Lombok의 어노테이션
     *
     * @RequestBody
     * - 클라이언트에서 request body에 담은 데이터를 자바 객체로 변환시켜주는 어노테이션
     * - 이 때, HttpMessageConverter를 사용한다.
     *
     * @ResponseBody
     * - Http body를 Java 객체로 매핑하여 Client로 리턴
     *
     * @Valid
     * - 빈 검증기를 이용해 객체의 제약 조건을 검증하도록 지시하는 어노테이션
     * - 예를 들어, @Valid는 해당 객체의 프로퍼티에 @NotBlank 어노테이션을 확인하여,
     *   프로퍼티가 빈 값으로 들어오는 것에 대해서 유효성을 검증한다.
     *   유효성 검증을 통과하지 못했을 경우에 리턴할 메시지도 설정할 수 있다.
     * - 컨트롤러에서만 동작한다.
     *
     * @PathVariable
     * - Url에서의 path param으로 전달받은 값을 메서드의 파라미터로 받을 수 있게 하는 어노테이션
     *
     * @ModelAttribute
     * - Client에서 전달하는 값을 오브젝트 형태로 매핑해주는 어노테이션
     *
     * *@RequestBody와 @ModelAttribute의 차이
     * @RequestBody
     * - 요청 본문의 JSON, XML, Text 등의 데이터를 HttpMessageConverter를 통해 파싱되어 Java 객체로 변환
     * - 필드를 바인딩할 생성자나 setter는 필요 없다.
     * - 하지만 기본 생성자는 필수, getter나 setter 중 1가지는 정의되어 있어야 함
     *
     * @ModelAttribute
     * - Query param 혹은 Form 형태의 데이터만을 처리
     * - Json 형식의 데이터를 전송하면 415 Unsupported Media Type 에러 발생
     * - contentType을 x-www-form-url-encoded로, 요청 본문 내용을 Form 형식으로 보내면 정상 동작
     * - 필드를 바인딩할 생성자 혹은 setter가 필요
     *
     * @Autowired
     * - 필요한 의존 객체의 타입에 해당하는 빈 찾아 주입
     * - 생성자 주입, 세터 주입, 필드 주입을 통해 DI를 구현할 수 있음
     * - 생성자 주입이 recommended
     */

    private final PostService postService;

    /**
     * 글 생성
     * @param request
     */
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // 데이터를 꺼내서 검증하는 것은 좋지 않음
//        if (request.getTitle().contains("바보")) {
//            throw new InvalidRequest();
//        }
        request.validate();

        // Case1. 저장한 데이터 Entity -> respons로 응답
        // Case2. postId만 리턴 => Client에서는 해당 id로 글 조회 api를 통해 데이터 수신
        // Case3. 응답 필요 없음 (best), Clent에서 데이터를 잘 관리함
        postService.write(request);
    }

    /**
     * 글 단건 조회
     * @param postId
     * @return postResponse
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     * 글 목록 조회(검색 + 페이징)
     * @param postSearch
     * @return List<PostResponse>
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    /**
     * 글 수정
     * @param postId
     * @param request
     */
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    /**
     * @글 삭제
     * @param postId
     */
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
