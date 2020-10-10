package com.fo0.twitchbot.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fo0.twitchbot.controller.ControllerSystem;

public class SystemInfoTest {

    @Test
    public void test() {
        System.out.println("INFO: " + ControllerSystem.info());
        assertNotNull(ControllerSystem.info());
    }

}
