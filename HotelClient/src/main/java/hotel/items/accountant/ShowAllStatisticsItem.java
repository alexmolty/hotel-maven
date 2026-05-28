package hotel.items.accountant;

import hotel.items.HotelItem;
import model.Room;
import hotel.resources.HotelApplContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowAllStatisticsItem extends HotelItem {
    public ShowAllStatisticsItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show all statistics";
    }

    @Override
    public void perform() {
        LocalDate today = LocalDate.now();
        inOut.outputLine("--- All statistics for " + today.format(DateTimeFormatter.ofPattern(dateFormat)) + " ---");
        inOut.outputLine("Hotel name: " + infoService.getHotelName());
        inOut.outputLine("Room types: " + infoService.getRoomTypes().size());
        inOut.outputLine("Rooms: " + infoService.getRooms().size());
        inOut.outputLine("Guests: " + infoService.getGuests().size());
        inOut.outputLine("Bookings: " + infoService.getNumberOfBookings());
        inOut.outputLine("Average booking price: " + infoService.getAverageBookingPrice());
        inOut.outputLine("Most popular room types: " + infoService.getMostPopularRoomTypes());
        List<Room> availableRooms = infoService.getAvailableRoomsForDate(today);
        inOut.outputLine("Available rooms for today (" + availableRooms.size() + ") : ");
        for (Room room : availableRooms) {
            inOut.outputLine(room);
        }
        List<Room> occupiedRooms = infoService.getOccupiedRoomsForDate(today);
        inOut.outputLine("Occupied rooms for today (" + occupiedRooms.size() + ") : ");
        for (Room room : occupiedRooms) {
            inOut.outputLine(room);
        }
    }
}
