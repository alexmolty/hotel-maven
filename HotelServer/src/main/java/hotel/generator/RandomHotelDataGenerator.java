package hotel.generator;

import model.Hotel;

import hotel.service.HotelManagingService;
import model.*;

import java.time.LocalDate;
import java.util.*;

public class RandomHotelDataGenerator {

    private final int MIN_ROOMS_NUMBER = 10;
    private final int MAX_ROOMS_NUMBER = 10;
    private final int GUESTS_COUNT = 10;
    private final int PERIOD_OF_RANDOM_BOOKINGS = 30;
    private final int BOOKINGS_COUNT = 15;
    private final int MIN_DAYS_TO_CHECKOUT = 1;
    private final int MAX_DAYS_TO_CHECKOUT = 10;
    private final int FIRST_ROOM_NUMBER = 100;
    private final int GUESTS_FIRST_ID = 10000;
    private final Random rand = new Random();
    private final Hotel hotel;
    private final HotelManagingService manager;
    private final LocalDate now = LocalDate.now();

    public RandomHotelDataGenerator(Hotel hotel, HotelManagingService manager) {
        this.hotel = hotel;
        this.manager = manager;

        Collection<RoomType> roomTypes = generateRoomTypes().values();
        roomTypes.forEach(manager::registerRoomType);

        Collection<Room> rooms = generateRooms(new ArrayList<>(roomTypes)).values();
        rooms.forEach(manager::registerRoom);

        Collection<Guest> guests = generateGuests().values();
        guests.forEach(hotel::addGuest);

        generateBookings(new ArrayList<>(guests), new ArrayList<>(rooms));
    }

    public Map<String, RoomType> generateRoomTypes() {
        Map<String, RoomType> map = new HashMap<>();
        map.put("STANDARD", new RoomType("STANDARD", 150.0, 2));
        map.put("SUPERIOR", new RoomType("SUPERIOR", 170.0, 2));
        map.put("DELUXE", new RoomType("DELUXE", 200.0, 3));
        map.put("ROYAL", new RoomType("ROYAL", 300.0, 4));
        return map;
    }

    public Map<Integer, Room> generateRooms(List<RoomType> typesList) {
        Map<Integer, Room> roomsMap = new HashMap<>();
        int count = rand.nextInt(MIN_ROOMS_NUMBER, MAX_ROOMS_NUMBER + 1);
        for (int i = 0; i < count; i++) {
            int roomNumber = FIRST_ROOM_NUMBER + i;
            RoomType randomType = typesList.get(rand.nextInt(typesList.size()));
            roomsMap.put(roomNumber, new Room(roomNumber, randomType));
        }
        return roomsMap;
    }

    public Map<Integer, Guest> generateGuests() {
        Map<Integer, Guest> guestsMap = new HashMap<>();
        for (int i = 1; i <= GUESTS_COUNT; i++) {
            int id = GUESTS_FIRST_ID + i;
            guestsMap.put(id, new Guest(id, "Guest" + i, "guest" + i + "@hotel.com", "pass" + i, generateBirthDate()));
        }
        return guestsMap;
    }

    private LocalDate generateBirthDate() {
        return now.minusYears(18 + rand.nextInt(50)).minusDays(rand.nextInt(365));
    }

    public void generateBookings(List<Guest> guestsList, List<Room> roomsList) {
        int attempts = 0;
        while (hotel.getBookings().size() < BOOKINGS_COUNT && attempts < 1000) {
            attempts++;
            Guest randGuest = guestsList.get(rand.nextInt(guestsList.size()));
            Room randRoom = roomsList.get(rand.nextInt(roomsList.size()));
            LocalDate checkIn = now.plusDays(rand.nextInt(PERIOD_OF_RANDOM_BOOKINGS));
            LocalDate checkOut = checkIn.plusDays(rand.nextInt(MIN_DAYS_TO_CHECKOUT, MAX_DAYS_TO_CHECKOUT));

            try {
                manager.createBooking(randGuest, randRoom, checkIn, checkOut);
            } catch (Exception e) {

            }
        }
    }
}