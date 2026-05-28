package hotel.proxy;

import api.APIConstants;
import interfaces.*;
import model.*;
import client.TCPClientJava;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HotelTCPProxy extends TCPClientJava implements IHotelManagingService, IHotelInfoService, APIConstants {
    public HotelTCPProxy(String hostName, int port) throws Exception {
        super(hostName, port);
    }

    @Override
    public String getHotelName() {
        return sendRequest(GET_HOTEL_NAME, null);
    }

    @Override
    public Room findRoomByNumber(int number) {
        return sendRequest(GET_ROOM, number);
    }

    @Override
    public Guest findGuestById(int id) {
        return sendRequest(GET_GUEST, id);
    }

    @Override
    public RoomType findRoomTypeByName(String roomTypeName) {
        return sendRequest(GET_ROOM_TYPE, roomTypeName);
    }

    @Override
    public Booking findBookingById(int id) {
        return sendRequest(GET_BOOKING, id);
    }

    @Override
    public Map<String, RoomType> getRoomTypes() {
        return sendRequest(GET_ROOM_TYPES, null);
    }

    @Override
    public Map<Integer, Room> getRooms() {
        return sendRequest(GET_ROOMS, null);
    }

    @Override
    public Map<Integer, Booking> getBookings() {
        return sendRequest(GET_BOOKINGS, null);
    }

    @Override
    public Map<Integer, Guest> getGuests() {
        return sendRequest(GET_GUESTS, null);
    }

    @Override
    public boolean isRoomAvailableForDate(Room room, LocalDate localDate) {
        return sendRequest(IS_ROOM_AVAILABLE_DATE, new Object[]{room, localDate});
    }

    @Override
    public boolean isRoomAvailableForDates(Room room, LocalDate localDate, LocalDate localDate1) {
        return sendRequest(IS_ROOM_AVAILABLE_DATES, new Object[]{room, localDate, localDate1});
    }

    @Override
    public List<Room> getAvailableRoomsForDate(LocalDate localDate) {
        return sendRequest(GET_AVAILABLE_ROOMS_DATE, localDate);
    }

    @Override
    public List<Room> getAvailableRoomsForDates(LocalDate localDate, LocalDate localDate1) {
        return sendRequest(GET_AVAILABLE_ROOMS_DATES, new Object[]{localDate, localDate1});

    }

    @Override
    public List<Room> getOccupiedRoomsForDate(LocalDate localDate) {
        return sendRequest(GET_OCCUPIED_ROOMS_DATE, localDate);
    }

    @Override
    public int getNumberOfBookings() {
        return sendRequest(STATS_NUMBER_OF_BOOKINGS, null);
    }

    @Override
    public double getTotalIncome() {
        return sendRequest(STATS_TOTAL_INCOME, null);
    }

    @Override
    public double getAverageBookingPrice() {
        return sendRequest(STATS_AVERAGE_BOOKING_PRICE, null);
    }

    @Override
    public String getMostPopularRoomTypes() {
        return sendRequest(STATS_POPULAR_ROOM_TYPES, null);
    }

    @Override
    public List<String> getMostPopularRoomTypesForAgeRange(Map<Integer, Booking> map, int from, int to) {
        //todo: fix method
        return getMostPopularRoomTypesForAgeRange(from, to);
    }

    @Override
    public List<String> getMostPopularRoomTypesForAgeRange(int from, int to) {
        return sendRequest(STATS_POPULAR_ROOM_TYPES_BY_AGE, new Object[]{from, to});
    }

    @Override
    public List<Booking> getBookingsStartOn(LocalDate localDate) {
        return sendRequest(GET_BOOKINGS_BY_DATE, localDate);
    }

    @Override
    public List<Booking> getBookingsByGuestsId(int guestsId) {
        return sendRequest(GET_BOOKINGS_BY_GUEST, guestsId);
    }

    @Override
    public boolean registerRoomType(RoomType roomType) {
        return sendRequest(ADD_ROOM_TYPE, roomType);
    }

    @Override
    public boolean registerRoom(Room room) {
        return sendRequest(ADD_ROOM, room);
    }

    @Override
    public void registerGuest(Guest guest) {
        sendRequest(ADD_GUEST, guest);
    }

    @Override
    public Booking createBooking(Guest guest, Room room, LocalDate localDate, LocalDate localDate1) {
        return sendRequest(CREATE_BOOKING, new Object[]{guest, room, localDate, localDate1});
    }

    @Override
    public Booking createBooking(Booking booking) {
        sendRequest(ADD_BOOKING, booking);
        return booking;
    }

    @Override
    public boolean removeBooking(int bookingId) {
        return sendRequest(REMOVE_BOOKING, bookingId);
    }

    @Override
    public boolean removeRoom(int roomNumber) {
        return sendRequest(REMOVE_ROOM, roomNumber);
    }

    @Override
    public boolean removeRoomType(String roomTypeName) {
        return sendRequest(REMOVE_ROOM_TYPE, roomTypeName);
    }

    @Override
    public boolean removeGuest(int guestId) {
        return sendRequest(REMOVE_GUEST, guestId);
    }

    public boolean saveData() {
        return sendRequest(SAVE_DATA, null);
    }
}
