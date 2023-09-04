package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.test.service.HelloService;

import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.*;

public class PointcutExpressionTest {

    @Test
    public void testPointcutExpression() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* org.springframework.test.service.TestService.*(..))");
        Class<HelloService> helloServiceClass = HelloService.class;
        Method method = helloServiceClass.getMethod("sayHello");

        assertThat(pointcut.matches(helloServiceClass)).isTrue();
        assertThat(pointcut.matches(method, helloServiceClass)).isTrue();

    }
}
