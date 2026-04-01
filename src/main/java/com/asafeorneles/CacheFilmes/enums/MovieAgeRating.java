package com.asafeorneles.CacheFilmes.enums;

import lombok.Getter;

@Getter
public enum MovieAgeRating {
    L("L"),
    M_10("+10"),
    M_12("+12"),
    M_14("+14"),
    M_16("+16"),
    M_18("+18");

    String ageRating;

    MovieAgeRating(String ageRating){
        this.ageRating = ageRating;
    }
}
