package com.studysiba.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class DataConversion {

    // UUID 생성
    public static String returnUUID() {
        return UUID.randomUUID().toString();
    }

    // 범위 번호 생성
    public static int returnRanNum(int range) {
        return (int) (Math.random() * range) + 1;
    }

    // 지금부터 지난 기간
    public static String DurationFromNow(Timestamp date) {
        int sec = 60;
        int min = 60;
        int hour = 24;
        int day = 30;
        int month = 12;
        long currTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (currTime - regTime) / 1000;
        String message = null;
        if (diffTime < sec) {
            message = "방금 전";
        } else if ((diffTime /= sec) < min) {
            message = diffTime + "분 전";
        } else if ((diffTime /= min) < hour) {
            message = diffTime + "시간 전";
        } else if ((diffTime /= hour) < day) {
            message = diffTime + "일 전";
        } else if ((diffTime /= day) < month) {
            message = diffTime + "달 전";
        } else {
            message = diffTime + "년 전";
        }
        return message;
    }

    // 현재날짜
    public static Timestamp currentTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = null;
        today = formatter.format(cal.getTime());
        Timestamp ts = Timestamp.valueOf(today);
        return ts;
    }

    // < > 문자 변경
    public static String changeSpChar(String str) {
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        return str;
    }

    public static String changeOriginTag(String str) {
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        str = str.replaceAll("<script>","&lt;script&gt;");
        str = str.replaceAll("</script>","&lt;/script&gt;");
        return str;
    }

}
