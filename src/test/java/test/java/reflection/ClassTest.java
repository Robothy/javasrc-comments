package test.java.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

// java.lang.Class 是一个高度抽象的类，用来描述类。
//
public class ClassTest {

    //获取 Class 的实例
    @Test
    void testGetClassIntance() throws ClassNotFoundException {
        // 获取 Class 实例
        Class<?> studentClass = Student.class; // 方式 1
        //方式二，由于每个类在 JVM 中仅对应1个实例，所以不同方式获取的同一个类的 Class 实例是相同的。
        assertEquals(studentClass.hashCode(), Class.forName("test.java.reflection.Student").hashCode());

        //方式三
        assertEquals(studentClass.hashCode(), new Student().getClass().hashCode());
    }

    // 使用无参公有构造方法创建实例
    @Test
    void createInstanceByNonArgsConstructor() throws IllegalAccessException, InstantiationException {
        Student student = Student.class.newInstance();
        assertNotNull(student);
        assertEquals("Hello", student.write("Hello"));
    }

    // 使用有参公有构造方法创建实例
    @Test
    void createInstanceByPublicConstructorWithArgs() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Student> constructor = Student.class.getConstructor(String.class);
        Student bob = constructor.newInstance("Bob");
        assertEquals("Bob", bob.getName());
    }

    // 使用私有构造方法创建实例
    @Test
    void createInstanceByPrivateConstructorWithArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<Student> constructor = Student.class.getDeclaredConstructor(String.class, int.class);
        assertThrows(IllegalAccessException.class, () -> constructor.newInstance("Bob", 21));
        constructor.setAccessible(true);
        Student bob = constructor.newInstance("Bob", 21);
        assertEquals("Bob", bob.getName());
        assertEquals(21, bob.getAge());
    }

    // 获取并调用方法
    @Test
    void getMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Student student = new Student("Bob");

        /* 调用有参数的方法 */
        Method write = Student.class.getMethod("write", String.class);
        // 第一个参数是被调用的实例，第二个是被调用方法的参数
        // 如果调用静态方法，第一个参数可以为 null
        Object hello = write.invoke(student, "Hello");
        assertEquals("Hello", hello);

        /* 调用无参数的方法 */

        Method getName = Student.class.getMethod("getName");
        assertEquals("Bob", getName.invoke(student));
    }

    // 获取属性并设置对象的属性值
    @Test
    void getField() throws NoSuchFieldException, IllegalAccessException {
        assertThrows(NoSuchFieldException.class, ()->Student.class.getField("name"));
        Field name = Student.class.getDeclaredField("name");
        name.setAccessible(true);

        Student student = new Student();
        assertNull(student.getName());
        name.set(student, "Bob"); // 给 student 实例的name属性设置值 Bob
        assertEquals("Bob", student.getName());
    }
}

class Student extends Person implements Study, Movable{

    private String name;

    private int age;

    public Student(){}

    public Student(String name){
        this.name= name;
    }

    private Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String write(String something) {
        return something;
    }

    @Override
    public void move() {

    }
}

class Person{
    void speak(){
    }
}

interface Study {
    String write(String something);
}

interface Movable{
    void move();
}
