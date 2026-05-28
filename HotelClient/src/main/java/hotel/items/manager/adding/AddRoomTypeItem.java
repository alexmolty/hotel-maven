package hotel.items.manager.adding;

import hotel.items.HotelItem;
import model.RoomType;
import hotel.resources.HotelApplContext;

public class AddRoomTypeItem extends HotelItem {
    public AddRoomTypeItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Add room type";
    }

    @Override
    public void perform() {
        String roomTypeName = inOut.inputString("Enter room type name: ");
        if (roomTypeName == null) return;
        if (infoService.findRoomTypeByName(roomTypeName) != null) {
            inOut.outputLine("Room type already exists.");
            return;
        }
        Double pricePerNight = inOut.inputDouble("Enter price per night: ");
        if (pricePerNight == null) return;
        Integer capacity = inOut.inputInteger("Enter capacity: ");
        if (capacity == null) return;
        RoomType roomType = new RoomType(roomTypeName, pricePerNight, capacity);
        boolean added = managingService.registerRoomType(roomType);
        if (added) {
            inOut.outputLine("Room type registered successfully.");
        }
    }
}
