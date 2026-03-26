package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // If already logged in, redirect to dashboard
        if (session.getAttribute("adminUser") != null) {
            return "redirect:/admin/dashboard";
        }
        return "admin/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        try {
            List<User> users = userRepository.findAll();
            
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    // Check if user is admin
                    if (!user.isAdmin()) {
                        redirectAttributes.addFlashAttribute("error", "Only admin users can access this panel");
                        return "redirect:/admin/login";
                    }
                    // Login successful - store user in session
                    session.setAttribute("adminUser", user);
                    return "redirect:/admin/dashboard";
                }
            }
            
            // Login failed
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/admin/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during login");
            return "redirect:/admin/login";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, @RequestParam(required = false) String action) {
        // Check if user is logged in
        User adminUser = (User) session.getAttribute("adminUser");
        if (adminUser == null) {
            return "redirect:/admin/login";
        }
        
        try {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            model.addAttribute("adminUser", adminUser);
            if ("create".equals(action)) {
                model.addAttribute("showCreateForm", true);
            }
            return "admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading users");
            return "admin/dashboard";
        }
    }
    
    @PostMapping("/create")
    public String createUser(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(required = false) boolean isAdmin,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        // Check if user is logged in
        User adminUser = (User) session.getAttribute("adminUser");
        if (adminUser == null) {
            return "redirect:/admin/login";
        }
        
        try {
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Username and password are required");
                return "redirect:/admin/dashboard?action=create";
            }
            
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setAdmin(isAdmin);
            userRepository.save(newUser);
            
            redirectAttributes.addFlashAttribute("success", "User created successfully");
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating user - Username may already exist");
            return "redirect:/admin/dashboard?action=create";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, HttpSession session, Model model) {
        // Check if user is logged in
        User adminUser = (User) session.getAttribute("adminUser");
        if (adminUser == null) {
            return "redirect:/admin/login";
        }
        
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                model.addAttribute("adminUser", adminUser);
                return "admin/edit";
            } else {
                return "redirect:/admin/dashboard";
            }
        } catch (Exception e) {
            return "redirect:/admin/dashboard";
        }
    }
    
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                            @RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(required = false) boolean isAdmin,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        // Check if user is logged in
        User adminUser = (User) session.getAttribute("adminUser");
        if (adminUser == null) {
            return "redirect:/admin/login";
        }
        
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                User updateUser = user.get();
                
                if (username != null && !username.isEmpty()) {
                    updateUser.setUsername(username);
                }
                if (password != null && !password.isEmpty()) {
                    updateUser.setPassword(password);
                }
                updateUser.setAdmin(isAdmin);
                
                userRepository.save(updateUser);
                redirectAttributes.addFlashAttribute("success", "User updated successfully");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user");
        }
        
        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if user is logged in
        User adminUser = (User) session.getAttribute("adminUser");
        if (adminUser == null) {
            return "redirect:/admin/login";
        }
        
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "User deleted successfully");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user");
        }
        
        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
