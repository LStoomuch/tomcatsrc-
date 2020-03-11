package org.apache.catalina.startup;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object o = myClassLoader.loadClass("org.apache.catalina.startup.ClassLoaderTest").newInstance();
        System.out.println(o.getClass());
        System.out.println(o instanceof org.apache.catalina.startup.ClassLoaderTest);
    }
}
