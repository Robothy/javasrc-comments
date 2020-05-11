package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

//
public class FileInputStreamTest {

    static final String DIR = "src/test/resources/test/java/io";

    @BeforeEach
    void createFile() throws IOException {
        File file = new File(DIR + "/b.txt");
        Files.write( file.toPath(), "abc".getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        assertTrue(file.exists());
    }

    @AfterEach
    void deleteFile() throws IOException {
        File file = new File(DIR + "/b.txt");
        Files.delete(file.toPath());
        assertFalse(file.exists());
    }

    @Test
    void readData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(DIR + "/b.txt"));
        byte[] data = new byte[8];  // 字节数组
        int count;
        StringBuilder result = new StringBuilder();
        while ((count=fileInputStream.read(data))!=-1){
            result.append(new String(data, 0, count));
        }
        fileInputStream.close();
        assertEquals("abc", result.toString());
    }

}
