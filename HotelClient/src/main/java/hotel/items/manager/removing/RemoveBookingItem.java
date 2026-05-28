package hotel.items.manager.removing;

import hotel.items.HotelItem;
import hotel.resources.HotelApplContext;

public class RemoveBookingItem extends HotelItem {
    public RemoveBookingItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Remove booking";
    }

    @Override
    public void perform() {
        Integer bookingId = inOut.inputInteger("Enter booking id to remove");
        if (bookingId == null) return;
        boolean removed = managingService.removeBooking(bookingId);
        inOut.outputLine(removed ? "Booking removed successfully" : "Booking not found");
    }
}
