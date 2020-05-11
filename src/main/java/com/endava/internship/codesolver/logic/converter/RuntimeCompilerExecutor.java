package com.endava.internship.codesolver.logic.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RuntimeCompilerExecutor {

    private String errorStack = "";

    public String getErrorStack() {
        return errorStack;
    }

    public List<Boolean> executeUserCode(@NotNull final String className, @NotNull final String code) {
        List<Boolean> result = new LinkedList<>();
        RuntimeCompiler r = new RuntimeCompiler();
        List<Method> allTests = new LinkedList<>();
        try {

            r.addClass(className, code);
            r.compile();

            final Class cls = r.getCompiledClass("com.endava.internship.codesolver.logic.converter.generated." + className);

            Object instance = cls.newInstance();

            Method[] allMethods = cls.getDeclaredMethods();

            allTests = Arrays.stream(allMethods)
                    .filter(m -> m.getName().startsWith("test") && (m.getGenericReturnType() == boolean.class))
                    .collect(Collectors.toList());

            for (Method m : allTests) {
                m.setAccessible(true);
                Object o = m.invoke(instance);
                result.add((Boolean) o);
            }

        } catch (InvocationTargetException ex) {
            log.error(ex.getCause().getMessage());
            result = new LinkedList<>(Arrays.asList(new Boolean[allTests.size()]));
            Collections.fill(result, Boolean.FALSE);

        } catch (Exception e) {
            log.error(e.getMessage());
            errorStack = r.getLogs();
        }

        return result;
    }

}
