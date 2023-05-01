package com.bannylog.api.repository;

import com.bannylog.api.domain.Post;
import com.bannylog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
