package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class Junit3TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith("test")) {
                logger.debug("{}", declaredMethod);
                int paramCount = declaredMethod.getParameterCount();

                if (paramCount > 0) {
                    declaredMethod.invoke(clazz.newInstance(), "Hello world");
                } else {
                    declaredMethod.invoke(clazz.newInstance());
                }
            }
        }
    }
}
