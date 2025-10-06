package com.spring.controller;

import com.spring.entity.TV;
import com.spring.service.TVService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class TVController {

    private final TVService tvService;
    private final JmsTemplate jmsTemplate;


    @Autowired
    public TVController(TVService tvService, JmsTemplate jmsTemplate) {
        this.tvService = tvService;
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/tvs/buy/{id}")
    public String buyTV(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            TV tv = tvService.getTVById(id).orElseThrow(() -> new IllegalArgumentException("Телевизор не найден"));
            jmsTemplate.convertAndSend("adminQueue", "Пользователь хочет купить телевизор ID: " + id + ", модель: " + tv.getModel());
            redirectAttributes.addFlashAttribute("success", "Запрос на покупку отправлен администратору!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка покупки: " + e.getMessage());
        }
        return "redirect:/tvs/list";
    }

    // Главная страница
    @GetMapping("/tvs")
    public String home() {
        return "home"; // home.html
    }

    // Список всех телевизоров
    @GetMapping("/tvs/list")
    public String listTVs(Model model) {
        List<TV> tvs = tvService.getAllTV();
        model.addAttribute("tvs", tvs);
        return "list"; // list.html
    }

    // Форма добавления
    @GetMapping("/tvs/add")
    public String addTVForm(Model model) {
        model.addAttribute("tv", new TV());
        return "add"; // add.html
    }

    @PostMapping("/tvs/add")
    public String addTV(@Valid @ModelAttribute("tv") TV tv, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add"; // Возвращаем форму с ошибками валидации
        }
        try {
            tvService.addTV(tv);
            redirectAttributes.addFlashAttribute("success", "Телевизор добавлен успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка добавления: " + e.getMessage());
            return "redirect:/tvs/add";
        }
        return "redirect:/tvs/list";
    }

    // Форма редактирования
    @GetMapping("/tvs/edit/{id}")
    public String editTVForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            TV tv = tvService.getTVById(id).orElseThrow(() -> new IllegalArgumentException("Телевизор не найден"));
            model.addAttribute("tv", tv);
            return "edit"; // edit.html
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tvs/list";
        }
    }

    @PostMapping("/tvs/edit/{id}")
    public String editTV(@PathVariable Long id, @Valid @ModelAttribute("tv") TV updatedTV, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit"; // Возвращаем форму с ошибками
        }
        try {
            tvService.updateTV(id, updatedTV);
            redirectAttributes.addFlashAttribute("success", "Телевизор обновлён успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка обновления: " + e.getMessage());
            return "redirect:/tvs/edit/" + id;
        }
        return "redirect:/tvs/list";
    }

    // Удаление
    @GetMapping("/tvs/delete/{id}")
    public String deleteTV(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tvService.deleteTV(id);
            redirectAttributes.addFlashAttribute("success", "Телевизор удалён успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка удаления: " + e.getMessage());
        }
        return "redirect:/tvs/list";
    }

    // Форма поиска
    @GetMapping("/tvs/search")
    public String searchForm() {
        return "search"; // search.html
    }

    // Поиск
    @PostMapping("/tvs/search")
    public String searchTVs(@RequestParam Double price, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<TV> tvs = tvService.searchByPriceGreaterThan(price);
            model.addAttribute("tvs", tvs);
            model.addAttribute("price", price);
            return "search-result"; // search-result.html
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка поиска: " + e.getMessage());
            return "redirect:/tvs/search";
        }
    }

    // Глобальная обработка ошибок
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", "Внутренняя ошибка: " + e.getMessage());
        return "error"; // error.html
    }
}