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
- @RestController - json 데이터를 리턴
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
<br>
<br>
<br>
<br>
<br>
<br>

