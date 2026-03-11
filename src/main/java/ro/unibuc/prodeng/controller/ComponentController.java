package ro.unibuc.prodeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.prodeng.response.ComponentResponse; 
import ro.unibuc.prodeng.service.ComponentService;

@RestController
@RequestMapping("/api/components") 
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @GetMapping
    public ResponseEntity<Page<ComponentResponse>> getComponents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean isConsumable,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ComponentResponse> components = componentService.getComponents(
                category, isConsumable, available, search, page, size
        );
        return ResponseEntity.ok(components);
    }
}