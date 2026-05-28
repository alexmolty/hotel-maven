package hotel;

import hotel.app.resources.*;
import hotel.generator.RandomHotelDataGenerator;
import hotel.protocol.HotelTCPProtocol;
import hotel.service.*;
import model.Hotel;
import server.ServerJava;

import java.nio.file.Files;
import java.nio.file.Path;

public class HotelApplServer {
    private static final int PORT = 8888;

    public static void main(String[] args) {
        System.out.println(">>> Initializing Hotel Server...");

        try {
            Hotel hotel = new Hotel("Pacific Resort");
            HotelManagingService manager = new HotelManagingService(hotel);
            HotelInfoService infoService = new HotelInfoService(hotel);
            FileService fileService = new FileService(hotel);

            boolean allFilesExist = Files.exists(Path.of(DataManager.ROOMS_FILE)) &&
                    Files.exists(Path.of(DataManager.ROOM_TYPES_FILE)) &&
                    Files.exists(Path.of(DataManager.GUESTS_FILE)) &&
                    Files.exists(Path.of(DataManager.BOOKINGS_FILE));

            if (allFilesExist) {
                System.out.println(">>> Loading database from files...");
                DataManager.loadAllData(hotel, fileService);
            } else {
                System.out.println(">>> Missing database files. Generating random mock data...");
                new RandomHotelDataGenerator(hotel, manager);
                DataManager.saveAllData(hotel, fileService);
            }

            HotelDataSaver dataSaver = new HotelDataSaver(hotel, fileService);

            HotelTCPProtocol protocol = new HotelTCPProtocol(manager, infoService, dataSaver);
            ServerJava server = new ServerJava(protocol, PORT);

            System.out.println(">>> Server successfully started on port " + PORT + ". Waiting for clients...");
            server.run();

        } catch (Exception e) {
            System.err.println(">>> Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}