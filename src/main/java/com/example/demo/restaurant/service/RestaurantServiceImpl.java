package com.example.demo.restaurant.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.AccountRoleRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.restaurant.controller.form.*;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantListResponseForm;
import com.example.demo.restaurant.entity.*;
import com.example.demo.restaurant.repository.*;
import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.account.entity.RoleType.BUSINESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    final private RestaurantRepository restaurantRepository;
    final private RestaurantImagesRepository restaurantImagesRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private MenuRepository menuRepository;
    final private FoodRepository foodRepository;
    final private RestaurantFoodRepository restaurantFoodRepository;

    @Override
    public List<RestaurantListResponseForm> list() {
        List<RestaurantListResponseForm> tmpList = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
            if (!maybeImages.isEmpty()) {
                RestaurantListResponseForm responseForm = new RestaurantListResponseForm(restaurant, maybeImages.get(0).getImageResourcePath());
                tmpList.add(responseForm);
            } else {
                tmpList.add(new  RestaurantListResponseForm(restaurant, ""));
            }
        }
        return tmpList;
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
        log.info("restaurantImagesList: " + restaurantImagesList);

        final Optional<Menu> maybeMenu = menuRepository.findByRestaurantId(restaurant.getId());
        if (maybeMenu.isEmpty()) {
            return null;
        }

        final Menu menu = maybeMenu.get();
        log.info("menu: " + menu);

        final Optional<RestaurantFood> maybeRestaurantFood = restaurantFoodRepository.findByRestaurantId(restaurant.getId());
        if (maybeRestaurantFood.isEmpty()) {
            return null;
        }

        final RestaurantFood restaurantFood = maybeRestaurantFood.get();
        log.info("restaurantFood: " + restaurantFood);

        return new RestaurantReadResponseForm(restaurant, restaurantImagesList, menu , restaurantFood);
    }

    @Override
    public List<BusinessRestaurantListResponseForm> businessRegisterRestaurantList(Long accountId) {
        List<BusinessRestaurantListResponseForm> businessRegisterRestaurantList = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantRepository.findAllByAccountId(accountId) ;

        for (Restaurant restaurant: restaurantList ){
            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());

            if (!maybeImages.isEmpty()) {
                BusinessRestaurantListResponseForm responseForm = new BusinessRestaurantListResponseForm(
                        restaurant, maybeImages.get(0).getImageResourcePath());
                businessRegisterRestaurantList.add(responseForm);
            } else {
                businessRegisterRestaurantList.add(new BusinessRestaurantListResponseForm(
                        restaurant, ""
                        ));
            }
        }
        return businessRegisterRestaurantList;
    }

    @Override
    public RestaurantModifyResponseForm modify(Long id, RestaurantModifyForm modifyForm) {
        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);

        if (maybeRestaurant.isEmpty()) {
            log.info("존재하지 않는 맛집입니다.");
            return null;
        }

        Restaurant restaurant = maybeRestaurant.get();
        restaurant.setRestaurantName(modifyForm.getRestaurantName());
        restaurant.setRestaurantInfo(modifyForm.getRestaurantInfo());
        restaurant.setRestaurantAddress(modifyForm.getRestaurantAddress());
        restaurant.setRestaurantNumber(modifyForm.getRestaurantNumber());
        restaurant.setRestaurantTime(modifyForm.getRestaurantTime());

        restaurantRepository.save(restaurant);

        final List<RestaurantImages> restaurantImagesList = new ArrayList<>();

        for (String imageUrl : modifyForm.getImageUrls()) {
            RestaurantImages restaurantImage = new RestaurantImages(imageUrl);
            restaurantImage.setRestaurant(restaurant); // 레스토랑 엔티티와의 관계 설정
            restaurantImagesList.add(restaurantImage);
        }

        restaurantImagesRepository.saveAll(restaurantImagesList);

        final Optional<Menu> maybeMenu = menuRepository.findByRestaurantId(restaurant.getId());
        if (maybeMenu.isEmpty()) {
            return null;
        }

        Menu menu = maybeMenu.get();
        menu.setMenuItem(modifyForm.getMenuItem());
        menu.setMenuPrice(modifyForm.getMenuPrice());

        menuRepository.save(menu);

        final Optional<Food> maybeFood = foodRepository.findByFoodType(modifyForm.getFoodType());
        if (maybeFood.isEmpty()) {
            return null;
        }

        Food food = maybeFood.get();
        food.setFoodType(modifyForm.getFoodType());
        final RestaurantFood restaurantFood = new RestaurantFood(food, restaurant);
        restaurantFoodRepository.save(restaurantFood);

        return new RestaurantModifyResponseForm(restaurant, restaurantImagesList, menu, restaurantFood);
    }

    @Override
    public String returnName(Long id) {

        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);
        if (maybeRestaurant.isEmpty()) {
            return null;
        }

        String restaurantName = maybeRestaurant.get().getRestaurantName();

        return restaurantName;
    }

    @Override
    public Integer returnVisitor(Long id) {



        return null;
    }

    @Override
    public void delete(Long id) {
        menuRepository.deleteByRestaurantId(id);
        restaurantFoodRepository.deleteByRestaurantId(id);

        restaurantImagesRepository.deleteAllByRestaurantId(id);
        restaurantRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Boolean register(RestaurantRegisterRequest request, List<String> restaurantImageUrls) {

        String userToken = request.getUserToken();
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        log.info("accountId: " + accountId);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isEmpty()) {
            return false;
        }

        Account account = maybeAccount.get();
        boolean isBusinessAccount = accountRoleRepository.findRoleByAccount(account).getRoleType() == BUSINESS;
        if (!isBusinessAccount) {
            return false;
        }

        Restaurant restaurant = request.toRestaurant();
        restaurant.setAccount(account);
        restaurantRepository.save(restaurant); // 레스토랑 엔티티 저장

        // 메뉴 담아주기
        List<MenuData> menus = request.getMenus();
        for (MenuData menuData : menus) {
            Menu menu = new Menu(menuData.getMenuItem(), menuData.getMenuPrice());
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);
        }

        final List<RestaurantImages> restaurantImagesList = new ArrayList<>();

        for (String imageUrl : restaurantImageUrls) {
            RestaurantImages restaurantImage = new RestaurantImages(imageUrl);
            restaurantImage.setRestaurant(restaurant); // 레스토랑 엔티티와의 관계 설정
            restaurantImagesList.add(restaurantImage);
        }

        restaurantImagesRepository.saveAll(restaurantImagesList); // 레스토랑 이미지 엔티티들 저장

        Optional<Food> maybeFood = foodRepository.findByFoodType(request.getFoodType());
        if (maybeFood.isPresent()) {
            final Food food = maybeFood.get();
            final RestaurantFood restaurantFood = new RestaurantFood(food, restaurant);
            restaurantFoodRepository.save(restaurantFood);
        }

        return true;
    }
}
