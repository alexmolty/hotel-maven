package hotel.items.accountant;

import hotel.items.HotelItem;
import model.Booking;
import model.Room;
import hotel.resources.HotelApplContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowOccupancyForDateItem extends HotelItem {
    public ShowOccupancyForDateItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show occupancy for date";
    }

    @Override
    public void perform() {
        LocalDate selected = inOut.inputDate("Enter date in format: ", dateFormat);
        if (selected == null) return;
        List<Booking> bookings = infoService.getBookingsStartOn(selected);
        showBookings(bookings, "No bookings found for " + selected.format(DateTimeFormatter.ofPattern(dateFormat)));
        List<Room> rooms = infoService.getOccupiedRoomsForDate(selected);
        inOut.outputLine("Occupied rooms for " + selected.format(DateTimeFormatter.ofPattern(dateFormat)) + " (" + rooms.size() + "): " + rooms);
    }
}
