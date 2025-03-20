package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(ModelMap model,
                               @RequestParam(value = "count",
                                       required = false,
                                       defaultValue = "100") Integer count,
                               Principal principal) {
        model.addAttribute("users", userService.getUsers(count));
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("roles", userService.getAllRolesNames());

        return "/admin";
    }

    @PostMapping("/admin/newUser")
    public String createNewUser(@ModelAttribute("user") User user,
                                @RequestParam(name = "selectedRoles",
                                        required = false) Set<String> selectedRoles) {
        userService.saveUser(user, selectedRoles);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/users")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(name = "selectedRoles",
                                     required = false) Set<String> selectedRoles) {
        userService.saveUser(user, selectedRoles);
        return "redirect:/admin";
    }

    @DeleteMapping("/users")
    public String deleteUser(@RequestParam(value = "id",
            required = false) Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
