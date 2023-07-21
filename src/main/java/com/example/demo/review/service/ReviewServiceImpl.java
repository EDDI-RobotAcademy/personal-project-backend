package com.example.demo.review.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import com.example.demo.restaurant.repository.RestaurantRepository;
import com.example.demo.review.controller.form.ReviewModifyForm;
import com.example.demo.review.controller.form.response.ReviewListResponseForm;
import com.example.demo.review.controller.form.response.ReviewReadResponseForm;
import com.example.demo.review.entity.Review;
import com.example.demo.review.repository.ReviewRepository;
import com.example.demo.review.service.request.ReviewRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private ReviewRepository reviewRepository;
    final private RestaurantRepository restaurantRepository;

    @Override
    public Boolean register(ReviewRegisterRequest request) {

        String userToken = request.getUserToken();
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);


        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();

            Optional<Restaurant> maybeRestaurant = restaurantRepository.findByRestaurantName(request.getRestaurantName());
            if (maybeRestaurant.isPresent()) {

                Restaurant restaurant = maybeRestaurant.get();

                Float ratings = request.getRatings();
                String comment = request.getComment();

                Review review = new Review(ratings, comment);
                review.setAccount(account);
                review.setRestaurant(restaurant);
                reviewRepository.save(review);

                return true;
            }
        }

        return false;
    }

    @Override
    public List<ReviewListResponseForm> list(Long restaurantId) {
        List<ReviewListResponseForm> tmpList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);

        for (Review review : reviews) {
//            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
//            if (!maybeImages.isEmpty()) {
            ReviewListResponseForm responseForm = new ReviewListResponseForm(review);
            tmpList.add(responseForm);
//            }
        }

        return tmpList;
    }

    @Override
    public Review modify(Long id, ReviewModifyForm modifyForm) {

        final String userToken = modifyForm.getUserToken();
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isPresent()) {
            Optional<Review> maybeReview = reviewRepository.findById(id);

            if (maybeReview.isEmpty()) {
                log.info("존재하지 않는 리뷰입니다.");
                return null;
            }

            Review review = maybeReview.get();
            review.setRatings(modifyForm.getRatings());
            review.setComment(modifyForm.getComment());

            return reviewRepository.save(review);
        }

        return null;
    }

    @Transactional
    @Override
    public ReviewReadResponseForm read(Long id) {
        final Optional<Review> maybeReview = reviewRepository.findById(id);

        if (maybeReview.isEmpty()) {
            log.info("후기가 존재하지 않습니다.");
            return null;
        }
        final Review review = maybeReview.get();
        log.info("review:" + review);

//        final List<RestaurantImages> restaurantImagesList = restaurantImagesRepository.findByRestaurantId(review.getId());
//        log.info("restaurantImagesList: " + restaurantImagesList);

        return new ReviewReadResponseForm(review);
    }
}