package ru.javawebinar.topjava.to;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import ru.javawebinar.topjava.util.UsersUtil;

public class UserTo extends BaseTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "[Имя] не должно быть пустым")
    @Size(min = 2, max = 100, message = "[Имя] размер должно быть между 5 и 100 символами")
    private String name;

    @Email(message = "[Почта] должна быть корректным email адресом")
    @NotBlank(message = "[Почта] не должна быть пустой")
    @Size(max = 128, message = "Размер [Почта] не более 128 символов")
    private String email;

    @NotBlank(message = "[Пароль] не должен быть пустым")
    @Size(min = 5, max = 128, message = "Размер [Пароль] должен быть между 5 и 128 символами")
    private String password;

    @Range(min = 10, max = 10000, message = "[Калории] должны быть в диапазоне от 10 до 10000")
    @NotNull(message = "[Калории] не должны быть пустыми")
    private Integer caloriesPerDay = UsersUtil.DEFAULT_CALORIES_PER_DAY;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, int caloriesPerDay) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCaloriesPerDay(Integer caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public Integer getCaloriesPerDay() {
        return caloriesPerDay;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", caloriesPerDay='" + caloriesPerDay + '\'' +
                '}';
    }
}
