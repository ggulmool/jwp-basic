package core.ref;

import next.model.Answer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<?> clazz = Question.class;
        showClassInfo(clazz);

        clazz = User.class;
        showClassInfo(clazz);

        clazz = Answer.class;
        showClassInfo(clazz);
    }

    private void showClassInfo(Class<?> clazz) {
        logger.info("------------------------------------------------------------");
        logger.debug("#Class");
        logger.debug("{}", clazz.getName());

        logger.info("------------------------------------------------------------");
        logger.debug("#public Field");
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            logger.debug("{}", field);
        }
        logger.debug("#Declared Field");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            logger.debug("{}", declaredField);
        }

        logger.info("------------------------------------------------------------");
        logger.debug("#public Constructor");
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            logger.debug("{}", constructor);
        }
        logger.debug("#Declared Constructor");
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            logger.debug("{}", declaredConstructor);
        }
        logger.info("------------------------------------------------------------");
        logger.debug("#public Method");
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            // public한 모든 메소드를 가져온다. 부모의 상속 메소드 포함.
            logger.debug("{}", method);
        }
        logger.debug("#Declared Method");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            // Class의 정의한 메소드만
            logger.debug("{}", declaredMethod);
        }
    }

    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            logger.debug("{}", declaredConstructor);
            Object user = declaredConstructor.newInstance("ggulmool", "pass", "kny", "ggulmool@naver.com");
            logger.info("{}", (User) user);
        }
    }
    
    @Test
    public void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
        Student student = new Student();

        Field nameField = clazz.getDeclaredField("name");
        Field ageField = clazz.getDeclaredField("age");

        nameField.setAccessible(true);
        ageField.setAccessible(true);

        nameField.set(student, "ggulmool");
        ageField.setInt(student, 15);
        logger.debug("{}", student);
    }
}
