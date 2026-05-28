package hotel.items.manager.adding;

import hotel.items.HotelItem;
import model.Guest;
import hotel.resources.HotelApplContext;

public class AddGuestItem extends HotelItem {
    public AddGuestItem(HotelApplContext context) {
        super(context);
    }


    @Override
    public String displayName() {
        return "Add guest";
    }

    @Override
    public void perform() {
        Guest guest = inputNewGuest();
        if (guest == null) return;
        managingService.registerGuest(guest);
        inOut.outputLine("Guest added successfully");
    }
}
