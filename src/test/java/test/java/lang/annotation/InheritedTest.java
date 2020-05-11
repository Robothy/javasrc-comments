package test.java.lang.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Inherited   -- 只能被用来标注“Annotation类型”，它所标注的Annotation具有继承性。
//假设，我们定义了某个 Annotaion，它的名称是 MyAnnotation，并且 MyAnnotation 被标注为 @Inherited。
//现在，某个类 Base 使用了MyAnnotation，则 Base 具有了"具有了注解 MyAnnotation"；
//现在，Sub 继承了 Base，由于 MyAnnotation 是 @Inherited的(具有继承性)，所以，Sub 也 "具有了注解 MyAnnotation"。
public class InheritedTest {

    @Test
    void test(){
        assertTrue(InheritableAnnotationFather.class.isAnnotationPresent(InheritableAnnotation.class));
        assertTrue(InheritableAnnotationFather.class.isAnnotationPresent(InheritableAnnotation.class));

        assertTrue(UnInheritableAnnotationFather.class.isAnnotationPresent(UnInheritableAnnotation.class));
        assertFalse(SonOfUnInheritableAnnotationFather.class.isAnnotationPresent(UnInheritableAnnotation.class));
    }

}

/*---------- Inherited -----------*/

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface InheritableAnnotation {}

@InheritableAnnotation
class InheritableAnnotationFather{}

class SonOfInheritableAnnotation extends InheritableAnnotationFather{

}

/*---------------Not Inherited----------------*/
@Retention(RetentionPolicy.RUNTIME)
@interface UnInheritableAnnotation{
}

@UnInheritableAnnotation
class UnInheritableAnnotationFather{

}

class SonOfUnInheritableAnnotationFather extends UnInheritableAnnotationFather{

}