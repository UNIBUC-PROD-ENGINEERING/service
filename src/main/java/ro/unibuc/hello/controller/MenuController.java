package ro.unibuc.hello.controller;

import java.util.List;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.MenuDTO;
import ro.unibuc.hello.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/get-all")
    @ResponseBody
    public List<MenuDTO> getMenus() {
        return menuService.getMenus();
    }

    @GetMapping("/get")
    @ResponseBody
    public MenuDTO getMenu(@RequestParam(name="id") String id)
    {
        return menuService.getMenu(id);
    }

    @PostMapping("/insert")
    @ResponseBody
    public MenuDTO insertMenu(@RequestParam(name = "restaurantId") String restaurantId,
                                  @RequestParam(name = "dishesIds") List<String> dishesIds) {
        return menuService.insertMenu(restaurantId, dishesIds);
    }

    @PutMapping("/update")
    @ResponseBody
    public MenuDTO updateMenu(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "restaurantId") String restaurantId,
                                  @RequestParam(name = "dishesIds") List<String> dishesIds){
        return menuService.updateMenu(id, restaurantId, dishesIds);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteMenu(@RequestParam(name = "id") String id){
        return menuService.deleteMenu(id);
    }
}
