package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public class Guest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String name;
    private final String email;
    private transient String password;
    private final LocalDate birthDate;

    private static final String EMAIL_REGEX = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Guest(int id, String name, String email, String password, LocalDate birthDate) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(birthDate, "Birth date cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date is incorrect");
        }
        this.id = id;
        this.name = name.trim();
        this.email = validateEmail(email);
        this.password = password;
        this.birthDate = birthDate;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    private String validateEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        email = email.trim();
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email;
    }

    @Override
    public String toString() {
        return String.format("Guest #%d | %s | %s | Age: %d",
                id, name, email, getAge());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id == guest.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
