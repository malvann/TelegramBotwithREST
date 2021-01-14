package com.example.TelegramBot.service;

import com.example.TelegramBot.model.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<CityInfo, Long> {
    List<String> getCityInfoByCity(@Param("city") String city);
}
