package com.ureca.gate.place.infrastructure.jpaadapter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name ="place_vector")
@Getter
public class PlaceVectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_vector_id")
    private Long id;

    @JoinColumn(name = "place_id")
    @OneToOne
    private PlaceEntity placeEntity;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    private float[] embedding;

}
