package test.java.io;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FileTest {

    private static final String DIR = "src/test/resources/test/java/io";

    @BeforeAll
    static void createFile() throws IOException {
        File file = new File(DIR + "/a.txt");
        Files.write( file.toPath(), "abc".getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        assertTrue(file.exists());
    }

    @AfterAll
    static void deleteFile() throws IOException {
        File file = new File(DIR + "/a.txt");
        Files.delete(file.toPath());
        assertFalse(file.exists());
    }

    // File 只能能够获取文件或者文件夹的基本信息，但是不能对文件的内容进行操作
    @Test
    void newFile(){
        File file = new File("src/test/resources/test/java/io/a.txt");
        assertTrue(file.exists());
        assertTrue(file.getAbsolutePath().endsWith(file.getPath()));
    }

    @Test
    void fileTraverse(){
        ArrayList<File> files = new ArrayList<>();
        fileTraverse(new File("src/test/resources/test/java"), files);
        assertTrue(files.size() > 0);
        System.out.println(files);
    }

    // 递归遍历某个文件夹下的所有文件
    void fileTraverse(File file, List<File> files){
        if(file.isFile()){
            files.add(file);
        }else {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                fileTraverse(listFile, files);
            }
        }
    }

}
