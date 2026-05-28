package hotel.items.manager.showinfo;

import hotel.items.HotelItem;
import model.Guest;
import hotel.resources.HotelApplContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowGuestsItem extends HotelItem {
    public ShowGuestsItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show guests";
    }

    @Override
    public void perform() {
        List<Guest> guests = new ArrayList<>(infoService.getGuests().values());
        if (guests.isEmpty()) {
            inOut.outputLine("No guests found");
            return;
        }
        guests.stream()
                .sorted(Comparator.comparing(Guest::getId))
                .forEach(inOut::outputLine);
    }
}
