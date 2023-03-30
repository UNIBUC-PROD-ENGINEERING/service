
package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.DishesDTO;
import ro.unibuc.hello.dto.MenuDTO;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Tag("IT")
public class MenuServiceTestIT {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DishesRepository dishesRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuService menuService;

    private RestaurantEntity restaurant;

    private List<DishesEntity> dishes;

    @Test
    public void testGetMenus(){

        menuRepository.deleteAll();
        List<MenuDTO> menus   = menuService.getMenus();
        Assertions.assertNotNull(menus);
        Assertions.assertEquals(0, menus.size());
    }
    @Test
    public void testInsertMenu() {
        createObjects();

        List<String> dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        MenuDTO menuDTO = menuService.insertMenu(restaurant.getId().toString(), dishesIds);

        MenuDTO menuInsertedDTO = menuService.getMenu(menuDTO.getId());

        Assertions.assertNotNull(menuDTO);
        Assertions.assertEquals(restaurant.getName(), menuDTO.getRestaurant().getName());

        Assertions.assertEquals(dishes.size(), menuDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), menuDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), menuDTO.getDishes().get(0).getName());

        Assertions.assertEquals(restaurant.getName(), menuInsertedDTO.getRestaurant().getName());

        Assertions.assertEquals(dishes.size(), menuInsertedDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), menuInsertedDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), menuInsertedDTO.getDishes().get(0).getName());



    }

    @Test
    public void testUpdateMenu(){
        createObjects();

        List<String> dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        MenuDTO menuDTO = menuService.insertMenu(restaurant.getId().toString(), dishesIds);
        this.dishes = Arrays.asList(dishesRepository.save(new DishesEntity()),dishesRepository.save(new DishesEntity()));
        dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        MenuDTO menuUpdatedDTO = menuService.updateMenu(menuDTO.getId().toString(), restaurant.getId().toString(), dishesIds);

        MenuDTO menuUpdatedDTODB = menuService.getMenu(menuUpdatedDTO.getId());

        Assertions.assertNotNull(menuUpdatedDTO);
        Assertions.assertEquals(restaurant.getName(), menuUpdatedDTO.getRestaurant().getName());
        Assertions.assertEquals(dishes.size(), menuUpdatedDTO.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), menuUpdatedDTO.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), menuUpdatedDTO.getDishes().get(0).getName());

        Assertions.assertEquals(restaurant.getName(), menuUpdatedDTODB.getRestaurant().getName());
        Assertions.assertEquals(dishes.size(), menuUpdatedDTODB.getDishes().size());
        Assertions.assertEquals(dishes.get(0).getName(), menuUpdatedDTODB.getDishes().get(0).getName());
        Assertions.assertEquals(dishes.get(1).getName(), menuUpdatedDTODB.getDishes().get(0).getName());






    }

    @Test
    public void testDeleteMenu(){
        menuRepository.deleteAll();

        createObjects();

        List<String> dishesIds = Arrays.asList(dishes.get(0).getId().toString(), dishes.get(1).getId().toString());

        MenuDTO menuDTO = menuService.insertMenu(restaurant.getId().toString(), dishesIds);

        String deleteMessage = menuService.deleteMenu(menuDTO.getId().toString());

        Assertions.assertEquals("Order with id:" + menuDTO.getId().toString() + "was deleted", deleteMessage);

        Assertions.assertNull(menuService.getMenu(menuDTO.getId()));

    }

    private void createObjects(){

        RestaurantEntity restaurant = new RestaurantEntity("LaGianone", "gianone.restaurant@gmail.com", "27 Septembrieeee, nr. 34");
        DishesEntity dish1 = new DishesEntity();
        DishesEntity dish2 = new DishesEntity();
        this.restaurant = restaurantRepository.save(restaurant);
        this.dishes = Arrays.asList(dishesRepository.save(dish1), dishesRepository.save(dish2));

    }









}
