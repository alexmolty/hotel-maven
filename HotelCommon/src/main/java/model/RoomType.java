package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class RoomType implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final double pricePerNight;
    private final int capacity;

    public RoomType(String name, double pricePerNight, int capacity) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("RoomType name cannot be empty");
        if (pricePerNight < 0) throw new IllegalArgumentException("Price per night cannot be negative");
        if (capacity < 0) throw new IllegalArgumentException("RoomType capacity cannot be negative");
        this.name = name.trim().toUpperCase();
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
    }

    public String getRoomTypeName() {
        return name;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return String.format("%s (Capacity: %d, Price: %.2f$)", name, capacity, pricePerNight);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoomType roomType = (RoomType) o;
        return Double.compare(pricePerNight, roomType.pricePerNight) == 0 && capacity == roomType.capacity && name.equals(roomType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toUpperCase(), pricePerNight, capacity);
    }
}
