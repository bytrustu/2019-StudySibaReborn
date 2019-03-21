package com.studysiba.common;

import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

public class DataValidation {

    // 소문자 숫자 포함 검증
    public static boolean checkEngAndNum(String text) {
        String regExp = "^[a-z0-9]*$";
        return Pattern.matches(regExp, text);
    }

    // 한글 영어 숫자 포함 검증
    public static boolean checkOnlyCharacters(String text) {
        String regExp = "^[ㄱ-ㅎ가-힣a-z0-9]*$";
        return Pattern.matches(regExp, text);
    }

    // 이메일 검증
    public static boolean checkEmail(String email) {
        String regExp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        return Pattern.matches(regExp, email);
    }

    // 이미지 파일 검증 [ JPG, JPEG, PNG, GIF, BMP ]
    public static boolean checkImageFile(String fileName) {
        boolean result = false;
        int post = fileName.lastIndexOf(".");
        String ext = fileName.substring( post + 1 ).toLowerCase();
        String[] images = {"jpg", "jpeg","png","gif","bmp"};
        for ( String str : images ) {
            if ( str.equals(ext) ) {
                result = true;
                break;
            }
        }
        return result;
    }

    // 문자열 크기를 비교해서 결과 반환
    public static boolean textLengthComparison(int length, String str) {
        char[] array = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        int size = 0;
        boolean result = true;
        for ( char c : array ) {
            size += ( c > 255 ) ? 2: 1;
            sb.append(c);
        }
        if ( size >= length ) result = false;
        return result;
    }
}
