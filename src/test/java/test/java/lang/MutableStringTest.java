package test.java.lang;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 通过反射和动态代理来使 String 的值可变。
public class MutableStringTest {

    @Test
    void test() throws IOException {
        StringBufferProxy stringBufferProxy = new StringBufferProxy();
        Object object = stringBufferProxy.bind(new StringBuffer());
        Appendable appendableObject = (Appendable) object;

        appendableObject.append("a");
        String a = appendableObject.toString();
        System.out.println(a);
        System.out.println(a.hashCode());
        appendableObject.append("b");
        System.out.println(a);
        System.out.println(a.hashCode());
        appendableObject.append("c");
        System.out.println(a);
        System.out.println(a.hashCode());
    }

    static class StringBufferProxy implements InvocationHandler {

        private StringBuffer stringBuffer;

        private char[] toStringCache = new char[100];

        Object bind(StringBuffer stringBuffer){
            this.stringBuffer = stringBuffer;
            return Proxy.newProxyInstance(StringBuffer.class.getClassLoader(), new Class[]{Appendable.class, CharSequence.class}, this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 拦截
            if("append".equals(method.getName())
                    && args.length == 1
                    && method.getParameterTypes()[0] == CharSequence.class){
                Field toStringCacheField = StringBuffer.class.getDeclaredField("toStringCache");
                toStringCacheField.setAccessible(true);

                Field valueField = StringBuffer.class.getSuperclass().getDeclaredField("value");
                valueField.setAccessible(true);

                Object returnValue = method.invoke(this.stringBuffer, args);
                char[] value = (char[])(valueField.get(this.stringBuffer));
                System.arraycopy(value, 0, this.toStringCache, 0, value.length);
                toStringCacheField.set(this.stringBuffer, this.toStringCache);
                return returnValue;
            }else {
                return method.invoke(this.stringBuffer, args);
            }
        }
    }

}
