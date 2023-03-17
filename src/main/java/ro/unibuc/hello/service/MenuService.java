package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.MenuEntity;
import ro.unibuc.hello.data.RestaurantEntity;
import ro.unibuc.hello.data.DishesEntity;


import ro.unibuc.hello.data.MenuRepository;
import ro.unibuc.hello.data.RestaurantRepository;
import ro.unibuc.hello.data.DishesRepository;

import ro.unibuc.hello.dto.MenuDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishesRepository dishesRepository;

    public List<MenuDTO> getMenus() {
        return menuRepository
                .findAll()
                .stream()
                .map(menu -> new MenuDTO(menu))
                .collect(Collectors.toList());
    }

    public MenuDTO getMenu(String id) {
        MenuEntity menu = menuRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if (menu != null)
            return new MenuDTO(menu);
        else
            return null;
    }

    public MenuDTO insertMenu(String restaurantId, List<String> dishesIds) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(String.valueOf(new ObjectId(restaurantId))).orElse(null);

        ArrayList<DishesEntity> dishesEntities = new ArrayList<>();
        if(dishesIds != null && !dishesIds.isEmpty())
            dishesIds.forEach(id -> dishesEntities.add(dishesRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));

        MenuEntity menu = new MenuEntity(restaurantEntity, dishesEntities);

        return new MenuDTO(menuRepository.save(menu));
    }

    public MenuDTO updateMenu(String id, String restaurantId, List<String> dishesIds){
        MenuEntity menu = menuRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if(menu != null)
        {
            if(restaurantId != null) {
                RestaurantEntity restaurantEntity = restaurantRepository.findById(String.valueOf(new ObjectId(restaurantId))).orElse(null);
                menu.setRestaurant(restaurantEntity);
            }

            if(dishesIds != null && !dishesIds.isEmpty()) {
                ArrayList<DishesEntity> dishesEntities = new ArrayList<>();

                dishesIds.forEach(dishesId -> dishesEntities.add(dishesRepository.findById(String.valueOf(new ObjectId(dishesId))).orElse(null)));
                if(!dishesEntities.isEmpty())
                    menu.setDishes(dishesEntities);
            }
            else
                menu.setDishes(null);

            return new MenuDTO(menuRepository.save(menu));
        }
        else {
            return null;
        }
    }

    public String deleteMenu(String id) {
        menuRepository.deleteById(String.valueOf(new ObjectId(id)));

        return "Menu with id " + id + " was deleted successfully!";
    }
}
