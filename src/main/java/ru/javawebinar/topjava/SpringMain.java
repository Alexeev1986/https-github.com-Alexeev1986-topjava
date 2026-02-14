package ru.javawebinar.topjava;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management  (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("\nBean definition names: ");
            String[] binsArr = appCtx.getBeanDefinitionNames();
            for (int i = 0; i < binsArr.length; i++) {
                System.out.println(i + ": " + binsArr[i]);
            }
            System.out.println("\nCreate user:");
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            try {
                Collection<MealTo> mealsTo = mealRestController.getAll();
                System.out.println("\ngetAll():");
                mealsTo.forEach(System.out::println);
                System.out.println("\nget(2):");
                System.out.println(mealRestController.get(2));
                Meal meal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Ужин", 557, 2);
                System.out.println("\nsave:");
                System.out.println(mealRestController.create(meal));
                System.out.println("\ndelete:");
                mealRestController.delete(2);
                System.out.println("\nupdate:");
                Meal upadateMeal = new Meal(13, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Ужин", 777, 1);
                mealRestController.update(13, upadateMeal);
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        }
    }
}
