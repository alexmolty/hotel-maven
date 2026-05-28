package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int roomNumber;
    private final RoomType type;

    public Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = Objects.requireNonNull(type, "Room type cannot be null");
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Room №%d | Type: %s", roomNumber, type.getRoomTypeName());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
