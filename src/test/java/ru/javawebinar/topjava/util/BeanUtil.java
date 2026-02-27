package ru.javawebinar.topjava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;

public class BeanUtil {
    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

    public static void printBeans(ConfigurableApplicationContext appCtx) {

        log.info("\n=== Active profiles: {} ===\n=== MealRepository beans: {} ===",
                Arrays.toString(appCtx.getEnvironment().getActiveProfiles()),
                Arrays.toString(appCtx.getBeanNamesForType(
                        ru.javawebinar.topjava.repository.MealRepository.class)));
        String[] beanNames = appCtx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        List<String> beans = new ArrayList<>();
        List<String> infrastructureBeans = new ArrayList<>();
        for (String name : beanNames) {
            try {
                Object bean = appCtx.getBean(name);
                String beanClassName = bean.getClass().getName();
                String result = "Bean : " + name + ", Class: " + beanClassName;
                if (beanClassName.startsWith("ru.javawebinar.topjava")) {
                    beans.add(result);
                } else {
                    infrastructureBeans.add(result);
                }
            } catch (BeansException e) {
                log.debug("Bean '{}' definition exist but cannot be instantiated: {}", name, e.getMessage());
            }
        }
        StringBuilder output = new StringBuilder();
        output.append("\nYOUR APPLICATION BEANS(").append(beans.size()).append("):\n");
        beans.forEach(bean -> output.append(bean).append("\n"));
        output.append("END YOUR BEANS\n");
        output.append("INFRASTRUCTURE BEANS: \n");
        infrastructureBeans.forEach(bean -> output.append(bean).append("\n"));
        output.append("END INFRASTRUCTURE BEANS\n");
        log.info(output.toString());
    }
}