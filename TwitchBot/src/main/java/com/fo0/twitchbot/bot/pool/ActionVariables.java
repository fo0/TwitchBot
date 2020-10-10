package com.fo0.twitchbot.bot.pool;

import java.util.Map;
import java.util.function.Supplier;

import com.fo0.twitchbot.bot.template.VariableDefaultTemplate;
import com.google.common.collect.Maps;

public class ActionVariables {

    public static Map<String, Supplier<String>> VARIABLES = Maps.newHashMap();

    static {
        VARIABLES.putAll(VariableDefaultTemplate.TEMPLATE);
    }

    public static final String PREFIX = "$";

    public static String getValue(String value) {
        if (!VARIABLES.containsKey(value))
            return null;

        return VARIABLES.get(value).get();
    }

}
