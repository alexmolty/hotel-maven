package hotel.app.resources;

import model.Hotel;
import hotel.service.FileService;


public class HotelDataSaver {
    private final Hotel hotel;
    private final FileService fileService;

    public HotelDataSaver(Hotel hotel, FileService fileService) {
        this.hotel = hotel;
        this.fileService = fileService;
    }

    public void saveAll() {
        DataManager.saveAllData(hotel, fileService);
    }
}
