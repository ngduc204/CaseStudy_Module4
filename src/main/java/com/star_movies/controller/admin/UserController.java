package com.star_movies.controller.admin;

import com.star_movies.model.User;
import com.star_movies.service.HandleUploadService;
import com.star_movies.service.RoleService;
import com.star_movies.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final HandleUploadService handleUploadService;

    public UserController(UserService userService, RoleService roleService, HandleUploadService handleUploadService) {
        this.userService = userService;
        this.roleService = roleService;
        this.handleUploadService = handleUploadService;

    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/user/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/user/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            model.addAttribute("roles", roleService.findAll());
            return "admin/user/form";
        } else {
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role.id") Long roleId,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile) {

        if (!avatarFile.isEmpty()) {
            try {
                String avatarPath = handleUploadService.handleUpload(avatarFile, "users");
                user.setAvatar(avatarPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/detail/{id}")
    public String viewUserDetail(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/user/detail";
        } else {
            return "redirect:/admin/users";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}