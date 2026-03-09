package ru.javawebinar.topjava.repository;

import java.util.List;
import ru.javawebinar.topjava.model.User;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(int id) {
        throw new UnsupportedOperationException("getWithMeals is not supported by this implementation");
    }
}