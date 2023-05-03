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

    private final PostRepository postRepository;

    /*
          Controller -> WebPostService(response를 위한 작업) -> Repository
                        PostService(외부 service와 통신하기 위한 작업)
     */

    public void write (PostCreate postCreate) {
         Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
//        return postRepository.findAll().stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
//                .collect(Collectors.toList());

        // one-indexed-parameters: true
        // web -> page 1일 경우 내부적으로 0으로 바꿈
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    // 글이 너무 많은 경우
    // 비용 많이, DB 뻗을 수 있음, 애플리케이션 서버 시간 및 트래픽 발생

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);
    }
}
