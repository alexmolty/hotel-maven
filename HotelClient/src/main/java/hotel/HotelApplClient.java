package hotel;

import cli.*;
import interfaces.*;
import hotel.items.accountant.*;
import hotel.items.guest.*;
import hotel.items.manager.adding.*;
import hotel.items.manager.removing.*;
import hotel.items.manager.showinfo.*;
import hotel.menu.*;
import hotel.proxy.HotelTCPProxy;
import hotel.resources.HotelApplContext;

import java.util.List;

public class HotelApplClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    public static void main(String[] args) {
        try {
            InputOutput inOut = new ConsoleInputOutput();
            HotelTCPProxy proxy = new HotelTCPProxy(HOST, PORT);
            IHotelInfoService hotelInfoService = proxy;
            IHotelManagingService hotelManagingService = proxy;
            HotelApplContext context = new HotelApplContext(inOut, hotelInfoService, hotelManagingService, DATE_FORMAT);

            inOut.outputLine(".------------------------------.");
            inOut.outputLine("| Welcome to Hotel Data System |");
            inOut.outputLine("'------------------------------'");

            Menu menu = new Menu(getMainMenuItems(context), inOut);
            menu.runMenu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Item> getMainMenuItems(HotelApplContext context) {
        return List.of(
                new SubMenuItem("Guest", context.getInOut(), getGuestItems(context)),
                new SubMenuItem("Manager", context.getInOut(), getManagerItems(context)),
                new SubMenuItem("Accountant", context.getInOut(), getAccountantItems(context)),
                new SaveEndExitItem(context)
        );
    }

    private static List<Item> getAccountantItems(HotelApplContext context) {
        return List.of(
                new ShowAllStatisticsItem(context),
                new ShowBookingsForSelectedDay(context),
                new ShowMostPopularRoomTypesForRangeAgeItem(context),
                new ShowOccupancyForDateItem(context),

                new BackItem()

        );
    }

    private static List<Item> getManagerItems(HotelApplContext context) {
        return List.of(
                new AddRoomTypeItem(context),
                new AddRoomItem(context),
                new AddGuestItem(context),

                new ShowRoomTypesItem(context),
                new ShowRoomsItem(context),
                new ShowGuestsItem(context),
                new ShowBookingsItem(context),

                new RemoveRoomTypeItem(context),
                new RemoveRoomItem(context),
                new RemoveGuestItem(context),
                new RemoveBookingItem(context),

                new BackItem()
        );
    }

    private static List<Item> getGuestItems(HotelApplContext context) {
        return List.of(
                new RegisterGuestItem(context),
                new ShowRoomTypesItem(context),
                new ShowAvailableRoomsItem(context),
                new CreateBookingItem(context),
                new ShowMyBookingsItem(context),
                new CancelMyBookingItem(context),

                new BackItem()
        );
    }
}