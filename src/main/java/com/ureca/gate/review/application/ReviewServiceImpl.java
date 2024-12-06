package com.ureca.gate.review.application;

import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.MEMBER_NOT_FOUND;
import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.PLACE_NOT_FOUND;
import static com.ureca.gate.global.exception.errorcode.CommonErrorCode.REVIEW_ALREADY_EXISTS;

import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.global.util.file.FileStorageService;
import com.ureca.gate.global.util.file.UploadFile;
import com.ureca.gate.member.application.outputport.MemberRepository;
import com.ureca.gate.member.domain.Member;
import com.ureca.gate.place.application.outputport.PlaceRepository;
import com.ureca.gate.place.domain.Place;
import com.ureca.gate.review.application.outputport.ReviewFileRepository;
import com.ureca.gate.review.application.outputport.ReviewRepository;
import com.ureca.gate.review.controller.inputport.KeywordService;
import com.ureca.gate.review.controller.inputport.ReviewKeywordService;
import com.ureca.gate.review.controller.inputport.ReviewService;
import com.ureca.gate.review.controller.request.ReviewSaveRequest;
import com.ureca.gate.review.controller.response.PlaceReviewResponse;
import com.ureca.gate.review.controller.response.ReviewKeywordResponse;
import com.ureca.gate.review.controller.response.ReviewResponse;
import com.ureca.gate.review.domain.Keyword;
import com.ureca.gate.review.domain.Review;
import com.ureca.gate.review.domain.ReviewFile;
import com.ureca.gate.review.domain.ReviewKeyword;
import com.ureca.gate.review.infrasturcture.jpaadapter.KeywordJpaRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

  private final KeywordJpaRepository keywordJpaRepository;
  @Value("${file.dir.review-file}")
  private String fileDir;

  private final FileStorageService fileStorageService;
  private final ReviewKeywordService reviewKeywordService;
  private final KeywordService keywordService;
  private final MemberRepository memberRepository;
  private final PlaceRepository placeRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewFileRepository reviewFileRepository;


  @Transactional
  public Review getById(Long reviewId) {
    return reviewRepository.findById(reviewId).orElseThrow(() -> new BusinessException(
        CommonErrorCode.REVIEW_NOT_FOUND));
  }

  @Transactional
  public List<ReviewResponse> getAllByMember(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(()->new BusinessException(MEMBER_NOT_FOUND));
    List<Review> reviewList = reviewRepository.findAllByMember(member);
    List<ReviewResponse> reviewResponseList = new ArrayList<>();
    for (Review review : reviewList) {
      List<String> fileUrlList = getFileUrlList(member.getId(), review.getReviewFiles());
      List<String> keywordList = reviewKeywordService.getReviewKeywordContents(review);
      ReviewResponse reviewResponse = ReviewResponse.from(review, fileUrlList, keywordList);
      reviewResponseList.add(reviewResponse);
    }
    return reviewResponseList;
  }

  @Transactional
  public PlaceReviewResponse getAllByPlace(Long placeId) {
    Place place = placeRepository.findById(placeId).orElseThrow(()->new BusinessException(PLACE_NOT_FOUND));
    List<Review> reviewList = reviewRepository.findAllByPlace(place);
    List<ReviewResponse> reviewResponseList = new ArrayList<>();
    for (Review review : reviewList) {
      List<String> fileUrlList = getFileUrlList(review.getMember().getId(), review.getReviewFiles());
      List<String> keywordContentList = reviewKeywordService.getReviewKeywordContents(review);
      ReviewResponse reviewResponse = ReviewResponse.from(review, fileUrlList, keywordContentList);
      reviewResponseList.add(reviewResponse);
    }
    Integer reviewCount = reviewRepository.CountByPlace(place);
    Double BeforeStarRateAvg = reviewRepository.findAverageStarRateByPlaceId(placeId);
    Double AfterStarRateAvg = Math.round(BeforeStarRateAvg * 10) / 10.0;
    String starRateAvg = String.format("%.1f", AfterStarRateAvg);
    List<ReviewKeywordResponse> reviewKeywordResponseList = keywordJpaRepository.findKeywordStatsByPlaceId(placeId);
    return PlaceReviewResponse.from(starRateAvg, reviewCount, reviewResponseList, reviewKeywordResponseList);
  }

  @Transactional
  public ReviewResponse create(Long memberId, ReviewSaveRequest request, List<MultipartFile> files)
      throws IOException {
    Member member = memberRepository.findById(memberId).orElseThrow(()->new BusinessException(MEMBER_NOT_FOUND));
    Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(()->new BusinessException(PLACE_NOT_FOUND));
    Review savedReview = reviewRepository.save(Review.from(member, place, request));
    if(files != null) {
      for(MultipartFile file : files){
        UploadFile uploadFile = fileStorageService.storeFile(memberId, file, fileDir);
        ReviewFile reviewFile = ReviewFile.from(savedReview.getId(), uploadFile);
        reviewFileRepository.save(reviewFile);
      }
    }
    for (Long keywordId : request.getKeywords()){
      Keyword keyword = keywordService.getById(keywordId);
      ReviewKeyword reviewKeyword = reviewKeywordService.create(savedReview, keyword);
    }
    List<String> fileUrlList = getFileUrlList(memberId, savedReview.getReviewFiles());
    List<String> keywordList = reviewKeywordService.getReviewKeywordContents(savedReview);
    return ReviewResponse.from(savedReview, fileUrlList, keywordList);
  }

  @Transactional
  public Review update(Long memberId, Long reviewId, ReviewSaveRequest request,List<MultipartFile> files) throws IOException {
    Review review = getById(reviewId);
    for (ReviewFile reviewFile : review.getReviewFiles()){
      fileStorageService.deleteFile(memberId, reviewFile.getUploadFile(), fileDir);
      reviewFileRepository.delete(reviewFile);
    }
    if (files != null) {
      for(MultipartFile file : files){
        UploadFile uploadFile = fileStorageService.storeFile(memberId, file, fileDir);
        ReviewFile reviewFile = ReviewFile.from(review.getId(), uploadFile);
        reviewFileRepository.save(reviewFile);
      }
    }
    reviewKeywordService.deleteAll(review);
    for (Long keywordId : request.getKeywords()){
      Keyword keyword = keywordService.getById(keywordId);
      ReviewKeyword reviewKeyword = reviewKeywordService.create(review, keyword);
    }
    return reviewRepository.save(review.update(request));
  }

  @Transactional
  public void delete(Long reviewId) {
    Review review = getById(reviewId);
    for (ReviewFile reviewFile : review.getReviewFiles()) {
      reviewFileRepository.delete(reviewFile);
    }
    reviewKeywordService.deleteAll(review);
    reviewRepository.delete(review);
  }

  @Override
  public List<String> getFileUrlList(Long memberId, List<ReviewFile> files) {
    List<String> fileUrlList = new ArrayList<>();
    for (ReviewFile reviewFile : files) {
      UploadFile uploadFile = reviewFile.getUploadFile();
      String fileUrl = fileStorageService.generateFileUrl(memberId, uploadFile, fileDir);
      fileUrlList.add(fileUrl);
    }
    return fileUrlList;
  }
}
