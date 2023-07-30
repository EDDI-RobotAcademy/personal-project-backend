package com.example.demo.like.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.like.controller.form.LikeRestaurantCountForm;
import com.example.demo.like.controller.form.LikeRestaurantForm;
import com.example.demo.like.entity.LikeEntity;
import com.example.demo.like.repository.LikeRepository;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private RestaurantRepository restaurantRepository;
    final private LikeRepository likeRepository;

    @Override
    public Boolean likeRestaurant(LikeRestaurantForm likeRestaurantForm) {

        String userToken = likeRestaurantForm.getUserToken();
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();

            Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(likeRestaurantForm.getRestaurantId());

            if (maybeRestaurant.isPresent()) {
                Restaurant restaurant = maybeRestaurant.get();

                if (isAlreadyLiked(account.getId(), restaurant.getId())) {
                    return false;
                }

                LikeEntity like = likeRestaurantForm.toLike(account, restaurant);
                likeRepository.save(like);

                return true;
            }
        }

        return false;
    }

    @Override
    public void delete(Long restaurantId, String userToken) {

        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isPresent()) {
            Optional<LikeEntity> maybeLike = likeRepository.findByAccountIdAndRestaurantId(accountId, restaurantId);

            if (maybeLike.isPresent()) {
                likeRepository.deleteById(maybeLike.get().getId());
            }
        }
    }

    public Integer likesRestaurantCount(LikeRestaurantCountForm countForm) {
        Long restaurantId = countForm.getRestaurantId();
        return likeRepository.countLikesByRestaurantId(restaurantId);
    }

    // 중복 찜 체크하기
    private boolean isAlreadyLiked(Long accountId, Long restaurantId) {
        Optional<LikeEntity> maybeLike = likeRepository.findByAccountIdAndRestaurantId(accountId, restaurantId);
        return maybeLike.isPresent();
    }
}
