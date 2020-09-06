package com.calling.localhost;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SayHello {

    @Autowired
    private CatFactApi catFactApi;


    @PostConstruct
    public void init() {
        JsonNode facts = catFactApi.getCatFacts();
        System.out.println(facts);
    }
}
