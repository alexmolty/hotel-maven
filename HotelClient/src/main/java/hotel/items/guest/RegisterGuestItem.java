package hotel.items.guest;

import hotel.items.HotelItem;
import model.Guest;
import hotel.resources.HotelApplContext;

public class RegisterGuestItem extends HotelItem {
    public RegisterGuestItem(HotelApplContext context) {
        super(context);
    }
    @Override
    public String displayName() {
        return "Register guest";
    }

    @Override
    public void perform() {
        Guest guest = inputNewGuest();
        if(guest == null) {
            return;
        }
        managingService.registerGuest(guest);
        inOut.outputLine("Guest " + guest.getName() + " successfully registered.");
    }
}
