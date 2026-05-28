package hotel.protocol;

import api.APIConstants;
import hotel.app.resources.HotelDataSaver;
import interfaces.*;
import model.*;
import protocol.*;

import java.io.Serializable;
import java.time.LocalDate;

import static protocol.TCPResponseCode.*;

public class HotelTCPProtocol implements ProtocolJava, APIConstants {
    private final IHotelManagingService managerService;
    private final IHotelInfoService infoService;
    private final HotelDataSaver dataSaver;

    public HotelTCPProtocol(IHotelManagingService managerService, IHotelInfoService infoService, HotelDataSaver dataSaver) {
        this.managerService = managerService;
        this.infoService = infoService;
        this.dataSaver = dataSaver;
    }

    @Override
    public ResponseJava getResponse(RequestJava request) {
        try {
            Serializable data = request.requestData;
            return switch (request.requestType) {
                case ADD_ROOM_TYPE -> ok(managerService.registerRoomType((RoomType) data));
                case ADD_ROOM -> ok(managerService.registerRoom((Room) data));
                case ADD_BOOKING -> addBooking(data);
                case ADD_GUEST -> addGuest(data);
                case CREATE_BOOKING -> createBooking(data);
                case REMOVE_ROOM_TYPE -> ok(managerService.removeRoomType((String) data));
                case REMOVE_ROOM -> ok(managerService.removeRoom((Integer) data));
                case REMOVE_BOOKING -> ok(managerService.removeBooking((Integer) data));
                case REMOVE_GUEST -> ok(managerService.removeGuest((Integer) data));
                case GET_HOTEL_NAME -> ok(infoService.getHotelName());
                case GET_ROOM_TYPE -> ok(infoService.findRoomTypeByName((String) data));
                case GET_ROOM -> ok(infoService.findRoomByNumber((Integer) data));
                case GET_BOOKING -> ok(infoService.findBookingById((Integer) data));
                case GET_GUEST -> ok(infoService.findGuestById((Integer) data));
                case GET_ROOM_TYPES -> ok((Serializable) infoService.getRoomTypes());
                case GET_ROOMS -> ok((Serializable) infoService.getRooms());
                case GET_BOOKINGS -> ok((Serializable) infoService.getBookings());
                case GET_GUESTS -> ok((Serializable) infoService.getGuests());
                case GET_BOOKINGS_BY_DATE -> ok((Serializable) infoService.getBookingsStartOn((LocalDate) data));
                case GET_BOOKINGS_BY_GUEST -> ok((Serializable) infoService.getBookingsByGuestsId((Integer) data));
                case IS_ROOM_AVAILABLE_DATE -> isRoomAvailableForDate(data);
                case IS_ROOM_AVAILABLE_DATES -> isRoomAvailableForDates(data);
                case GET_AVAILABLE_ROOMS_DATE ->
                        ok((Serializable) infoService.getAvailableRoomsForDate((LocalDate) data));
                case GET_AVAILABLE_ROOMS_DATES -> getAvailableRoomsForDates(data);
                case GET_OCCUPIED_ROOMS_DATE ->
                        ok((Serializable) infoService.getOccupiedRoomsForDate((LocalDate) data));
                case STATS_NUMBER_OF_BOOKINGS -> ok(infoService.getNumberOfBookings());
                case STATS_AVERAGE_BOOKING_PRICE -> ok(infoService.getAverageBookingPrice());
                case STATS_POPULAR_ROOM_TYPES -> ok(infoService.getMostPopularRoomTypes());
                case STATS_POPULAR_ROOM_TYPES_BY_AGE -> getMostPopularRoomTypesForAgeRange(data);
                case SAVE_DATA -> saveData();
                default -> wrong("Unknown request type: " + request.requestType);
            };
        } catch (Exception e) {
            return wrong(e.getMessage());
        }
    }

    private ResponseJava createBooking(Serializable data) {
        Object[] params = (Object[]) data;
        Booking booking = managerService.createBooking(
                (Guest) params[0],
                (Room) params[1],
                (LocalDate) params[2],
                (LocalDate) params[3]);
        return ok(booking);
    }

    private ResponseJava addBooking(Serializable data) {
        managerService.createBooking((Booking) data);
        return ok(true);
    }

    private ResponseJava getMostPopularRoomTypesForAgeRange(Serializable data) {
        Object[] params = (Object[]) data;
            return ok((Serializable)
                    infoService.getMostPopularRoomTypesForAgeRange(
                            (Integer) params[0],
                            (Integer) params[1]
                    ));
    }


    private ResponseJava getAvailableRoomsForDates(Serializable data) {
        Object[] params = (Object[]) data;
        return ok((Serializable) infoService.getAvailableRoomsForDates(
                (LocalDate) params[0],
                (LocalDate) params[1]
        ));
    }

    private ResponseJava isRoomAvailableForDates(Serializable data) {
        Object[] params = (Object[]) data;
        return ok(infoService.isRoomAvailableForDates(
                (Room) params[0],
                (LocalDate) params[1],
                (LocalDate) params[2]));
    }

    private ResponseJava isRoomAvailableForDate(Serializable data) {
        Object[] params = (Object[]) data;
        return ok(infoService.isRoomAvailableForDate(
                (Room) params[0],
                (LocalDate) params[1]
        ));
    }


    private ResponseJava saveData() {
        try {
            dataSaver.saveAll();
            return ok(true);
        } catch (Exception e) {
            return wrong(e.getMessage());
        }
    }

    private ResponseJava addGuest(Serializable data) {
        managerService.registerGuest((Guest) data);
        return ok(true);
    }

    private ResponseJava ok(Serializable data) {
        return new ResponseJava(OK, data);
    }

    private ResponseJava wrong(String message) {
        return new ResponseJava(WRONG_REQUEST, message);
    }
}
