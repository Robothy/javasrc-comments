package test.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringBufferTest {

    void test(){

    }

    @Test
    void toStringTest(){
        StringBuffer stringBuffer = new StringBuffer("Buffer");
        Assertions.assertNotSame(stringBuffer.toString(), stringBuffer.toString());
    }

}
