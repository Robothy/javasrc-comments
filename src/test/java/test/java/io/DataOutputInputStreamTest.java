package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class DataOutputInputStreamTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void dataOutputStream() throws IOException {
        File file = new File(DIR + "/a.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        dataOutputStream.writeInt(12);
        dataOutputStream.writeDouble(1.23d);

        dataOutputStream.flush();
        dataOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        int i = dataInputStream.readInt(); // 读取时和写入时的类型要一致
        double d = dataInputStream.readDouble();
        Assertions.assertEquals(12, i);
        Assertions.assertEquals(1.23d, d);
        fileInputStream.close();
        dataOutputStream.close();
    }

}
