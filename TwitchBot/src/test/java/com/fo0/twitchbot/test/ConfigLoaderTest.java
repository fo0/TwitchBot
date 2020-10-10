package com.fo0.twitchbot.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.Test;

import com.fo0.twitchbot.model.TwitchBotConfig;
import com.fo0.twitchbot.model.TwitchBotConfigs;
import com.fo0.twitchbot.utils.ConfigLoader;
import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;

public class ConfigLoaderTest {

    private TwitchBotConfigs kv = null;

    private static final String PATH = "bots/abc.tmp";

    @AfterClass
    public static void cleanup() {
        if (Paths.get(PATH).toFile().exists()) {
            Paths.get(PATH).toFile().delete();
        }
    }

    @Test
    public void testCondition() {
        TwitchBotConfigs bots = TwitchBotConfigs.builder()
                                                .build();
        bots.getList()
            .add(TwitchBotConfig.builder()
                                .id("a")
                                .name("b")
                                .build());

        bots.getList()
            .add(TwitchBotConfig.builder()
                                .build());

        TwitchBotConfig config = TwitchBotConfig.builder()
                                                .id("x")
                                                .name("x")
                                                .build();

        if (bots.getList()
                .stream()
                .anyMatch(e -> {
                    if (StringUtils.equals(e.getId(), config.getId()))
                        return true;

                    if (StringUtils.equals(e.getName(), config.getName()))
                        return true;

                    return false;
                })) {
            Logger.debug("bot exists");
        } else {
            Logger.debug("bot not exists");
        }

    }

    @Test
    public void simpleTest() {
        kv = TwitchBotConfigs.builder()
                             .list(Arrays.asList(TwitchBotConfig.builder()
                                                                .build()))
                             .build();

        ConfigLoader<TwitchBotConfigs> config = new ConfigLoader<TwitchBotConfigs>("test-config", Paths.get(PATH),
                TwitchBotConfigs.class, () -> kv, e -> {
                    kv = e;
                }, null);

        config.save();
        Utils.sleep(TimeUnit.SECONDS, 1);
        kv = null;

        config.load();
        assertNotNull(kv);
    }

    @Test
    public void simpleTest2() {
        kv = TwitchBotConfigs.builder()
                             .list(Arrays.asList(TwitchBotConfig.builder()
                                                                .build()))
                             .build();

        ConfigLoader<TwitchBotConfigs> config = new ConfigLoader<TwitchBotConfigs>("test-config", Paths.get(PATH),
                TwitchBotConfigs.class, () -> kv, e -> {
                    kv = e;
                }, null);

        assertTrue(Files.exists(Paths.get(PATH)));

        assertEquals(kv, config.getInput()
                               .get());
    }

}
