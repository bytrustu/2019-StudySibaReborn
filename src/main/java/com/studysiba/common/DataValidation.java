package com.studysiba.common;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class DataValidation {


    /*
     *  VO객체 내부의 원하는 변수만 NULL 및 공백 체크 순회
     *  @Param VO객체, 변수명 배열
     *  @Return NULL 값이 있을경우 해당 변수명 , 모두 NULL이 아닐경우 VALUES_STATE_GOOD 반환
     */
    public static String findEmptyValue(Object obj, String[] valueNames) throws Exception {
        String stateCode = "VALUES_STATE_GOOD";
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            for (String str : valueNames) {
                if (field.getName().equals(str)) {
                    if (field.get(obj) == null || field.get(obj).toString().trim().equals("")) {
                        stateCode = str;
                        break;
                    }
                }
            }
        }
        return stateCode;
    }


    /*
     *  영어 + 숫자 만 포함된 문자 체크
     *  @Param 텍스트
     *  @Return 문자열 검증
     */
    public static boolean checkEngAndNum(String text) {
        String regExp = "^[a-z|0-9]*$";
        return Pattern.matches(regExp, text);
    }


    /*
     *  한글 + 영어 + 숫자 만 포함된 문자 체크
     *  @Param 텍스트
     *  @Return 문자열 검증
     */
    public static boolean checkOnlyCharacters(String text) {
        String regExp = "^[ㄱ-ㅎ가-힣a-z0-9]*$";
        return Pattern.matches(regExp, text);
    }


    /*
     *  아이디@주소.국가코드
     *  @Param 이메일
     *  @Return 이메일 검증
     */
    public static boolean checkEmail(String email) {
        String regExp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return Pattern.matches(regExp, email);
    }

    /*
     *  영어 숫자  포함 5-16 길이 체크
     *  @Param 문자열길이
     *  @Return 비밀번호 검증
     */
    public static boolean checkPassword(String password) {
        String regExp = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{5,16}$";
        return Pattern.matches(regExp, password);
    }


    /*
     *  그림파일 확장자 확인 [ JPG, JPEG, PNG, GIF, BMP ]
     *  @Param 파일이름
     *  @Return 그림파일 확장자 검증
     */
    public static boolean checkImageFile(String fileName) {
        boolean result = false;
        int post = fileName.lastIndexOf(".");
        String ext = fileName.substring(post + 1).toLowerCase();
        String[] images = {"jpg", "jpeg", "png", "gif", "bmp"};
        for (String str : images) {
            if (str.equals(ext)) {
                result = true;
                break;
            }
        }
        return result;
    }


    /*
     *  문자열 크기 체크
     *  @Param 크기, 텍스트
     *  @Return 크기와 텍스트의 크기를 검증
     */
    public static boolean textLengthComparison(int length, String str) {
        char[] array = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        int size = 0;
        boolean result = true;
        for (char c : array) {
            size += (c > 255) ? 2 : 1;
            sb.append(c);
        }
        if (size > length) result = false;
        return result;
    }


}
