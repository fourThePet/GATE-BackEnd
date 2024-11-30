package com.ureca.gate.favorites.domain;


import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.domain.Place;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Favorites {
    private Long id;
    private Member member;
    private Place place;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public Favorites(Long id, Member member, Place place, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.member = member;
        this.place = place;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Favorites from(Member member, Place place) {
        return Favorites.builder()
                .member(member)
                .place(place)
                .build();
    }

    public void delete(){

    }

}
