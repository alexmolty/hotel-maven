package hotel.items.manager.showinfo;

import hotel.items.HotelItem;
import model.RoomType;
import hotel.resources.HotelApplContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowRoomTypesItem extends HotelItem {
    public ShowRoomTypesItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show room types";
    }

    @Override
    public void perform() {
        List<RoomType> roomTypes = new ArrayList<>(infoService.getRoomTypes().values());
        if (roomTypes.isEmpty()) {
            inOut.outputLine("No room types found");
            return;
        }
        roomTypes.stream()
                .sorted(Comparator.comparing(RoomType::getPricePerNight))
                .forEach(inOut::outputLine);
    }
}
