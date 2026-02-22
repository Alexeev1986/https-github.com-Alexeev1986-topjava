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
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management  (ARM)
        //System.setProperty("spring.profiles.active", "inmemory");
        System.setProperty("spring.profiles.active", "jdbc");
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("=== Active profiles: " +
                    Arrays.toString(appCtx.getEnvironment().getActiveProfiles()));
            System.out.println("=== MealRepository beans: " +
                    Arrays.toString(appCtx.getBeanNamesForType(
                            ru.javawebinar.topjava.repository.MealRepository.class)));

            System.out.println("Bean definition names: ");
            Arrays.stream(appCtx.getBeanDefinitionNames()).forEach(System.out::println);
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email1@mail.ru", "password", Role.ADMIN));
            System.out.println(SecurityUtil.authUserId());

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
}
