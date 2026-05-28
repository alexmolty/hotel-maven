package hotel.items.accountant;

import hotel.items.HotelItem;
import hotel.resources.HotelApplContext;

import java.util.List;

public class ShowMostPopularRoomTypesForRangeAgeItem extends HotelItem {
    public ShowMostPopularRoomTypesForRangeAgeItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Show most popular room type for range age";
    }

    @Override
    public void perform() {
        Integer minAge = inOut.inputInteger("Enter minimum age (from 1 to 100)", 1, 100);
        if (minAge == null) return;
        Integer maxAge = inOut.inputInteger("Enter maximum age (from 1 to 100)", 1, 100);
        if (maxAge == null) return;
        List<String> roomTypes = infoService.getMostPopularRoomTypesForAgeRange(minAge, maxAge);
        if (roomTypes == null || roomTypes.isEmpty()) {
            inOut.outputLine("No room types found for this age range");
            return;
        }
        inOut.outputLine("Most popular room types for age range [" + minAge + "..." + maxAge + "] : " + roomTypes);
    }
}
