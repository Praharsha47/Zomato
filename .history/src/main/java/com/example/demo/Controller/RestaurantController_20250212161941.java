package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Restaurant;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Services.RestaurantService;
@CrossOrigin(origins = "*") // Allow Angular frontend

@RestController
@RequestMapping("/restaurants") // Changed to plural for RESTful conventions
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    // // Get Restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant); // Return 200 OK with the restaurant data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found
        }
    }

    @GetMapping
    public ResponseEntity<Page<Restaurant>> getRestaurants(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "9", required = false) int size) {
        Page<Restaurant> restaurants = restaurantService.getRestaurants(page, size);
        return ResponseEntity.ok(restaurants);
    }
    @GetMapping("/nearby")
    public List<Restaurant> getRestaurantsNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "3000") double radius) {
        return restaurantRepository.findRestaurantsByLocation(lat, lon, radius);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
    @GetMapping("/byResId/{resId}")
    public ResponseEntity<Restaurant> getRestaurantByResId(@PathVariable String resId) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantByResId(resId);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
