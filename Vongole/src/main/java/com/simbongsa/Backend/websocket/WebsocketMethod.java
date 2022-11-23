package com.simbongsa.Backend.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebsocketMethod {
    MESSAGE("MESSAGE"),
    ERROR("ERROR");

    private final String method;
}
