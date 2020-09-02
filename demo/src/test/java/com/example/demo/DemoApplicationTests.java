package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Artist;
import com.example.demo.entity.Track;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.primitives.Ints.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void test(){

        String str="eyJ2IjoiMy4wLjAiLCJvcyI6IndlYiIsIml0IjoxNTk3Mzk3MzM2ODIxLCJpZGYiOiIxNTk3Mzk3MzM0NzYxNTA0OSIsInQiOiJQVy9mYVFENjdEaG10OVZyUkdsZ0JIeGlUbkl2WlJOQkJBcXJwMnh1SDhyZm0wblJqZU5QVU90VzhnVGplYmpCIn0=";
       byte[] decooder = Base64.getDecoder().decode(str.getBytes());
        System.out.println(new String(decooder));

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(str);
        System.out.println("BASE64解密：" + new String(bytes));
    }
    @Test
    void contextLoads() {
        //源码、反码、补码
        //位运算
        //与 101 & 011 = 001
        System.out.println("&: "+(5 & 3));
        System.out.println(-5 & 3);
        System.out.println(5 & -3);
        System.out.println(-5 & -3);
        //或 101 | 011 = 111
        System.out.println("|: "+(5 | 3));
        //异或 101 ^ 011 = 110
        System.out.println("^:"+(5 ^ 3));



        System.out.println("1 ~"+(~ 1));
        System.out.println("-5 ~"+(~(-5)));

        System.out.println("5 <<"+(5 << 2));
        System.out.println("-5 <<"+((-5) << 2));
        System.out.println("5 >>"+(5 >> 2));
        System.out.println("-5 >>"+(-5 >> 2));
        System.out.println(((-5) >> 2));
        System.out.println("5 >>>"+(5 >>> 2));
        System.out.println("-5 >>>"+(-5 >>> 2));


    }

    @Test
    public void DateTest(){
        Instant instant = Instant.now();
        System.out.println(Instant.EPOCH);
        System.out.println(instant);
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(LocalDate.ofYearDay(2020,180));
        System.out.println(localDate);
        String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(localTime);
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(localDateTime);
    }
    @Test
    public void FunctionProgramTest(){
        List<Artist> allArtists = Lists.newArrayList();
        Artist artist1 = new Artist();
        artist1.setName("张三");
        artist1.setOrigin("周口");
        artist1.setMembers("河南");
        allArtists.add(artist1);

        Artist artist2 = new Artist();
        artist2.setName("李四");
        artist2.setOrigin("漯河");
        artist2.setMembers("河南");
        allArtists.add(artist2);

        Artist artist3 = new Artist();
        artist3.setName("王五");
        artist3.setOrigin("周口");
        artist3.setMembers("河南");
        allArtists.add(artist3);

        String name = allArtists.stream().map(Artist::getName).findAny().get();
        System.out.println(name);

        long count = allArtists.stream() .filter(artist -> artist.isFrom("周口")) .count();
        System.out.println(count);
        List list = allArtists.stream() .filter(artist -> artist.isFrom("周口")).map(Artist::getName).collect(Collectors.toList());
        System.out.println(list);
        List<String> collected = Stream.of("a", "b", "c").collect(toList());
        assertEquals(Arrays.asList("a", "b", "c"), collected);

        List<String> collected1 = Stream.of("a", "b", "hello") .map(string -> string.toUpperCase()) .collect(toList());
        assertEquals(Arrays.asList("A", "B", "HELLO"), collected1);

        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4)) .flatMap(numbers -> numbers.stream()) .collect(toList());
        assertEquals(asList(1, 2, 3, 4), together);
        List<Track> tracks = Arrays.asList(new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.stream() .min(Comparator.comparing(track -> track.getLength())) .get();
                assertEquals(tracks.get(1), shortestTrack);


        int reduce = Stream.of(1, 2, 3) .reduce(0, (acc, element) -> acc + element);
        assertEquals(6, reduce);


        BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
        int apply = accumulator.apply( accumulator.apply( accumulator.apply(0, 1), 2), 3);





    }

}
