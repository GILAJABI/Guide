package com.guide.ex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
public class ImgController {

    private String uploadPath;

}
