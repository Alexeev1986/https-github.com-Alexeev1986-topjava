package ru.javawebinar.topjava;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management  (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            try {
                Collection<Meal> meals = mealRestController.getAll();
                System.out.println("getAll():");
                meals.forEach(System.out::println);
                System.out.println("get(1):");
                System.out.println(mealRestController.get(1));
                Meal meal = new Meal(1, null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Ужин", 557);
                System.out.println("save:");
                System.out.println(mealRestController.create(meal));
                System.out.println("delete:");
                mealRestController.delete(1);
                System.out.println("update:");
                Meal upadateMeal = new Meal(1, 8, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Ужин", 777);
                mealRestController.update(upadateMeal);
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        }
    }
}
