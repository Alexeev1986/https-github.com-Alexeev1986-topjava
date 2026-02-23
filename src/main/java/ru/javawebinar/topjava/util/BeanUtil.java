package ru.javawebinar.topjava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.ConfigurableApplicationContext;

public class BeanUtil {
    public static void printBeans(ConfigurableApplicationContext appCtx) {
        System.out.println("=== Active profiles: " +
                Arrays.toString(appCtx.getEnvironment().getActiveProfiles()));

        System.out.println("=== MealRepository beans: " +
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
                String result = "Bean : " + name + ", Class: " + bean.getClass().getName();
                if (beanClassName.startsWith("ru.javawebinar.topjava")) {
                    beans.add(result);
                } else {
                    infrastructureBeans.add(result);
                }
            } catch (Exception e) {
                System.out.println(name + "[Definition only, not instantiated or complex]");
            }
        }
        System.out.println("YOUR APPLICATION BEANS(" + beans.size() + "): ");
        beans.forEach(System.out::println);
        System.out.println("END YOUR BEANS");
        System.out.println("INFRASTRUCTURE BEANS: ");
        infrastructureBeans.forEach(System.out::println);
        System.out.println("END INFRASTRUCTURE BEANS");
    }
}
