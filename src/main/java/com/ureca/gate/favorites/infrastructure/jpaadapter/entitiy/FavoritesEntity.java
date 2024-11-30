package com.ureca.gate.favorites.infrastructure.jpaadapter.entitiy;

import com.ureca.gate.favorites.domain.Favorites;
import com.ureca.gate.global.entity.BaseTimeEntity;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.member.infrastructure.jpaadapter.entity.MemberEntity;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.place.infrastructure.jpaadapter.entity.PlaceEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name ="favorites")
@Getter
public class FavoritesEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorites_id")
    private Long id;
    @JoinColumn(name  = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity memberEntity;
    @JoinColumn(name  = "place_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceEntity placeEntity;

    public static FavoritesEntity from(Favorites favorites){
        FavoritesEntity favoritesEntity = new FavoritesEntity();
        favoritesEntity.id = favorites.getId();
        favoritesEntity.memberEntity = MemberEntity.from(favorites.getMember());
        favoritesEntity.placeEntity = PlaceEntity.from(favorites.getPlace());
        return favoritesEntity;
    }

    public Favorites toModel(){
        return Favorites.builder()
                .id(id)
                .member(memberEntity.toModel())
                .place(placeEntity.toModel())
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .build();
    }

}
