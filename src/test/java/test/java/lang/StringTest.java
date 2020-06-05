package test.java.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    void intern(){

        // JDK7 及以后版本的 JVM 将常量池从方法区中移动到了堆中。
        // StringBuilder 在堆中创建“计算机科学”对象，调用 intern 方法时，由于字符串常量池中
        // 没有“计算机科学”，属于首次遇到，所以字符串常量池会记录堆中的“计算机科学”对象的引用并返回
        // 所以此时堆中的 str1 和常量池中的 str1.intern() 是一样的。
        String str1 = new StringBuilder("计算机").append("科学").toString();
        Assertions.assertSame(str1.intern(), str1);

        // 同理，StringBuilder 在堆中创建“java”，而此时常量池中已经有了“java”。
        // 因此，调用 intern 方法时，返回的是常量池中“java”的引用，自然和堆中 StringBuilder
        // 创建的引用不一样。
        String str2 = new StringBuilder("ja").append("va").toString();
        Assertions.assertNotSame(str2.intern(), str2);

    }

    @Test
    void MAX_VALUE(){

        //对于所有的 i 满足 i > 0，Integer.MAX_VALUE - i >= 0
        for (int i=1; i<= Integer.MAX_VALUE && i > 0; i++){
            Assertions.assertTrue(Integer.MAX_VALUE - i >= 0);
        }

        // 对于所有的 i 满足 i < 0, Integer.MAX_VALUE - i < 0
        for (int i=-1; i>= Integer.MIN_VALUE && i<=-1; i--){
            Assertions.assertTrue(Integer.MAX_VALUE - i < 0);
        }

    }

}
