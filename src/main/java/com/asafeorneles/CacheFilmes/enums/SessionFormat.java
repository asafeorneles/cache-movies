package com.asafeorneles.CacheFilmes.enums;

import lombok.Getter;

@Getter
public enum SessionFormat {
    D2("2D"),
    D3("3D");

    final String format;

    SessionFormat(String format){
        this.format = format;
    }
}
