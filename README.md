# Spring Boot 게시판 API repository

- Spring Boot를 사용하여 간단한 게시판 API 개발

- 사용 기술
  - Spring Boot
  - Tomcat
  - Gradle
  - Lombok
  - Spring Data JPA
  - H2

<br>

---

# Note

## 초기 설정

- src > main > resources에서 application.properties > application.yml로 확장자 변경 (개인 선호)
- src > main > resources의 static, templates 폴더는 front 작업할 때에 필요할 수 있음

<br>

## Controller

- @Controller - html의 주소를 리턴함
- SSR -> jsp, thymeleaf, mustache, freemarker
  - html rendering
- SPA
  - API(json)
  - Vue -> Nuxt(Vue + SSR)
  - React -> Next(React + SSR)
- @GetMapping
- @PostMapping
- @PutMapping
- @PatchMapping
- @DeleteMapping
- Controller에서 cmd+chift+t 누르면 test controller 생성됨
- Test Contoller class에서 test 함수에는 @Test를 붙여야 함
- @DisplayName() 어노테이션으로 테스트의 이름을 설정할 수 있음
- @Autowired 어노테이션으로 의존성을 주입받음
- mockMvc를 주입받기 위해서는 해당 테스트 Controller에 @WebMvcTest 어노테이션을 기입
- mockMvc
  - 웹 어플리케이션을 서버에 배포하지 않고 테스트용 mvc 환경을 만들어 요청, 전송, 응답기능을 제공해주는 유틸리티 클래스

<br>

## Note

- Http Methods
  - GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

- Lombok
  - 반복되는 메서드를 Annotation을 사용해서 자동으로 작성해주는 Java 라이브러리
  - DTO Class에서 @Getter, @Setter, @ToString 등 기계적으로 작성해야 하는 메서드들을 Annotation으로 작성하여 코드 다이어트가 됨

<br>

## 데이터 검증
### 왜?
1. client 개발자가 깜빡해서 실수로 값을 안 보낼 수 있음
2. client 버그로 값이 누락될 수 있음
3. 외부 나쁜 사람이 값을 임의로 조작해서 보낼 수 있음
4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있음
5. 서버 개발자가 편안해짐


### 검증 방법
1. 각 필드에 대해 검증
  - 좋지 않음. 무언가 3번 이상 반복작업을 할 때 뭔가 잘못된 건 아닌지 확인 필요
  - 생각보다 검증해야할 게 많음
2. DTO 클래스에서 검증
  - Controller에서 해당 매핑되어 있는 메서드에 @Valid 어노테이션을 달아서 DTO에 대한 검증을 실시
  - DTO 클래스에서 각 필드에 @NotBlank을 달면, 빈값 넘어올 시 에러 발생
  - @NotBlank(message = "에러 메시지")로 에러 발생 시의 메시지를 셋팅할 수 있음
  - @NotBlank 어노테이션은 null도 빈문자열 뿐만 아니라 null도 확인해줌
3. BindingResult 클래스를 사용하여 별도의 예외처리를 할 수 있음 -> client에 에러 메시지를 커스텀해서 전달할 수 있음
4. json에 대한 검증 방법
  - jsonPath("$.필드").value("값") 으로 json에 대한 검증이 가능
  - 배열, 객체에 대한 검증 가능

<br>

## 에러 처리
- 예외 처리 클래스(ExceptionController)에 @ControllerAdvice를 달면 Spring Bean으로 주입됨
- BindingResult를 제거하고 요청을 받으면, 에러 발생 시 @ControllerAdvice가 달린 ExceptionController로 요청이 넘어옴
- ExceptionController 내에서 예외 처리
- ExceptionController에서 예외 리턴 시 hashMap이 아닌 응답 클래스(ErrorResponse)로 리턴하는 것이 좋음
- 예외 리턴 시 어떤 필드가 잘못되었는지도 보내주는 것이 좋음
  - 응답 클래스에 해당 정보 추가하는 메서드 정의

<br>

## 데이터 저장
- Service layer를 생성해서 거기에서 repository를 호출하여 데이터 저장하는 것이 좋음
- PostRepository는 interfacd로써 JpaRapository를 상속받음
- PostService에서 PostRepository 주입 시에는 생성자 injection으로 주입할 것
- 직접 생성자를 만들어서 주입할 수도 있지만, final로 생성된 필드는 Lombok을 통해 @RequiredArgsConstructor로 생성자 injection이 가능
- PostController에서 PostService를 주입받을 때도 위와 같이 할 것
- Field injection is not recommended
- Test 코드 외에는 사용하지 않는 것을 추천
- PostController -> PostService -> PostRepository 방향으로 호출, 최종적으로 넘어온 값을 PostEntity으로 변환해서 저장
- ControllerTest에서 어플리케이션의 전반적인 테스트를 할 때는 @WebMvcTest 보다는 @SpringBootTest를 달아야함
- @SpringBootTest를 달면 MockMvc에 대한 빈 주입이 안 됨
- @SpringBootTest와 함께 @AutoConfigureMockMvc를 달면 됨
- 전체 테스트를 돌릴 때 각각 테스트 메서드들이 서로 영향을 주지 않기 위해서는 ControllerTest에서 각 메서드들에서 PostRepository를 초기화해야 함
- @BeforeEach가 붙은 함수는 각각의 테스트 메서드들이 실행되기 전에 실행
```java
@BeforeEach
void clean() {
  postRepository.deleteAll();
}
```

<br>

## 클래스 분리
- ObjectMapper는 Jackson 라이브러리가 제공하는 객체
- Jackson은 객체를 Json으로 변환해주는 라이브러리


### Builder 패턴
- 일반적으로 인스턴스 생성 시에, 생성자의 파라미터 순서를 지켜서 생성함
- 하지만 순서가 바뀔 시에 데이터가 바뀌어서 생성될 수 있음
- Builder 패턴을 사용하면 인스턴스 생성 시에 프로퍼티를 직접 찾아 생성하기 때문에 순서가 바뀌어도 문제 없음
- @Builder는 Lombok에서 제공해줌
- @Builder는 클래스 위에 달 수 있지만, 그렇게 될 경우 클래스의 다른 어노테이션이나 각각의 필드가 final인지 아닌지에 따라 해당 클래스에 모순이 발생해 작동하지 않을 수 있음
- @Builder를 클래스에 달고, 클래스 안에 빈 생성자가 있으면 runtime 에러 발생
- 고로 @Builder는 생성자에 다는 것이 안전함

```java
public class PostCreate {
  private String title;
  private String content;
  
  @Builder
  public PostCreate(String title, String content) {
    this.title = title;
    this.content = content;
  };
};
```

- 일반적 인스턴스 생성
```java
PostCreate postCreate = new PostCreate("제목", "내용"); // 제목과 내용이 바뀌면 바뀐대로 인스턴스 생성됨
```

- Builder 패턴으로 인스턴스 생성
```java
PostCreate postCreate = PostCreate.builder()
  .title("제목")
  .content("내용")
  .build();
```

- 장점
  - 가독성이 좋음
  - 필요한 값만 받을 수 있음
  - 객체의 불변성

<br>

## Test
- ControllerTest와 ServiceTest는 별도로 작성해야 함
- ServiceTest에 @SpringBootTest를 달아야 Service가 주입됨
- 각 테스트 함수는 서로에 간섭하지 않아야 함
- 하나의 테스트 함수에서
  - given: 어떤 데이터가 주어지고,
  - when: MockMvc를 통해 실행했을 때,
  - then: 이런 결과가 나올 것이다
  - 틀을 잡고 하면 마음이 편함
  - expected: (when + then)의 의미
- 인스턴스 생성할 때는 왠만하면 빌더 패턴으로 생성

<br>

## 응답 클래스 분리
- 제목의 10글자만을 리턴해달라는 요구사항이 생긴다면, 엔티티에 getTitle과 같은 getter 메서드에 넣으면 안 됨
- -> 서비스 정책에 맞는 응답 클래스를 분리하는 것이 좋음
- Service에서 빌더 패턴으로 응답 클래스의 인스턴스 생성
- Service layer에서 Entity를 Response class로 변환하는 것이 맞냐는 이야기도 있음. 취향 존중
- requestDto, responseDto를 생성해서 요청, 응답 클래스 나누자

<br>

## 여러 게시글 조회
- control + alt + O => optimizing import
- .stream().map().collect(Collector.toList()); 를 통해 entity -> response class로 변환 
```java

public class PostService {
    ...
    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                  .id(post.getId())
                  .title(post.getTitle())
                  .build())
                .collect(Collector.toList());
    }

```

- 더 간소화한 코드
```java
// 1) postRepository에서 생성자 오버로딩
@Getter
public class PostResponse {
    ...
    // 생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    ...
}

// 2) postService에서는 post를 인자로 넘겨 response class에서 생성자 오버로딩을 통해 entity를 response class로 변환
public class PostService {
    ...
    public List<PostResponse> getList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new) // .map(new PostResponse(post))의 간소화 버전
                .collect(Collectors.toList());
    }
    ...
}

```

<br>

## h2 console
- application.yml 파일에 다음과 같은 설정을 하면 h2 console에 접속할 수 있음
```java
spring:
  h2:
    console:
      enabled: true
      path: /h2-console

  datasource:
    url: jdbc:h2:mem:bannylog //
    username: sa //
    password: //
    driver-class-name: org.h2.Driver //
```

<br>

## Pagination
- 한 번에 글이 너무 많이 조회되면
  - 비용이 많이 듦
  - DB가 뻗을 수 있음
  - 트래픽 비용이 발생함
  -> pagination의 필요성이 충분함
  

- offset: 어디서 부터 가져올지
- limit: 행을 얼마나 가져올지

```SELECT * FROM 테이블명 ORDERS LIMIT 숫자(A) OFFSET 숫자(B)```

- 위 쿼리는 (B+1) 행부터 A 행 만큼 출력함

- cmd + e = 최근 작업한 파일 목록창

- @PageableDefault
  - SpringBoot에서는 page값이 기본 0으로 셋팅
  - @PageableDefault 어노테이션을 사용하면 웹에서 넘어온 page=1이 내부에서 page=0으로 치환되어 첫번째 페이지의 데이터 정상적으로 조회
  - application.yml에서 data > web > pageable > default-page-size = 5로 설정하면 requestParam에 size를 전달하지 않도고 기본 사이즈만큼의 데이터를 조회할 수 있음
  - 혹시 작동하지 않는다면, PostController에 @PageableDefault를 사용하고 있는지 확인하자
  - 한 페이지당 10개씩, 20개씩 보기 기능이 있으면 size를 front에서 전달하는게 맞지만, 보통은 서버에서 관리
  - 별점순, 등록순과 같은 기능이 있다면 RequestParam 그에 따라 달라짐
  - 인덱싱을 고려해야 함

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>



