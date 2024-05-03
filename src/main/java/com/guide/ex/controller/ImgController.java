package com.guide.ex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
public class ImgController {

    @Value("${com.guide.upload.path}")// import 시에 springframework으로 시작하는 Value
    private String uploadPath;




}
