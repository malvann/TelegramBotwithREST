package com.example.TelegramBot.controller;

import com.example.TelegramBot.model.CityInfo;
import com.example.TelegramBot.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    private final CityService service;

    public AppController(CityService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<CityInfo> infoList = service.getAll();
        model.addAttribute("infoList", infoList);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewCityInfoPage(Model model) {
        CityInfo cityInfo = new CityInfo();
        model.addAttribute("cityInfo", cityInfo);
        return "new_cityInfo";
    }

    @PostMapping(path = "/save")
    public String saveCityInfo(@ModelAttribute("cityInfo") CityInfo cityInfo) {
        service.save(cityInfo);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditCityInfoPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_cityInfo");
        CityInfo cityInfo = service.get(id);
        mav.addObject("cityInfo", cityInfo);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteCityInfo(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";
    }
}
