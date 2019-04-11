package com.studysiba.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
public enum ChatRoomName {

    GROUP("/group"),
    PRIVATE("/private"),
    PUBLIC("/public"),
    ALARM("/alarm");

    private String roomName;

    ChatRoomName(String name) {
        this.roomName = name;
    }

    public static boolean isValidChatRoomName(String value) {
        return Arrays.stream(ChatRoomName.values())
                .anyMatch((chatRoomName) -> chatRoomName.name()
                        .equals(value));
    }


}
