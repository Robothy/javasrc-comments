package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Objects;

// OutputStreamWriter 字符流按照字符编码转化为字节流之后输出
public class OutputStreamWriterTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void outputStreamWriter() throws IOException {
        PrintStream out = System.out;
        File file = new File(DIR + "/a.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
        String chinese = "中文字符";
        outputStreamWriter.write(chinese);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        fileOutputStream.close();
        Assertions.assertEquals(chinese, Files.readAllLines(file.toPath(), Charset.forName("GBK")).get(0));
    }

}
