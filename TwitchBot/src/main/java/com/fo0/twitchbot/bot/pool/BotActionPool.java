package com.fo0.twitchbot.bot.pool;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.fo0.twitchbot.utils.Logger;
import com.fo0.twitchbot.utils.Utils;
import com.google.common.collect.Maps;

public class BotActionPool {

    public static Map<String, String> ACTIONS = Maps.newConcurrentMap();

    public static final String PREFIX = "!";

    public static void addEntry(String key, String value) {
        ACTIONS.put(key, value);
    }

    public static void addEntries(Map<String, String> map) {
        ACTIONS.putAll(map);
    }

    /**
     * action must contain prefix: "!"
     * 
     * @param action
     * @return
     * @Created 10.10.2020 - 20:06:44
     * @author fo0 (GH:fo0)
     */
    public static String execute(String action) {
        // check if valid action and starts with !
        if (!StringUtils.startsWith(action, PREFIX)) {
            Logger.debug("ignores action, because there is no leading \"" + PREFIX + "\": " + action);
            return null;
        }

        // remove leading !
        action = StringUtils.removeStart(action, PREFIX);
        Logger.debug("removed " + PREFIX + "from action: " + action);

        if (!ACTIONS.containsKey(action)) {
            Logger.debug("could not find action: " + action + " in Actions");
            return null;
        }

        String tmpAction = ACTIONS.get(action);
        Logger.debug("found action: " + action + " value: " + tmpAction);

        // parse variables
        List<String> actions = Utils.mapByRegEx(tmpAction, "\\" + ActionVariables.PREFIX + "\\w+");
        Map<String, String> map = Maps.newHashMap();

        actions.forEach(e -> {
            String possibleVar = e.replaceFirst("\\" + ActionVariables.PREFIX, "");
            String replacement = ActionVariables.getValue(possibleVar);

            // skip if no replacement found
            if (StringUtils.isEmpty(replacement))
                return;

            map.put(possibleVar, replacement);
        });

        Logger.debug("evaluting action: " + action + " value: " + tmpAction);
        
        // replace variables with value
        for (Entry<String, String> e : map.entrySet()) {
            tmpAction = tmpAction.replaceAll("\\" + ActionVariables.PREFIX + e.getKey(), e.getValue());
        }

        Logger.debug("evaluted action: " + action + " value: " + tmpAction);
        return tmpAction;
    }
}
