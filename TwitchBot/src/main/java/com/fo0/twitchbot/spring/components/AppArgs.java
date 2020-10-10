package com.fo0.twitchbot.spring.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.extern.log4j.Log4j2;

/**
 * @created 10.10.2020 - 22:51:06
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@Component
@Log4j2
public class AppArgs {

    @Autowired
    private ApplicationArguments args;

    public List<String> getCmdArgs() {
        return args.getNonOptionArgs();
    }

    public List<String> getSpringArgs() {
        return Lists.newArrayList(args.getSourceArgs());
    }
}
