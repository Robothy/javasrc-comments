package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Objects;

public class InputStreamReaderTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void inputStreamReader() throws IOException {

        File file = new File(DIR + "/a.txt");
        String chinese = "中文字符";
        Files.write(file.toPath(), Collections.singletonList(chinese),// 创建一个GBK编码的文本文件并写入一些内容
                Charset.forName("GBK"), StandardOpenOption.CREATE_NEW);

        FileInputStream fileInputStream = new FileInputStream(file); // 创建一个文件输入流

        InputStreamReader inputStreamReader =   // 创建转换输入流，以GBK编码的格式进行转换
                new InputStreamReader(fileInputStream, "GBK");

        char[] buff = new char[8]; // 字符数组
        int len = 0;
        StringBuilder result = new StringBuilder();
        while ((len=inputStreamReader.read(buff))!= -1){
            result.append(new String(buff, 0, len));
        }
        inputStreamReader.close();
        fileInputStream.close();

        // 这里之所以要加上 \r\n 是因为前面Files往文件里面写的时候内容放在集合里，写入文件时默认会加上一个 lineSeparator
        Assertions.assertEquals(chinese+"\r\n", result.toString());


        /*--- 以其它编码读取 ---*/
        fileInputStream = new FileInputStream(file);
        inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);

        result = new StringBuilder();
        while((len = inputStreamReader.read(buff))!=-1){
            result.append(new String(buff, 0, len));
        }
        inputStreamReader.close();
        fileInputStream.close();
        Assertions.assertNotEquals(chinese+"\r\n", result.toString());
    }

}
