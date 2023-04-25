package com.bannylog.api.service;

import com.bannylog.api.domain.Post;
import com.bannylog.api.repository.PostRepository;
import com.bannylog.api.request.PostCreate;
import com.bannylog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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
        /*
          Controller -> WebPostService(response를 위한 작업) -> Repository
                        PostService(외부 service와 통신하기 위한 작업)
         */
    }

    public List<PostResponse> getList() {
//        return postRepository.findAll().stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
//                .collect(Collectors.toList());
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

//    public Post getRss(Long id) {
//        return postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
//    }
}
