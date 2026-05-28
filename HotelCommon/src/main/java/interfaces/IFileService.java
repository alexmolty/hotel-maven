package interfaces;

import model.Booking;
import model.Guest;
import model.Room;
import model.RoomType;

import java.util.Map;

public interface IFileService {
    void saveGuests(String filename);

    void saveRoomTypes(String filename);

    void saveBookings(String filename);

    void saveRooms(String filename);

    Map<Integer, Guest> readGuests(String filename);

    Map<String, RoomType> readRoomTypes(String filename);

    Map<Integer, Booking> readBookings(String filename);

    Map<Integer, Room> readRooms(String filename);
}
