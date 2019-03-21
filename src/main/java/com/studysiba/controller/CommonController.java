package com.studysiba.controller;

import com.studysiba.common.DataValidation;
import com.studysiba.service.common.CommonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String moveMain(){
        log.info("move main");
        int test = commonService.getTest();
        log.info(test);

        String test1 = passwordEncoder.encode("안녕하세요");
        log.info(test1);

        return "common/main";
    }

}
