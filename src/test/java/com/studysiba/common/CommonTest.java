package com.studysiba.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {


    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    //@Test
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

    //@Test
    public void dataEncrypt() {
        String encode1 = passwordEncoder.encode("test");
        String encode2 = passwordEncoder.encode("안녕히가세요");

        System.out.println("인코딩 데이터1 : " + encode1);
        System.out.println("인코딩 데이터2 : " + encode2);

        System.out.println("데이터 매칭1-1 : "  + passwordEncoder.matches("안녕하세요",encode1));
        System.out.println("데이터 매칭1-2 : "  + passwordEncoder.matches("안녕히가세요",encode1));
        System.out.println("데이터 매칭1-3 : "  + passwordEncoder.matches("어서오세요",encode1));

        System.out.println("데이터 매칭2-1 : "  + passwordEncoder.matches("안녕하세요",encode2));
        System.out.println("데이터 매칭2-2 : "  + passwordEncoder.matches("안녕히가세요",encode2));
        System.out.println("데이터 매칭2-3 : "  + passwordEncoder.matches("어서오세요",encode2));
    }

    //@Test
    public void DataConvensionTest(){
        System.out.println("UUID 생성1 : " + DataConversion.returnUUID());
        System.out.println("UUID 생성2 : " + DataConversion.returnUUID());
        System.out.println("UUID 생성3 : " + DataConversion.returnUUID());

        System.out.println("랜덤번호 생성1 : " + DataConversion.returnRanNum(50));
        System.out.println("랜덤번호 생성2 : " + DataConversion.returnRanNum(5));
        System.out.println("랜덥번호 생성3 : " + DataConversion.returnRanNum(10));
    }


}
