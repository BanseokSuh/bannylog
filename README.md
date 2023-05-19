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


<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

