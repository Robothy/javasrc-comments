package test.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StringBufferTest {

    void test(){

    }

    @Test
    void toStringTest(){
        StringBuffer stringBuffer = new StringBuffer("Buffer");
        Assertions.assertNotSame(stringBuffer.toString(), stringBuffer.toString());
    }

    @Test
    void appendFloat(){
        StringBuffer stringBuffer = new StringBuffer();

        // StringBuffer 和 StringBuilder 在追加浮点数的时候会去除后面的 0
        stringBuffer.append(1.20);
        Assertions.assertEquals("1.2", stringBuffer.toString());
    }



}
