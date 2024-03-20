package ro.unibuc.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.controllers.contracts.RoleCreateRequest;
import ro.unibuc.hello.dtos.RoleDTO;
import ro.unibuc.hello.services.RoleService;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/roles")
    @ResponseBody
    public List<RoleDTO> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public RoleDTO getRoleById(@PathVariable String id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/roles")
    @ResponseBody
    public RoleDTO addRole(@RequestBody RoleCreateRequest role) {
        return roleService.addRole(role);
    }

    @PutMapping("/roles/{id}")
    @ResponseBody
    public RoleDTO updateRole(@PathVariable String id, @RequestBody RoleCreateRequest role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/roles/{id}")
    @ResponseBody
    public void deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
    }
}
