package test.java.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MutableStringTest {

    static class StringBufferProxy implements InvocationHandler {

        private StringBuffer stringBuffer;

        Object bind(StringBuffer stringBuffer){
            this.stringBuffer = stringBuffer;
            return stringBuffer;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            char[] toStringCache;

            // 拦截
            if("append".equals(method.getName())
                    && args.length == 1
                    && method.getParameterTypes()[0] == String.class){
                Field toStringCacheField = StringBuffer.class.getDeclaredField("toStringCache");
                toStringCacheField.setAccessible(true);
                toStringCache = (char[]) toStringCacheField.get(this.stringBuffer);
                Object returnValue = method.invoke(this.stringBuffer, args);
                toStringCacheField.set("toStringCache", toStringCache);
                return returnValue;
            }else {
                return method.invoke(this.stringBuffer, args);
            }
        }
    }

}
