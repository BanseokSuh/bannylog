package com.bannylog.api.controller;

import com.bannylog.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate params) {
        /*
         * 서버에서 파라미터 받는 법
         * 1) @RequestParam
         * 2) Map<String, String>
         * 3) DTO
         *   {
         *      title: "",
         *      content: "",
         *      user: {
         *          id: "",
         *          name: ""
         *      }
         *   }
         */
        log.info("params={}", params.toString());
        return "Post test";
    }
}
