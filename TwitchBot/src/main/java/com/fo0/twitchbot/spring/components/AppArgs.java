package com.fo0.twitchbot.spring.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @created 10.10.2020 - 22:51:06
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@Component
public class AppArgs {

    @Autowired
    private ApplicationArguments args;

    public String[] getCmdArgs() {
        return new String[] {};
    }
    
    @PostConstruct
    public void init() {
        
    }
}
