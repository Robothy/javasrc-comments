package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BufferedReaderWriterTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void bufferedReader() throws IOException {
        File file = new File(DIR + "/a.txt");
        String content = "Buffered Input Stream";
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        char[] buff = new char[8];
        int len;
        StringBuilder result = new StringBuilder();
        while ((len = bufferedReader.read(buff)) != -1){
            result.append(new String(buff, 0, len));
        }

        // 关闭流的时候遵循“先开后关”原则
        bufferedReader.close();
        fileReader.close();

        assertEquals(content, result.toString());
    }

    @Test
    void bufferedWriter() throws IOException {

        File file = new File(DIR + "/a.txt");
        String content = "Buffered Input Stream";
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();
        assertEquals(content, new String(Files.readAllBytes(file.toPath())));
    }

}
