package hotel.app.resources;

import hotel.service.FileService;
import model.Hotel;

public class DataManager {
    public static final String BOOKINGS_FILE = "bookings.data";
    public static final String ROOMS_FILE = "rooms.data";
    public static final String GUESTS_FILE = "guests.data";
    public static final String ROOM_TYPES_FILE = "room_types.data";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static void loadAllData(Hotel hotel, FileService fs) {
        fs.readRoomTypes(ROOM_TYPES_FILE).values().forEach(hotel::addRoomType);
        fs.readRooms(ROOMS_FILE).values().forEach(hotel::addRoom);
        fs.readGuests(GUESTS_FILE).values().forEach(hotel::addGuest);
        fs.readBookings(BOOKINGS_FILE).values().forEach(hotel::addBooking);
        System.out.println(">>> Data restored successfully.");
    }

    public static void saveAllData(Hotel hotel, FileService fs) {
        System.out.println(">>> Saving data to the files...");
        fs.saveRoomTypes(ROOM_TYPES_FILE);
        fs.saveRooms(ROOMS_FILE);
        fs.saveGuests(GUESTS_FILE);
        fs.saveBookings(BOOKINGS_FILE);
        System.out.println(">>> Data has been saved.");
    }
}
