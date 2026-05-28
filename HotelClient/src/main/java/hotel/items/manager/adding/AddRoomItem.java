package hotel.items.manager.adding;

import hotel.items.HotelItem;
import model.Room;
import model.RoomType;
import hotel.resources.HotelApplContext;

public class AddRoomItem extends HotelItem {
    public AddRoomItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Add room";
    }

    @Override
    public void perform() {
        Integer number = inOut.inputInteger("Enter new room number:");
        if (number == null) return;
        if (infoService.getRooms().containsKey(number)) {
            inOut.outputLine("Room already exists.");
            return;
        }
        RoomType roomType = getExistingRoomType();
        if (roomType == null) return;
        Room room = new Room(number, roomType);
        boolean added = managingService.registerRoom(room);
        if (added) {
            inOut.outputLine("Room added successfully.");
            return;
        }
        inOut.outputLine("Something went wrong while adding new room.");
    }
}
