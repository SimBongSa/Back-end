package com.simbongsa.Backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tag {

    // category
    CHILD("어린이"), DISABLED("장애인"), SENIOR("노인"),
    MULTICULTURAL_FAMILY("다문화가정"), ENVIRONMENT("환경"), ABANDONED_ANIMAL("유기동물"),

    // conditions
    ADULT("성인 우대"), MALE("남성 우대"), FEMALE("여성 우대"),
    KIND("친절한 분 우대"), MILITARY("군필자 우대"),

    // skills
    PLAY_THE_INSTRUMENT("악기 연주 가능하신 분"), FUNNY("유쾌한 분"),LIKE_CHILD("아이를 좋아하는 분"),
    GOOD_AT_CLEANING("청소에 일가견이 있으신 분"),ACTIVE("활동적인 분"),CAREFUL("꼼꼼한 분"),
    LIKE_ANIMAL("동물을 사랑하는 분"),

    ALL("");




    private final String msg;
}
