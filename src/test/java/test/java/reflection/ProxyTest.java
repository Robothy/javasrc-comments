package test.java.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Proxy 专门完成代理的操作类，是所有动态代理类的父类。通过此类为一个或多个接口动态地实现类。
public class ProxyTest {

    private InvocationHandler handler = (proxy, method, args) -> {
        if (method.getName().equals("read")) {
            return "reading";
        } else if ("write".equals(method.getName())) {
            return "writing";
        } else {
            return null;
        }
    };


    //通过动态代理创建对象
    @Test
    void createInstanceByProxy() {


        Object o = Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class[]{Readable.class, Writable.class}, handler);
        Readable o1 = (Readable) o;
        Writable o2 = (Writable) o;
        assertEquals("reading", o1.read());
        assertEquals("writing", o2.write());
    }

    // 创建代理类的 Class 实例
    @Test
    void createClassInstance() throws IllegalAccessException, InstantiationException {
        Class<?> proxyClass = Proxy.getProxyClass(this.getClass().getClassLoader(), Readable.class, Writable.class);
        Object o = proxyClass.newInstance();
        assertTrue(o instanceof Readable);
        assertTrue(o instanceof Writable);
    }


}


interface Readable {
    String read();
}

interface Writable {
    String write();
}
