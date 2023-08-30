package org.springframework.test.ioc;

import cn.hutool.core.io.IoUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class ResourceAndResourceLoaderTest {

    @Test
    public void testResourceLoader() throws Exception {
        DefaultResourceLoader loader = new DefaultResourceLoader();

        // 加载classpath下的文件资源
        Resource resource = loader.getResource("classpath:hello.txt");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
        assertThat(content).isEqualTo("hello world");

        // 加载网络资源
        resource = loader.getResource("http://www.baidu.com");
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println(content);

        // 加载本地资源
        resource = loader.getResource("E:\\WangProject\\java\\MySpring\\src\\main\\resources\\hello.txt");
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
        assertThat(content).isEqualTo("hello world");


        /**
         * 动态生成类，去加载并执行方法
         */
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.wxy.testClass");
        String methodCode = "public int insert(){System.out.println(123); return 1;}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);
        //最后需要将方法添加到类中
        ctClass.addMethod(ctMethod);
        //在内存中生成class
        ctClass.toClass();
        //类加载到JVM当中 返回AccountDaoImpl 类的字节码
        Class<?> clazz = Class.forName("com.wxy.testClass");
        Object obj = clazz.newInstance();
        Method insert = clazz.getMethod("insert");
        Object invoke = insert.invoke(obj, null);
        System.out.println(invoke);
    }
}
