package hotel.items.manager.showinfo;

import hotel.items.HotelItem;
import hotel.resources.HotelApplContext;

import java.util.ArrayList;

public class ShowRoomsItem extends HotelItem {
    public ShowRoomsItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show rooms";
    }

    @Override
    public void perform() {
        showRooms(new ArrayList<>(infoService.getRooms().values()), "No rooms founded");
    }
}
