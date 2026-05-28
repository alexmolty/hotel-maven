package api;

public interface APIConstants {
    // Hotel Managing Service
    // add
    String ADD_ROOM_TYPE = "/room-type/add";
    String ADD_ROOM = "/room/add";
    String ADD_GUEST = "/guest/add";
    String ADD_BOOKING = "/booking/add";
    String CREATE_BOOKING = "/booking/create";
    //remove
    String REMOVE_ROOM_TYPE = "/room-type/remove";
    String REMOVE_ROOM = "/room/remove";
    String REMOVE_GUEST = "/guest/remove";
    String REMOVE_BOOKING = "/booking/remove";

    // Hotel Info Service
    String GET_HOTEL_NAME = "/hotel/name";

    // get single entity
    String GET_ROOM = "/room/get";
    String GET_GUEST = "/guest/get";
    String GET_ROOM_TYPE = "/room-type/get";
    String GET_BOOKING = "/booking/get";
    // get many
    String GET_ROOM_TYPES = "/room-type/get-all";
    String GET_ROOMS = "/room/get-all";
    String GET_GUESTS = "/guest/get-all";
    String GET_BOOKINGS = "/booking/get-all";
    // specific lists
    String GET_BOOKINGS_BY_DATE = "/booking/get-by-date";
    String GET_BOOKINGS_BY_GUEST = "/booking/get-by-guest";
    // checking
    String IS_ROOM_AVAILABLE_DATE = "/room/is-available-date";
    String IS_ROOM_AVAILABLE_DATES = "/room/is-available-dates";
    String GET_AVAILABLE_ROOMS_DATE = "/room/get-available-date";
    String GET_AVAILABLE_ROOMS_DATES = "/room/get-available-dates";
    String GET_OCCUPIED_ROOMS_DATE = "/room/get-occupied-date";
    // info service
    String STATS_NUMBER_OF_BOOKINGS = "/stats/bookings-count";
    String STATS_TOTAL_INCOME = "/stats/total-income";
    String STATS_AVERAGE_BOOKING_PRICE = "/stats/average-booking-price";
    String STATS_POPULAR_ROOM_TYPES = "/stats/popular-room-types";
    String STATS_POPULAR_ROOM_TYPES_BY_AGE = "/stats/popular-room-types-by-age";

    String SAVE_DATA = "/system/save";
}
