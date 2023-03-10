package ro.unibuc.hello.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.DishesEntity;

import ro.unibuc.hello.data.DishesRepository;

import ro.unibuc.hello.dto.DishesDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.ArrayList;

@Component
public class DishesService {

    @Autowired
    private DishesRepository dishesRepository;

    public List<DishesDTO> getDishes() {
        ArrayList<DishesDTO> dishes = new ArrayList<>();

        dishesRepository.findAll().forEach(dishesEntity -> dishes.add(new DishesDTO(dishesEntity)));
        return dishes;
    }

    public DishesDTO getDish(String id) {
        DishesEntity dish = dishesRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if (dish != null)
            return new DishesDTO(dish);
        else
            return null;
    }

    public DishesDTO insertDish(String name, int quantity, float price) {
        DishesEntity dish = new DishesEntity(name, quantity, price);

        return new DishesDTO(dishesRepository.save(dish));
    }

    public DishesDTO updateDish(String id, String name, int quantity, float price) {
        DishesEntity dish = dishesRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);

        if(dish != null)
        {
            dish.setName(name);
            dish.setQuantity(quantity);
            dish.setPrice(price);

            return new DishesDTO(dishesRepository.save(dish));
        }
        else {
            return null;
        }
    }

    public String deleteDish(String id) {
        dishesRepository.deleteById(String.valueOf(new ObjectId(id)));

        return "Dish with id " + id + " was deleted successfully!";
    }
}
