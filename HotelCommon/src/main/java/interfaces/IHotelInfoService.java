package interfaces;

import model.Booking;
import model.Guest;
import model.Room;
import model.RoomType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service for retrieving information and calculating analytics.
 * Provides read-only access to hotel data, availability checks, and statistics.
 */
public interface IHotelInfoService {
    /**
     * Retrieves Hotel name string
     *
     * @return the string of a hotel name
     */
    String getHotelName();


    // --- Find Single Entities ---

    /**
     * Finds a room by its number.
     *
     * @param roomId the room number.
     * @return the room object, or null if not found.
     */
    Room findRoomByNumber(int roomId);

    /**
     * Finds a guest by their ID.
     *
     * @param guestId the guest ID.
     * @return the guest object, or null if not found.
     */
    Guest findGuestById(int guestId);

    /**
     * Finds a room type by its name.
     *
     * @param roomTypeName the name of the room type.
     * @return the room type object, or null if not found.
     * @throws IllegalArgumentException if the provided name is null or empty.
     */
    RoomType findRoomTypeByName(String roomTypeName);

    /**
     * Finds a booking by its ID.
     *
     * @param bookingId the booking ID.
     * @return the booking object, or null if not found.
     */
    Booking findBookingById(int bookingId);

    // --- Get Collections ---

    /**
     * Retrieves all registered room types.
     *
     * @return an unmodifiable map of room types (key: name, value: RoomType).
     */
    Map<String, RoomType> getRoomTypes();

    /**
     * Retrieves all registered rooms.
     *
     * @return an unmodifiable map of rooms (key: room number, value: Room).
     */
    Map<Integer, Room> getRooms();

    /**
     * Retrieves all active and past bookings.
     *
     * @return an unmodifiable map of bookings (key: booking ID, value: Booking).
     */
    Map<Integer, Booking> getBookings();

    /**
     * Retrieves all registered guests.
     *
     * @return an unmodifiable map of guests (key: guest ID, value: Guest).
     */
    Map<Integer, Guest> getGuests();

    // --- Availability ---

    /**
     * Checks if a specific room is available on a given date.
     *
     * @param room the room to check.
     * @param date the specific date.
     * @return true if the room is available, false otherwise.
     * @throws NullPointerException if room or date is null.
     */
    boolean isRoomAvailableForDate(Room room, LocalDate date);

    /**
     * Checks if a specific room is available for a date range.
     *
     * @param room         the room to check.
     * @param checkInDate  the check-in date.
     * @param checkOutDate the check-out date.
     * @return true if the room is available, false otherwise.
     * @throws NullPointerException if any argument is null.
     */
    boolean isRoomAvailableForDates(Room room, LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Retrieves a list of all rooms available on a specific date.
     *
     * @param date the target date.
     * @return a list of available rooms.
     * @throws NullPointerException if the date is null.
     */
    List<Room> getAvailableRoomsForDate(LocalDate date);

    /**
     * Retrieves a list of all rooms available for a specified date range.
     *
     * @param checkInDate  the check-in date.
     * @param checkOutDate the check-out date.
     * @return a list of available rooms.
     * @throws IllegalArgumentException if the check-out date is before the check-in date.
     * @throws NullPointerException     if any date is null.
     */
    List<Room> getAvailableRoomsForDates(LocalDate checkInDate, LocalDate checkOutDate);

    /**
     * Retrieves a list of all unavailable rooms on a specific date.
     *
     * @param date the target date.
     * @return a list of available rooms.
     * @throws NullPointerException if the date is null.
     */
    List<Room> getOccupiedRoomsForDate(LocalDate date);

    // --- Statistics and Analytics ---

    /**
     * Gets the total number of bookings in the system.
     *
     * @return the total number of bookings.
     */
    int getNumberOfBookings();

    /**
     * Calculates the total income generated from all bookings.
     *
     * @return the total income in the designated currency.
     */
    double getTotalIncome();

    /**
     * Calculates the average price of a booking.
     *
     * @return the average booking price, or 0 if there are no bookings.
     */
    double getAverageBookingPrice();

    /**
     * Determines the most popular room type(s) based on the total number of bookings.
     *
     * @return a comma-separated string of the most popular room type names.
     */
    String getMostPopularRoomTypes();

    /**
     * Determines the most popular room types for a specific age range of guests.
     *
     * @param bookings a specific collection of bookings to analyze.
     * @param minAge   the minimum age (inclusive).
     * @param maxAge   the maximum age (inclusive).
     * @return a list of the most popular room type names.
     * @throws IllegalArgumentException if minAge is greater than maxAge.
     */
    List<String> getMostPopularRoomTypesForAgeRange(Map<Integer, Booking> bookings, int minAge, int maxAge);

    /**
     * Determines the most popular room types for a specific age range using all system bookings.
     *
     * @param minAge the minimum age (inclusive).
     * @param maxAge the maximum age (inclusive).
     * @return a list of the most popular room type names.
     * @throws IllegalArgumentException if minAge is greater than maxAge.
     */
    List<String> getMostPopularRoomTypesForAgeRange(int minAge, int maxAge);

    /**
     * Retrieves a list of bookings that start on a specific date.
     *
     * @param checkInDate the target check-in date.
     * @return a list of bookings starting on that date.
     * @throws NullPointerException if the date is null.
     */
    List<Booking> getBookingsStartOn(LocalDate checkInDate);

    /**
     * Retrieves all bookings associated with a specific guest.
     *
     * @param guestId the guest's ID.
     * @return a list of bookings belonging to the guest.
     */
    List<Booking> getBookingsByGuestsId(int guestId);
}