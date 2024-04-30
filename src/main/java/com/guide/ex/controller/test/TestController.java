package com.guide.ex.controller.test;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/test")
public class TestController {

//    css, js 연결 테스트
    @GetMapping("/test")
    public void test(){

    }

    @ApiOperation(value = "location input", notes = "M: GET 위도, 경도값")
    @GetMapping("/test_location_input")
    public void test_location_input(){

    }

    @PostMapping("/test_location_input")
    public String test_location_save(HttpRequest req){
        return "redirect:/test_success";
    }



}
