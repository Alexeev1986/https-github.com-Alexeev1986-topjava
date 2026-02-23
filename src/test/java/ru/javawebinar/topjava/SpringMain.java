package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management  (ARM)
        //System.setProperty("spring.profiles.active", "inmemory");
        System.setProperty("spring.profiles.active", "jdbc");
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {


           printBeans(appCtx);

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            //adminUserController.create(new User(null, "userName", "email1@mail.ru", "password", Role.ADMIN));

            System.out.println("Authorization user: " + SecurityUtil.authUserId());

            MealRestController mealController = appCtx.getBean(MealRestController.class);

            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();

            mealController.getBetween(null, null, null, null).forEach(System.out::println);
        }
    }

    private static void printBeans(ConfigurableApplicationContext appCtx) {
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
        System.out.println("YOUR APPLICATION BEANS: ");
        beans.forEach(System.out::println);
        System.out.println("END YOUR BEANS");
        System.out.println("INFRASTRUCTURE BEANS: ");
        infrastructureBeans.forEach(System.out::println);
        System.out.println("END INFRASTRUCTURE BEANS");
    }
}
