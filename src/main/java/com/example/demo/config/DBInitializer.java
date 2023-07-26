package com.example.demo.config;

import com.example.demo.account.entity.Role;
import com.example.demo.account.entity.RoleType;
import com.example.demo.account.repository.RoleRepository;
import com.example.demo.restaurant.entity.Food;
import com.example.demo.restaurant.entity.FoodType;
import com.example.demo.restaurant.repository.FoodRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInitializer {

    private final RoleRepository roleRepository;
    private final FoodRepository foodRepository;

    @PostConstruct
    private void init () {
        log.debug("initializer 시작!");

        initAccountRoleTypes();
        initRestaurantFoodTypes();

        log.debug("initializer 종료!");
    }

    private void initAccountRoleTypes() {
        try {
            final Set<RoleType> roles =
                    roleRepository.findAll().stream()
                            .map(Role::getRoleType)
                            .collect(Collectors.toSet());

            for (RoleType type: RoleType.values()) {
                if (!roles.contains(type)) {
                    final Role role = new Role(type);
                    roleRepository.save(role);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void initRestaurantFoodTypes() {
        try {
            final Set<FoodType> foods =
                    foodRepository.findAll().stream()
                            .map(Food::getFoodType)
                            .collect(Collectors.toSet());

            for (FoodType type: FoodType.values()) {
                if (!foods.contains(type)) {
                    final Food food = new Food(type);
                    foodRepository.save(food);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
