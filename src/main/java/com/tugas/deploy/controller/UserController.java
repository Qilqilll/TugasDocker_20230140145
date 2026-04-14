package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // Menyimpan data secara temporary (in-memory), akan hilang jika server direstart
    private List<User> userList = new ArrayList<>();

    // Konfigurasi NIM sesuai dengan instruksi
    private final String TARGET_NIM = "20230140145";

    // 1. Menampilkan halaman Login
    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    // Memproses Login
    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        // Validasi: username = admin, password = nim masing-masing (20230140145)
        if ("admin".equals(username) && TARGET_NIM.equals(password)) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau Password salah!");
            return "login";
        }
    }

    // 2. Menampilkan halaman Home beserta tabel datanya
    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("users", userList);
        // Mengirimkan title khusus sesuai dengan NIM
        model.addAttribute("pageTitle", "MY Website " + TARGET_NIM);
        return "home";
    }

    // 3. Menampilkan halaman Form Input
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    // 4. Memproses hasil input dari form, lalu kembali ke Home
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute User user) {
        userList.add(user); // Data ditambahkan ke list temporary
        return "redirect:/home";
    }

    // Fungsi tambahan untuk Logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}