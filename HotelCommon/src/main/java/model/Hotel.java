package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Hotel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String hotelName;
    private final Map<String, RoomType> roomTypes; // key - roomtype name
    private final Map<Integer, Room> rooms; // key - room number
    private final Map<Integer, Guest> guests; // key - guest id
    private final Map<Integer, Booking> bookings; // key - booking id
    private final Map<LocalDate, List<Booking>> bookingsCheckInDate;


    public Hotel(String hotelName) {
        if (hotelName == null || hotelName.isBlank()) {
            throw new IllegalArgumentException("Hotel name cannot be null or blank");
        }

        this.hotelName = hotelName;
        this.roomTypes = new HashMap<>();
        this.rooms = new HashMap<>();
        this.guests = new HashMap<>();
        this.bookings = new HashMap<>();
        this.bookingsCheckInDate = new TreeMap<>();

    }

    public void addGuest(Guest guest) {
        guests.put(guest.getId(), guest);
    }

    public void addRoomType(RoomType roomType) {
        String name = normalizeRoomTypeName(roomType.getRoomTypeName());
        roomTypes.put(name, roomType);
    }

    public void addRoom(Room newRoom) {
        rooms.put(newRoom.getRoomNumber(), newRoom);
    }

    public String normalizeRoomTypeName(String roomTypeName) {
        return roomTypeName.trim().toUpperCase();
    }

    public void addBooking(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
        addBookingToDate(booking);
        Booking.synchronizedCounter(booking.getBookingId());
    }

    private void addBookingToDate(Booking booking) {
        LocalDate checkIn = booking.getCheckIn();
        bookingsCheckInDate.computeIfAbsent(checkIn, k -> new ArrayList<>()).add(booking);
    }

    public String getHotelName() {
        return hotelName;
    }

    public Map<String, RoomType> getRoomTypes() {
        return Collections.unmodifiableMap(roomTypes);
    }

    public Map<Integer, Room> getRooms() {
        return Collections.unmodifiableMap(rooms);
    }

    public Map<Integer, Guest> getGuests() {
        return Collections.unmodifiableMap(guests);
    }

    public Map<Integer, Booking> getBookings() {
        return Collections.unmodifiableMap(bookings);
    }

    public Map<LocalDate, List<Booking>> getBookingsCheckInDate() {
        return Collections.unmodifiableMap(bookingsCheckInDate);
    }

    public Guest removeGuest(int guestId) {
        return guests.remove(guestId);
    }

    public RoomType removeRoomType(String roomTypeName) {
        roomTypeName = normalizeRoomTypeName(roomTypeName);
        return roomTypes.remove(roomTypeName);
    }

    public Room removeRoom(int roomNumber) {
        return rooms.remove(roomNumber);
    }

    public Booking removeBooking(int bookingId) {
        Booking deletedBooking = bookings.remove(bookingId);

        if (deletedBooking != null) {
            LocalDate checkIn = deletedBooking.getCheckIn();
            List<Booking> listByDate = bookingsCheckInDate.get(checkIn);

            if (listByDate != null) {
                listByDate.remove(deletedBooking);
                if (listByDate.isEmpty()) {
                    bookingsCheckInDate.remove(checkIn);
                }
            }
        }
        return deletedBooking;
    }
}
