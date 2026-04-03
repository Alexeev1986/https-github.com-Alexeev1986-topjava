package ru.javawebinar.topjava.web.meal;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

@RestController
@RequestMapping(value = "profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {
    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(
                       @RequestParam(required = false) Integer id,
                       @RequestParam LocalDateTime dateTime,
                       @RequestParam String description,
                       @RequestParam int calories) {
        if (id == null) {
            super.create(new Meal(null, dateTime, description, calories));
        } else {
            super.update(new Meal(null, dateTime, description, calories), id);
        }
    }
}
