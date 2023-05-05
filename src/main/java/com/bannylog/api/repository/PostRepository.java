package com.bannylog.api.repository;

import com.bannylog.api.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 인터페이스 다중 상속
 * 자바 클래스는 하나의 부모 클래스를 상속할 수 있지만,
 * 인터페이스는 다중 상속이 가능하다.
 *
 * 인터페이스는 메서드를 선언만 하기 때문에 충돌의 여지가 없어서 다중 상속이 가능
 */
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
