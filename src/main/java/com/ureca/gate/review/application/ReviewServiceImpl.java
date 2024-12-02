package com.ureca.gate.review.application;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.MEMBER_NOT_FOUND;
import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.util.file.FileStorageService;
import com.ureca.gate.global.util.file.UploadFile;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.application.outputport.ReviewFileRepository;
import com.ureca.gate.review.application.outputport.ReviewRepository;
import com.ureca.gate.review.controller.inputport.ReviewService;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewFile;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

  @Value("${file.dir.review-file}")
  private String fileDir;

  private final FileStorageService fileStorageService;
  private final MemberRepository memberRepository;
  private final PlaceRepository placeRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewFileRepository reviewFileRepository;


  @Override
  public Review create(Long memberId, ReviewSaveRequest request, List<MultipartFile> files)
      throws IOException {

    Member member = memberRepository.findById(memberId).orElseThrow(()->new BusinessException(MEMBER_NOT_FOUND));
    Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(()->new BusinessException(PLACE_NOT_FOUND));

    Review review = Review.from(member, place, request);

    for(MultipartFile file : files){
      UploadFile uploadFile = fileStorageService.storeFile(memberId, file, fileDir);
      ReviewFile reviewFile = ReviewFile.from(review, uploadFile);
      reviewFileRepository.save(reviewFile);
    }

    return reviewRepository.save(review);
  }
}
