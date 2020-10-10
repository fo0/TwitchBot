package com.fo0.twitchbot.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fo0.twitchbot.bot.pool.BotActionPool;

public class VariableFindTest {

    @Test
    public void singleVariableTest() {
        String text = "!time";

        BotActionPool.addEntry("time", "test");
        String result = BotActionPool.execute(text);
        
        assertEquals("test", result);
    }

}
