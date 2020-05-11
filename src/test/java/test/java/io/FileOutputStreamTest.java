package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileOutputStreamTest {

    final static String DIR = "src/test/resources/test/java/io";

    @Test
    void writeData() throws IOException {
        File file = new File(DIR + "c.txt");
        FileOutputStream os = new FileOutputStream(file);
        os.write("abc".getBytes());
        os.flush(); // 将写到内存中的数据刷到硬盘
        os.close(); // 关闭流
        String abc = new String(Files.readAllBytes(file.toPath()));
        Assertions.assertEquals("abc", abc);
    }

    @AfterEach
    void deleteFile() throws IOException {
        File file = new File(DIR + "c.txt");
        Files.delete(file.toPath());
    }

}
