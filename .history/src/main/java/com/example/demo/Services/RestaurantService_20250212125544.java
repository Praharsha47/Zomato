package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Restaurant;
import com.example.demo.Repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Get Restaurant by ID
    public Restaurant getRestaurantById(long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    // Get List of Restaurants with Pagination
    public Page<Restaurant> getRestaurants(int page, int size) {
        return restaurantRepository.findAll(PageRequest.of(page, size));
    }

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }
    public Optional<Restaurant> getRestaurantByResId(String resId) {
        return restaurantRepository.findByResId(resId);
    }
}
