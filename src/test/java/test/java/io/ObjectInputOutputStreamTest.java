package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

 // 序列化类和反序列化类结构，包名必须一致。
public class ObjectInputOutputStreamTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void objectInputOutputStream() throws IOException, ClassNotFoundException {
        File file = new File(DIR + "/a.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        A a = new A();
        a.name = "abc";
        // 将对象 a 序列化后写到文件中
        objectOutputStream.writeObject(a);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        // 从文件 IO流中反序列化，得到对象 a
        Object o = objectInputStream.readObject();
        Assertions.assertTrue(o instanceof A);
        Assertions.assertEquals("abc", ((A)o).name);
        objectInputStream.close();
        fileInputStream.close();
    }

}

class A implements Serializable {
    // serialVersionUID 表示类的版本号，序列化和反序列化对象的类必须相同，且序列号也必须相同
    // 否则，在反序列化的时候抛出 InvalidClassException.
    private static final Long serialVersionUID  = 1L;

    public String name;
}

