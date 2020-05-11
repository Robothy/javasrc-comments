package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

// Buffered Stream 是处理器与内存之间交互的操作
// 通过 Buffered Stream，在处理器与硬盘之间建立了缓冲
// 避免了处理器直接与硬盘进行交互，极大提高了处理器访问硬盘的速度。
public class BufferedInputOutputStream {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void bufferedInputStream() throws IOException {
        File file = new File(DIR + "/a.txt");
        String content = "Buffered Input Stream";
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] buff = new byte[8];
        int len;
        StringBuilder result = new StringBuilder();
        while ((len = bufferedInputStream.read(buff)) != -1){
            result.append(new String(buff, 0, len));
        }

        // 关闭流的时候遵循“先开后关”原则
        bufferedInputStream.close();
        fileInputStream.close();

        assertEquals(content, result.toString());
    }

    @Test
    void bufferedOutputStream() throws IOException{
        File file = new File(DIR + "/a.txt");
        String content = "Buffered Input Stream";
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(content.getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.close();

        assertEquals(content, new String(Files.readAllBytes(file.toPath())));
    }

}
