package test.java.lang.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.annotation.XmlAttribute;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Annotation 中的元数据或属性通过Java反射机制来读取。
@DisplayName("Read Annotation Data")
public class ReadAnnotationDataTest {

    // 读取类注解的数据
    @Test
    void readFromClassAnnotation(){
        DisplayName annotation = getClass().getAnnotation(DisplayName.class);
        assertEquals("Read Annotation Data", annotation.value());
    }

    //读取方法注解的数据
    @Test
    @DisplayName("Read From Method Annotation")
    void readFromMethodAnnotation() throws NoSuchMethodException {
        DisplayName annotation = getClass().getAnnotation(DisplayName.class);
        assertEquals("Read From Method Annotation", annotation.value());
    }

    // 读取字段注解的数据
    @XmlAttribute(name = "Field", required = true)
    private String field;

    @Test
    void readFromFieldAnnotation() throws NoSuchFieldException {
        Field field = getClass().getField("field");
        XmlAttribute annotation = field.getAnnotation(XmlAttribute.class);
        assertEquals("Field", annotation.name());
        assertTrue(annotation.required());
    }

    // 读取当前方法注解数据
    @Test
    @DisplayName("Read current method annotation attributes.")
    public void readCurrentMethodAnnotation() throws NoSuchMethodException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Method method = getClass().getMethod(methodName);
        DisplayName annotation = method.getAnnotation(DisplayName.class);
        assertEquals("Read current method annotation attributes.", annotation.value());
    }

}