package com.ada.learning.guava;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CollectionsTest {
    public static void mapAndList(){
        // uniqueIndex
        ImmutableList<String> list = ImmutableList.of("hello", "world6");
        ImmutableMap<Integer, String> integerStringImmutableMap = Maps.uniqueIndex(list, String::length);
        log.warn("integerStringImmutableMap-{}", integerStringImmutableMap);

        // Multimaps.index
        list = ImmutableList.of("hello", "world6", "world", "hello");
        ImmutableListMultimap<Integer, String> index = Multimaps.index(list, String::length);
        log.warn("index-{}", index);

        // page helper
        List<Integer> collect = IntStream.range(0, 1000).boxed().collect(Collectors.toList());
        List<List<Integer>> partition = Lists.partition(collect, 15);
        log.warn("page-size-{}-pages-{}", collect.size(), partition.size());
        log.warn("page-0-{}", partition.get(0));
        log.warn("page-10-{}", partition.get(10));
        log.warn("page-66-{}", partition.get(66));

        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");
        multiset.add("ab");
        multiset.add("ab");
        log.warn("multiset-{}", multiset.toString());
        log.warn("multiset-{}", multiset.count("a"));
        for (Multiset.Entry<String> s : multiset.entrySet()) {
            log.warn("multiset-{}", s.getCount());
        }


        ListMultimap<String, String> multimap = MultimapBuilder.hashKeys().linkedListValues().build();
        multimap.put("a", "b");
        multimap.put("a", "c");
        multimap.put("a", "d");
        multimap.put("e", "f");
        List<String> hello = multimap.get("hello");
        hello.add("world");
        log.warn("multimap-{}", multimap);

        HashBasedTable<String, String, Integer> table = HashBasedTable.create();
        table.put("x1", "y1", 1);
        table.put("x1", "y2", 2);
        table.put("x2", "y1", 3);
        table.put("x2", "y2", 4);
        log.warn("table-{}", table);
        log.warn("table-{}", table.row("x1"));
        log.warn("table-{}", table.column("y1"));
        log.warn("table-cell-set-{}", table.cellSet());

        Range<Integer> closed = Range.closed(1, 10);
        log.warn("range-{}-{}", closed, closed.contains(5));
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(closed);

        Optional<String> first = FluentIterable.from(Arrays.asList("a", "b", "cd"))
                .transform(String::toUpperCase).filter(str -> str.length() == 1).first();
        log.warn("first-{}", first);


    }

    public static void hash(){
        HashCode helloWorld = Hashing.sha256().hashString("hello world", Charset.defaultCharset());
        log.warn("md5--{}", helloWorld.toString());

    }

    public static void graph(){

    }
}
