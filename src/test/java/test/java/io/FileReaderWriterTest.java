package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderWriterTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void fileReader() throws IOException {
        File file = new File(DIR + "/d.txt");
        Files.write(file.toPath(), "abc".getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        FileReader fileReader = new FileReader(file);
        char[] buff = new char[8]; // 字符数组
        int count;
        StringBuilder result = new StringBuilder();
        while((count=fileReader.read(buff))!=-1){   // 每次最多读取 8 个字符
            result.append(new String(buff, 0, count));
        }
        fileReader.close(); // 关闭流
        assertEquals("abc", result.toString());
    }

    @Test
    void fileWriter() throws IOException{
        File file = new File(DIR + "/d.txt");
        assertFalse(file.exists());
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("abc");
        fileWriter.flush(); // 将内存中的数据刷到硬盘
        fileWriter.close();// 关闭流

        // 如果文件不存在，fileWriter会自动创建文件；如果存在会将原来的文件覆盖掉
        assertTrue(file.exists());
        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("abc", content);
    }

    @Test
    void copyFile() throws IOException {
        File source = new File(DIR + "/d.txt");
        File target = new File(DIR + "/d1.txt");

        assertFalse(source.exists());
        assertFalse(source.exists());

        String content = "abc";
        Files.write(source.toPath(), content.getBytes(), StandardOpenOption.CREATE); //准备数据
        assertEquals(content, new String(Files.readAllBytes(source.toPath())));

        copyFile(source, target);
        assertEquals(content, new String(Files.readAllBytes(target.toPath())));
    }

    // 使用 FileReader 和 FileWriter 复制文件
    void copyFile(File source, File target) throws IOException {
        FileReader reader = new FileReader(source);
        FileWriter writer = new FileWriter(target);
        char[] buff = new char[8]; // 字符数组
        int count;
        while((count = reader.read(buff)) != -1){
            writer.write(buff, 0, count);
        }
        writer.flush();
        reader.close();
        writer.close();
    }
}
