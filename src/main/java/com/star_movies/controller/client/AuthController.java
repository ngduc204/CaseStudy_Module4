package com.star_movies.controller.client;

import com.star_movies.model.Role;
import com.star_movies.model.User;
import com.star_movies.service.HandleUploadService;
import com.star_movies.service.RoleService;
import com.star_movies.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AuthController {

    private final HandleUploadService handleUploadService;
    private final UserService userService;
    private final RoleService roleService;

    public AuthController(HandleUploadService handleUploadService,
                          UserService userService,
                          RoleService roleService) {
        this.handleUploadService = handleUploadService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String handleLogin() {
        return "client/login";
    }

    @GetMapping("/register")
    public String handleRegister(Model model) {
        model.addAttribute("user", new User());
        return "client/register";
    }

    @PostMapping("/register")
    public String saveUser(
            @ModelAttribute("user") User user,
            @RequestParam("roleId") Long roleId,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            Model model) {

        if (!isPasswordValid(user.getPassword(), confirmPassword)) {
            return handleRegistrationError(model, user, "Mật khẩu và xác nhận mật khẩu không khớp.");
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String avatarPath = handleUploadService.handleUpload(avatarFile, "users");
                user.setAvatar(avatarPath);
            } catch (IOException e) {
                System.err.println("Lỗi khi upload avatar: " + e.getMessage());
                e.printStackTrace();
                return handleRegistrationError(model, user, "Đăng ký thất bại: Lỗi khi tải ảnh đại diện.");
            }
        }

        try {
            assignUserRole(user, roleId);
        } catch (RuntimeException e) {
            System.err.println("Lỗi cấu hình vai trò: " + e.getMessage());
            e.printStackTrace();
            return handleRegistrationError(model, user, "Đăng ký thất bại: Lỗi cấu hình vai trò.");
        }

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu User: " + e.getMessage());
            e.printStackTrace();
            return handleRegistrationError(model, user, "Đăng ký thất bại: " + e.getMessage());
        }

        return "redirect:/login?registerSuccess";
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    private void assignUserRole(User user, Long roleId) {
        Optional<Role> userRoleOptional = roleService.findById(roleId);
        if (userRoleOptional.isEmpty()) {
            throw new RuntimeException("Vai trò người dùng không hợp lệ hoặc không tồn tại!");
        }
        user.setRole(userRoleOptional.get());
    }

    private String handleRegistrationError(Model model, User user, String errorMessage) {
        model.addAttribute("user", user);
        model.addAttribute("errorMessage", errorMessage);
        return "client/register";
    }
}