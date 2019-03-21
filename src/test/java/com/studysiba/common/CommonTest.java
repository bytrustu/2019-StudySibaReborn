package com.studysiba.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    @Test
    public void dataValidationTest() {
        System.out.println("이름숫자체크1 : " + DataValidation.checkEngAndNum("bytrustu1202"));
        System.out.println("이름숫자체크2 : " + DataValidation.checkEngAndNum("bytrustu하1202"));
        System.out.println("이름숫자체크3 : " + DataValidation.checkEngAndNum("bytrustu★1202"));

        System.out.println("문자체크1 : " + DataValidation.checkOnlyCharacters("안녕123"));
        System.out.println("문자체크2 : " + DataValidation.checkOnlyCharacters("안녕★123"));
        System.out.println("문자체크3 : " + DataValidation.checkOnlyCharacters("안녕asd123"));

        System.out.println("이메일체크1 : " + DataValidation.checkEmail("bytrustu@gmail.com") );
        System.out.println("이메일체크2 : " + DataValidation.checkEmail("bytrustugmail.com") );
        System.out.println("이메일체크3 : " + DataValidation.checkEmail("bytrustu@gmailcom") );

        System.out.println("문자크기체크1 : " + DataValidation.textLengthComparison(5,"abcd"));
        System.out.println("문자크기체크2 : " + DataValidation.textLengthComparison(5,"abcde"));
        System.out.println("문자크기체크3 : " + DataValidation.textLengthComparison(5,"하하하"));

        System.out.println("이미지파일체크1 : " + DataValidation.checkImageFile("haha.jpg"));
        System.out.println("이미지파일체크2 : " + DataValidation.checkImageFile("haha.GIF"));
        System.out.println("이미지파일체크3 : " + DataValidation.checkImageFile("haha.jsp"));
    }

}
