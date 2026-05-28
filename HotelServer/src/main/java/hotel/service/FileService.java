package hotel.service;

import interfaces.IFileService;
import model.*;

import java.io.*;
import java.util.Map;
import java.util.Objects;

public class FileService implements IFileService {
    private final Hotel hotel;

    public FileService(Hotel hotel) {
        this.hotel = Objects.requireNonNull(hotel);
    }

    // SAVING

    @Override
    public void saveGuests(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            var guests = hotel.getGuests();
            out.writeObject(guests);
            System.out.println("Successfuly wrote " + guests.size() + " guests.");
        } catch (IOException e) {
            throw new RuntimeException("Error saving guests to file " + filename, e);
        }
    }

    @Override
    public void saveRoomTypes(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            var roomTypes = hotel.getRoomTypes();
            out.writeObject(roomTypes);
            System.out.println("Successfuly wrote " + roomTypes.size() + " room types.");
        } catch (IOException e) {
            throw new RuntimeException("Error saving room types to file " + filename, e);
        }
    }

    @Override
    public void saveBookings(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            var bookings = hotel.getBookings();
            out.writeObject(bookings);
            System.out.println(bookings.size() + " bookings written successfully in file: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("Error saving bookings to file " + filename, e);
        }
    }

    @Override
    public void saveRooms(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            var rooms = hotel.getRooms();
            out.writeObject(rooms);
            System.out.println(rooms.size() + " rooms written successfully in file: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("Error saving rooms to file " + filename, e);
        }
    }

    // READING

    @SuppressWarnings("unchecked")
    @Override
    public Map<Integer, Guest> readGuests(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            var guests = (Map<Integer, Guest>) in.readObject();
            System.out.println("Restored successfully " + guests.size() + " guests");
            return guests;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading guests from file " + filename, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, RoomType> readRoomTypes(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            var roomTypes = (Map<String, RoomType>) in.readObject();
            System.out.println("Restored successfully " + roomTypes.size() + " room types");
            return roomTypes;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading room types from file " + filename, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Integer, Booking> readBookings(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            var bookings = (Map<Integer, Booking>) in.readObject();
            System.out.println("Restored successfully " + bookings.size() + " bookings");
            return bookings;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading bookings from file " + filename, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Integer, Room> readRooms(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            var rooms = (Map<Integer, Room>) in.readObject();
            System.out.println("Restored successfully " + rooms.size() + " rooms");
            return rooms;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading rooms from file " + filename, e);
        }
    }
}
