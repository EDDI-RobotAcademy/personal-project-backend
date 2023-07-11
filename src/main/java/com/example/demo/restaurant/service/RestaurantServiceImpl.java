package com.example.demo.restaurant.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import com.example.demo.restaurant.repository.RestaurantImagesRepository;
import com.example.demo.restaurant.repository.RestaurantRepository;
import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    final private RestaurantRepository restaurantRepository;
    final private RestaurantImagesRepository restaurantImagesRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;

    @Override
    public List<RestaurantListResponseForm> list() {
        List<RestaurantListResponseForm> tmpList = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();

        for (Restaurant restaurant:restaurants){
            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
            RestaurantListResponseForm responseForm = new RestaurantListResponseForm(restaurant, maybeImages.get(0).getImageResourcePath());
            tmpList.add(responseForm);
        }

        return tmpList;
    }

    @Override
    public Boolean register(RestaurantRegisterRequest request, List<MultipartFile> restaurantImg) {
        final List<RestaurantImages> restaurantImagesList = new ArrayList<>();
        final String fixedDirectoryPath = "../personal-project-frontend/src/assets/uploadImgs/";

        Restaurant restaurant = request.toRestaurant();
        String userToken = request.getUserToken();
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if(maybeAccount.isPresent()) {
            restaurant.setAccount(maybeAccount.get());
        }

        try {
            for (MultipartFile multipartFile: restaurantImg) {
                final String originalFileName = multipartFile.getOriginalFilename();
                final String uniqueRandomFileName = UUID.randomUUID() + originalFileName;
                final String fullPath = fixedDirectoryPath + uniqueRandomFileName;
                final FileOutputStream writer = new FileOutputStream(fullPath);

                log.info("originalFileName: " + originalFileName);

                writer.write(multipartFile.getBytes());
                writer.close();

                RestaurantImages restaurantImages = new RestaurantImages(uniqueRandomFileName);
                restaurantImagesList.add(restaurantImages);

                restaurant.setRestaurantImages(restaurantImages);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        restaurantRepository.save(restaurant);
        restaurantImagesRepository.saveAll(restaurantImagesList);

        return true;
    }

    @Override
    public RestaurantReadResponseForm read(Long id) {
        final Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);

        if (maybeRestaurant.isEmpty()) {
            log.info("존재하지 않는 맛집입니다.");
            return null;
        }
        final Restaurant restaurant = maybeRestaurant.get();
        log.info("restaurant:" + restaurant);

        final List<RestaurantImages> restaurantImagesList = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
        log.info("productImagesList: " + restaurantImagesList);

        return new RestaurantReadResponseForm(restaurant, restaurantImagesList);
    }
}
