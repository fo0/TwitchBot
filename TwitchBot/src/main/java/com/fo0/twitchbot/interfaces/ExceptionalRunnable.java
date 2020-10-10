package com.fo0.twitchbot.interfaces;

/**
 * @created 11.10.2020 - 00:08:37
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@FunctionalInterface
public interface ExceptionalRunnable {

    public abstract void run() throws Exception;

}