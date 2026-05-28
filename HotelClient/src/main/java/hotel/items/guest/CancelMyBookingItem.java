package hotel.items.guest;

import hotel.items.HotelItem;
import model.Booking;
import model.Guest;
import hotel.resources.HotelApplContext;

import java.util.List;

public class CancelMyBookingItem extends HotelItem {
    public CancelMyBookingItem(HotelApplContext context) {
        super(context);
    }
    @Override
    public String displayName() {
        return "Cancel my booking";
    }

    @Override
    public void perform() {
        Guest guest = getExistingGuest();
        if (guest == null) return;
        List<Booking> bookings = infoService.getBookingsByGuestsId(guest.getId());
        if (bookings.isEmpty()) {
            inOut.outputLine("No bookings found with guest id: " + guest.getId());
        }
        inOut.outputLine("Guest bookings: ");
        showBookings(bookings, "No bookings found with guest id: " + guest.getId());
        Integer bookingId = inOut.inputInteger("Enter booking id to cancel: ");
        if (bookingId == null) return;
        Booking booking = infoService.findBookingById(bookingId);
        if (booking == null || !booking.getGuest().equals(guest)) {
            inOut.outputLine("Booking does not belong to selected guest");
            return;
        }
        boolean removed = managingService.removeBooking(bookingId);
        inOut.outputLine(removed ? "Booking cancelled successfully" : "Booking not found");
    }
}
