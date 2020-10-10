package com.fo0.twitchbot.interfaces;

/**
 * @created 11.10.2020 - 00:08:57
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
@FunctionalInterface
public interface ExceptionalSupplier<T> {

    T get() throws Exception;

}