package hotel.items.manager.showinfo;

import hotel.items.HotelItem;
import hotel.resources.HotelApplContext;

import java.util.ArrayList;

public class ShowBookingsItem extends HotelItem {
    public ShowBookingsItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show bookings";
    }

    @Override
    public void perform() {
        showBookings(new ArrayList<>(infoService.getBookings().values()), "No bookings found");
    }
}
