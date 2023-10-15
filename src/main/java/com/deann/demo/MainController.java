package com.deann.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/board")
    @ResponseBody
    public String index() {
          return "게시판 목록";
    }
    @GetMapping("/")
    public String boot() {
        return "redirect:/question/list";
    }

}
