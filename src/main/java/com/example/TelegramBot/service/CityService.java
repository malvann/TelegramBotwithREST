package com.example.TelegramBot.service;

import com.example.TelegramBot.model.CityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CityService {
    @Autowired
    private CityRepository repository;

    public List<CityInfo> getAll(){
        return repository.findAll();
    }

    public void save(CityInfo cityInfo){
        repository.save(cityInfo);
    }

    public CityInfo get(long id){
        return repository.getOne(id);
    }

    public void delete(long id){
        repository.deleteById(id);
    }

    public List<String> getCityInfoByCity(String city){
        return repository.getCityInfoByCity(city);
    }
}
