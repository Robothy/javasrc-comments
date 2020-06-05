package test.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class HashSetTest {

    @Test
    void add(){
        Set<Integer> set = new HashSet<>();
        Assertions.assertTrue(set.add(1));
        Assertions.assertFalse(set.add(1));
        Assertions.assertTrue(set.add(100));
        Assertions.assertFalse(set.add(100));

    }

}
