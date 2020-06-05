package test.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringBuilderTest {

    void test(){
        StringBuilder stringBuilder = new StringBuilder();

    }

    @Test
    void appendNull(){
        StringBuilder stringBuilder = new StringBuilder();
        String str = null;
        stringBuilder.append(str);
        Assertions.assertEquals("null", stringBuilder.toString());
    }

    void test(char a[]){

    }

}
