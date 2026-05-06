package com.yychat.model;

public interface MessageType {
    String LOGIN_VALIDATE_SUCCESS = "1";
    String LOGIN_VALIDATE_FAILURE = "2";
    String COMMON_CHAT_MESSAGE = "3";
    String REQUEST_ONLINE_FRIEND = "4";
    String RESPONSE_ONLINE_FRIEND = "5";
    String NEW_ONLINE_TO_ALL_FRIEND = "6";
    String NEW_ONLINE_FRIEND = "7";
}