package ru.javawebinar.topjava.repository.datajpa;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudRepository;

    public DataJpaUserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            return crudRepository.save(user);
        } else {
            User existUser = crudRepository.getById(user.id());
            if (existUser != null) {
                existUser.setName(user.getName());
                existUser.setEmail(user.getEmail());
                existUser.setPassword(user.getPassword());
                existUser.setEnabled(user.isEnabled());
                existUser.setCaloriesPerDay(user.getCaloriesPerDay());
                return crudRepository.save(existUser);
            }
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    @Transactional(readOnly = true)
    public User getWithMeals(int id) {
        return crudRepository.getWithMeals(id);
    }
}
