package hotel.resources;

import cli.InputOutput;
import interfaces.IHotelInfoService;
import interfaces.IHotelManagingService;

public class HotelApplContext {
    private final InputOutput inOut;
    private final IHotelInfoService hotelInfoService;
    private final IHotelManagingService hotelManagingService;
    private final String dateFormat;

    public HotelApplContext(InputOutput inOut, IHotelInfoService hotelInfoService, IHotelManagingService hotelManagingService, String dateFormat) {
        this.inOut = inOut;
        this.hotelInfoService = hotelInfoService;
        this.hotelManagingService = hotelManagingService;
        this.dateFormat = dateFormat;
    }

    public InputOutput getInOut() {
        return inOut;
    }

    public IHotelInfoService getHotelInfoService() {
        return hotelInfoService;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public IHotelManagingService getHotelManagingService() {
        return hotelManagingService;
    }
}
