package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import ro.unibuc.hello.auth.AuthUtil;
import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.ItemPostRequest;
import ro.unibuc.hello.dto.ItemWithOwner;
import ro.unibuc.hello.permissions.ItemPermissionChecker;
import ro.unibuc.hello.service.ItemsService;

@Controller
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private ItemPermissionChecker permissionChecker;

    @PublicEndpoint
    @GetMapping("/items")
    @ResponseBody
    public List<ItemWithOwner> getAllItems() {
        return itemsService.getAllItems();
    }

    @PublicEndpoint
    @GetMapping("/items/{id}")
    @ResponseBody
    public ItemWithOwner getItemById(@PathVariable String id) {
        return itemsService.getItemById(id);
    }

    @PostMapping("/items")
    @ResponseBody
    public ItemWithOwner createItem(HttpServletRequest request, @RequestBody ItemPostRequest item) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        return itemsService.saveItem(userId, item);
    }

    @PutMapping("/items/{id}")
    @ResponseBody
    public ItemWithOwner updateItem(HttpServletRequest request, @PathVariable String id, @RequestBody ItemPostRequest item) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        return itemsService.updateItem(id, item);
    }

    @DeleteMapping("/items/{id}")
    @ResponseBody
    public void deleteItem(HttpServletRequest request, @PathVariable String id) {
        String userId = AuthUtil.getAuthenticatedUserId(request);
        permissionChecker.checkOwnership(userId, id);
        itemsService.deleteItem(id);
    }
}
