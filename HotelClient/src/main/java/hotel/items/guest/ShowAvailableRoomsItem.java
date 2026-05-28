package hotel.items.guest;

import hotel.items.HotelItem;
import model.Room;
import hotel.resources.HotelApplContext;

import java.time.LocalDate;
import java.util.List;

public class ShowAvailableRoomsItem extends HotelItem {
    public ShowAvailableRoomsItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show available rooms";
    }

    @Override
    public void perform() {
        LocalDate[] dates = inputCheckInCheckOut();
        if (dates == null) return;
        List<Room> availableRooms = infoService.getAvailableRoomsForDates(dates[0], dates[1]);
        if (availableRooms.isEmpty()) {
            inOut.outputLine("There is no available rooms for this dates.");
            return;
        }
        inOut.outputLine("Available rooms for this dates: ");
        showRooms(availableRooms, "No available rooms for this dates.");
    }
}
