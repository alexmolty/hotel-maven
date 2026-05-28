package hotel.items;

import cli.InputOutput;
import cli.Item;
import interfaces.IHotelInfoService;
import interfaces.IHotelManagingService;
import model.*;
import hotel.resources.HotelApplContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public abstract class HotelItem implements Item {
    protected final HotelApplContext context;

    protected final InputOutput inOut;
    protected final IHotelInfoService infoService;
    protected final IHotelManagingService managingService;
    protected final String dateFormat;

    protected HotelItem(HotelApplContext context) {
        this.context = context;
        this.inOut = context.getInOut();
        this.infoService = context.getHotelInfoService();
        this.managingService = context.getHotelManagingService();
        this.dateFormat = context.getDateFormat();
    }

    protected Guest getExistingGuest() {
        Integer guestId = inOut.inputInteger("Enter guest ID: ");
        if (guestId == null) return null;
        Guest guest = infoService.findGuestById(guestId);
        if (guest == null) {
            inOut.outputLine("No guest found with ID: " + guestId);
        }
        return guest;
    }

    protected Room getExistingRoom() {
        Integer roomNumber = inOut.inputInteger("Enter room number: ");
        if (roomNumber == null) return null;
        Room room = infoService.findRoomByNumber(roomNumber);
        if (room == null) {
            inOut.outputLine("No room found with number: " + roomNumber);
        }
        return room;
    }

    protected Booking getExistingBooking() {
        Integer bookingId = inOut.inputInteger("Enter booking ID: ");
        if (bookingId == null) return null;
        Booking booking = infoService.findBookingById(bookingId);
        if (booking == null) {
            inOut.outputLine("No booking found with ID: " + bookingId);
        }
        return booking;
    }

    protected RoomType getExistingRoomType() {
        String roomTypeName = inOut.inputString("Enter room type name: ");
        if (roomTypeName == null) return null;
        RoomType roomType = infoService.findRoomTypeByName(roomTypeName);
        if (roomType == null) {
            inOut.outputLine("No room type found with name: " + roomTypeName);
        }
        return roomType;
    }

    protected LocalDate[] inputCheckInCheckOut() {
        LocalDate checkIn = inOut.inputDate("Enter check in date in format: ", dateFormat);
        if (checkIn == null) return null;
        LocalDate checkOut = inOut.inputDate("Enter check out date in format: ", dateFormat);
        if (checkOut == null) return null;
        if (!checkOut.isAfter(checkIn)) {
            inOut.outputLine("Check out must be after check in date.");
            return null;
        }
        return new LocalDate[]{checkIn, checkOut};
    }

    protected Guest inputNewGuest() {
        Integer guestId = inOut.inputInteger("Enter guest ID: ");
        if (guestId == null) return null;
        String name = inOut.inputString("Enter guest name: ");
        if (name == null) return null;
        String email = inOut.inputString("Enter guest email: ");
        if (email == null) return null;
        String password = inOut.inputString("Enter guest password: ");
        if (password == null) return null;
        LocalDate birthDate = inOut.inputDate("Enter birth date in format: ", dateFormat);
        if (birthDate == null) return null;
        return new Guest(guestId, name, email, password, birthDate);
    }

    protected void showBookings(List<Booking> bookings, String newMessage) {
        if (bookings == null || bookings.isEmpty()) {
            inOut.outputLine("No bookings found.");
            return;
        }
        bookings.stream().sorted(Comparator.comparing(Booking::getBookingId)).forEach(inOut::outputLine);
    }

    protected void showRooms(List<Room> rooms, String newMessage) {
        if (rooms == null || rooms.isEmpty()) {
            inOut.outputLine("No rooms found.");
            return;
        }
        rooms.stream()
                .sorted(Comparator.comparing(Room::getRoomNumber))
                .forEach(inOut::outputLine);
    }
}
