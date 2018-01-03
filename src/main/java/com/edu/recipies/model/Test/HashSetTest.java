package com.edu.recipies.model.Test;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class HashSetTest {

    @Data
    @AllArgsConstructor
    class User {
        String name;
        int    age;
    }

    public static void main(String[] args) {
        new HashSetTest().test();

    }

    void test() {
        User one = new User("Olga", 7);
        User two = new User("Gena", 10);

        HashSet<User> users = Sets.newHashSet(one, two);
        printCollection(users);
        one.setName("Gena");
        one.setAge(10);
        printCollection(users);
        User three = new User("Mike", 13);
        printCollection(users);
        for (int i = 0; i < 4; i++) {
            users.add(new User("Mike", 13 + i));
        }
        log.info("SIZE : {}", users.size());
//        users = Sets.newHashSet(users);
        printCollection(users);

        users.remove(new User("Gena", 10));
        printCollection(users);
        users.remove(new User("Olga", 7));
        printCollection(users);

    }

    void printCollection(Collection<User> collection) {
        collection.forEach(u -> log.info("User {} HC", u));
        log.info("-----------------------------------");
    }
}
