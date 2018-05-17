package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class ControllerScanner {

    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(annotatedClasses);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotatedClasses) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : annotatedClasses) {
            try {
                controllers.put(clazz, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        return controllers;
    }
}
