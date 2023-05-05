package com.bannylog.api.service;

import com.bannylog.api.domain.Post;
import com.bannylog.api.domain.PostEditor;
import com.bannylog.api.repository.PostRepository;
import com.bannylog.api.request.PostCreate;
import com.bannylog.api.request.PostEdit;
import com.bannylog.api.request.PostSearch;
import com.bannylog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    /**
     * Controller -> WebPostService(response를 위한 작업)       -> Repository
     *               PostService(외부 service와 통신하기 위한 작업)
     */

    private final PostRepository postRepository;

    /**
     * 글 쓰기
     * @param postCreate
     */
    public void write (PostCreate postCreate) {
         Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    /**
     * 글 단건 조회
     * @param id
     * @return postResponse
     */
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    /**
     * 글 목록 조회(검색 + 페이징)
     * @param postSearch
     * @return List<PostResponse>
     */
    public List<PostResponse> getList(PostSearch postSearch) {
        // application.yml 파일에서 one-indexed-parameters: true로 설정하면
        // web에서 page를 1로 넘겨줄 경우 내부적으로 0으로 바꿈

        // 글이 너무 많은 경우
        // - 비용이 많이 듦
        // - DB 뻗을 수 있음
        // - 애플리케이션 서버 시간 및 트래픽 발생
        // -> 페이징 처리
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 글 수정
     * @param id
     * @param postEdit
     */
    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        /**
         * PostEditor 사용 이유
         * - 필드가 늘어날 경우에 처리하기 어렵기 때문에
         * - 변경하고 싶지 않은 필드를 null로 전달했을 때, 해당 필드는 원래 값으로 유지하기 위해서
         */
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
//        post.edit(
//                postEdit.getTitle() != null ? postEdit.getTitle() : post.getTitle(),
//                postEdit.getContent() != null ? postEdit.getContent() : post.getTitle()
//        );
    }

}
