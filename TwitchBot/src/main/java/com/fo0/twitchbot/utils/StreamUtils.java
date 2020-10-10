package com.fo0.twitchbot.utils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

/**
 * @created 11.10.2020 - 00:13:10
 * @author fo0 (GH:fo0)
 * @version 0.1
 */
public class StreamUtils {

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static <T> Stream<? extends T> orderedParallelStream(Collection<T> c) {
        Collection<T> col = c;
        if (col == null) {
            c = Lists.newArrayList();
        }
        return col.stream().unordered().parallel().distinct();
    }

    
    @SuppressWarnings("unchecked")
    public static <T> Stream<T> stream(Iterable<T> values) {
        if (IterableUtils.isEmpty(values)) {
            return (Stream<T>) Lists.newArrayList().stream();
        }
        return StreamSupport.stream(values.spliterator(), false);
    }
    
    @SafeVarargs
    public static <T> Stream<T> orderedParallelStream(T... values) {
        return Stream.of(values).unordered().parallel().distinct();
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> stream(T... values) {
        if (ArrayUtils.isEmpty(values)) {
            return (Stream<T>) Lists.newArrayList().stream();
        }

        return Stream.of(values);
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> stream(Collection<T> values) {
        if (CollectionUtils.isEmpty(values)) {
            return (Stream<T>) Lists.newArrayList().stream();
        }

        return values.stream();
    }

}