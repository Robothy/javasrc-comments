package test.java.util;

import org.junit.jupiter.api.Test;

public class ArrayListTest {

    @Test
    public void why_MAX_ARRAY_SIZE_is_MAX_VALUE_subtract_8(){
        int i = 10;
        while (i < Integer.MAX_VALUE / 1.5){
            i = i + (i>>1);
            System.out.println(i);
        }
    }

}
