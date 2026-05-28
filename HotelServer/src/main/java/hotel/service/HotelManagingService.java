package hotel.service;

import interfaces.IHotelManagingService;
import model.*;

import java.time.LocalDate;

import java.util.Objects;

public class HotelManagingService implements IHotelManagingService {
    private final Hotel hotel;

    public HotelManagingService(Hotel hotel) {
        this.hotel = Objects.requireNonNull(hotel);
    }

    @Override
    public boolean registerRoomType(RoomType roomType) {
        Objects.requireNonNull(roomType, "RoomType cannot be null");
        String nameNormalized = hotel.normalizeRoomTypeName(roomType.getRoomTypeName());
        if (hotel.getRoomTypes().containsKey(nameNormalized)) {
            throw new IllegalStateException("Room type already exists in this hotel.");
        }
        RoomType roomTypeNormalized = new RoomType(nameNormalized, roomType.getPricePerNight(), roomType.getCapacity());
        hotel.addRoomType(roomTypeNormalized);
        return true;
    }

    @Override
    public boolean registerRoom(Room room) {
        Objects.requireNonNull(room, "Room cannot be null");
        String name = hotel.normalizeRoomTypeName(room.getType().getRoomTypeName());
        Integer roomNumber = room.getRoomNumber();
        if (!hotel.getRoomTypes().containsKey(name)) {
            throw new IllegalStateException("Room type does not exist in this hotel.");
        }
        if (hotel.getRooms().containsKey(roomNumber)) {
            throw new IllegalStateException("This room already exists in this hotel.");
        }
        hotel.addRoom(room);
        return true;
    }

    @Override
    public void registerGuest(Guest guest) {
        Objects.requireNonNull(guest, "Guest cannot be null");
        if (hotel.getGuests().containsKey(guest.getId())) {
            throw new IllegalStateException("Guest already exists in this hotel.");
        }
        hotel.addGuest(guest);
    }

    private boolean isRoomAvailableForDates(Room room, LocalDate start, LocalDate end) {
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(start, "Start date cannot be null");
        Objects.requireNonNull(end, "End date cannot be null");
        return hotel.getBookings().values().stream()
                .filter(b -> b.getRoom().equals(room))
                .noneMatch(b -> b.overlaps(start, end));
    }

    @Override
    public Booking createBooking(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        Objects.requireNonNull(guest, "Guest cannot be null");
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(checkIn, "Start date cannot be null");
        Objects.requireNonNull(checkOut, "End date cannot be null");
        if (!hotel.getGuests().containsKey(guest.getId())) {
            throw new IllegalArgumentException("Guest " + guest.getName() + " does not registered in this hotel.");
        }
        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check out date cannot be before check in date date.");
        }
        if (!hotel.getRooms().containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number " + room.getRoomNumber() + " does not exist.");
        }
        if (!this.isRoomAvailableForDates(room, checkIn, checkOut)) {
            throw new IllegalStateException("Room " + room.getRoomNumber() + " is occupied for selected dates.");
        }
        Booking booking = new Booking(guest, room, checkIn, checkOut);
        hotel.addBooking(booking);
        return booking;
    }

    @Override
    public Booking createBooking(Booking booking) {
        Objects.requireNonNull(booking, "Booking cannot be null");
        if (!hotel.getGuests().containsKey(booking.getGuest().getId())) {
            throw new IllegalArgumentException("Guest " + booking.getGuest().getName() + " does not registered in this hotel.");
        }
        if (booking.getCheckOut().isBefore(booking.getCheckIn())) {
            throw new IllegalArgumentException("Check out date cannot be before check in date date.");
        }
        if (!hotel.getRooms().containsKey(booking.getRoom().getRoomNumber())) {
            throw new IllegalArgumentException("Room number " + booking.getRoom().getRoomNumber() + " does not exist.");
        }
        if (!this.isRoomAvailableForDates(booking.getRoom(), booking.getCheckIn(), booking.getCheckOut())) {
            throw new IllegalStateException("Room " + booking.getRoom().getRoomNumber() + " is occupied for selected dates.");
        }
        Booking.synchronizedCounter(booking.getBookingId());
        hotel.addBooking(booking);
        return booking;
    }

    @Override
    public boolean removeBooking(int bookingId) {
        Booking bookingDeleted = hotel.removeBooking(bookingId);
        return bookingDeleted != null;
    }

    @Override
    public boolean removeRoom(int roomNumber) {
        if (!hotel.getRooms().containsKey(roomNumber)) {
            throw new IllegalStateException("Room " + roomNumber + " does not exist in this hotel.");
        }
        boolean hasRelatedBookings = hotel.getBookings().values().stream()
                .anyMatch(b -> b.getRoom().getRoomNumber() == roomNumber);
        if (hasRelatedBookings) {
            throw new IllegalStateException("Cannot remove room " + roomNumber + " because of related bookings.");
        }
        Room roomToDelete = hotel.removeRoom(roomNumber);
        return roomToDelete != null;
    }

    @Override
    public boolean removeRoomType(String roomTypeName) {
        if (roomTypeName == null || roomTypeName.isBlank()) {
            throw new IllegalArgumentException("Room type name cannot be null or empty.");
        }
        boolean typeIsUsedByRooms = hotel.getRooms().values().stream()
                .map(Room::getType)
                .anyMatch(t -> t.getRoomTypeName().equalsIgnoreCase(roomTypeName));
        if (typeIsUsedByRooms) {
            throw new IllegalStateException("Cannot remove room type " + roomTypeName);
        }
        RoomType roomTypeToDelete = hotel.removeRoomType(roomTypeName);
        return roomTypeToDelete != null;
    }

    @Override
    public boolean removeGuest(int guestId) {
        boolean guestHaveBookings = hotel.getBookings().values().stream()
                .map(Booking::getGuest)
                .anyMatch(g -> g.getId() == guestId);
        if (guestHaveBookings) {
            throw new IllegalStateException("Cannot remove guest " + guestId + " because of related bookings.");
        }
        Guest guestToDelete = hotel.removeGuest(guestId);
        return guestToDelete != null;
    }

    // TODO changeCheckIn changeCheckOut
}


