package test.java.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomAccessFileTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @AfterEach
    void deleteFiles() throws IOException {
        File folder = new File(DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void randomAccessFile() throws IOException {
        File file = new File(DIR + "/a.txt");

        Files.write(file.toPath(), "abc".getBytes(), StandardOpenOption.CREATE_NEW);

        // 第二个参数指定访问的方式
        // r 只读
        // rw 读写
        // rwd 读，写，同步文件内容的更新
        // rws 读，写，同步文件内容和元数据的更新
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        // 设置读取文件内容的其始点，第 0 个字节
        randomAccessFile.seek(1);
        byte[] b = new byte[8];
        int len;
        StringBuilder res = new StringBuilder();
        while ((len=randomAccessFile.read(b))!=-1){
            res.append(new String(b, 0, len));
        }
        //因为从第1个字节开始读，所以忽略了第0个字节的 'a'
        assertEquals("bc", res.toString());
        randomAccessFile.close();


        randomAccessFile = new RandomAccessFile(file, "rw"); // 以读写的方式打开
        randomAccessFile.seek(2); // 指针移动到第2个字节
        randomAccessFile.write("de".getBytes());
        randomAccessFile.close();

        // 如果在文件的中间或者开头的位置写，则会覆盖掉与写入内容等长度的原内容
        assertEquals("abde", new String(Files.readAllBytes(file.toPath())));
    }

}
