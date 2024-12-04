package com.ureca.gate.review.application.outputport;

import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.domain.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
  Optional<Review> findById(Long id);

  List<Review> findAllByPlace(Place place);

  List<Review> findAllByMember(Member member);

  Review save(Review review);

  void delete(Review review);

  boolean existsByMemberAndPlace(Member member, Place place);

  Integer CountByPlace(Place place);

  Double findAverageStarRateByPlaceId(Long placeId);
}
