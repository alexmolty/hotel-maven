package hotel.service;

import interfaces.IHotelInfoService;
import model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HotelInfoService implements IHotelInfoService {
    private final Hotel hotel;

    public HotelInfoService(Hotel hotel) {
        this.hotel = Objects.requireNonNull(hotel);
    }

    @Override
    public int getNumberOfBookings() {
        if (hotel.getBookings().isEmpty()) {
            return 0;
        }
        return hotel.getBookings().size();
    }

    @Override
    public double getTotalIncome() {
        if (hotel.getBookings().isEmpty()) {
            return 0;
        }
        return hotel.getBookings().values().stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();
    }

    @Override
    public double getAverageBookingPrice() {
        if (hotel.getBookings().isEmpty()) {
            return 0;
        }
        int count = getNumberOfBookings();
        return count == 0 ? 0 : getTotalIncome() / count;
    }

    @Override
    public String getMostPopularRoomTypes() {
        if (hotel.getBookings().isEmpty()) {
            return "No types found";
        }
        Map<String, Long> typeCounts = hotel.getBookings().values().stream()
                .collect(
                        Collectors.groupingBy(
                                b -> b.getRoom().getType().getRoomTypeName(),
                                Collectors.counting()
                        )
                );
        long maxCountOfBookings = typeCounts.values().stream().max(Long::compare).orElse(0L);

        return typeCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCountOfBookings)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    @Override
    public List<Room> getAvailableRoomsForDate(LocalDate date) {
        Objects.requireNonNull(date, "Date cannot be null");
        if (hotel.getRooms().isEmpty()) {
            return List.of();
        }
        return hotel.getRooms().values().stream()
                .filter(room -> isRoomAvailableForDate(room, date))
                .toList();
    }

    @Override
    public List<Room> getAvailableRoomsForDates(LocalDate checkInDate, LocalDate checkOutDate) {
        Objects.requireNonNull(checkInDate);
        Objects.requireNonNull(checkOutDate);
        if (!checkInDate.isBefore(checkOutDate)) {
            throw new IllegalArgumentException("Check out date must be after check in date ");
        }
        if (hotel.getRooms().isEmpty()) {
            return List.of();
        }
        return hotel.getRooms().values().stream()
                .filter(room -> isRoomAvailableForDates(room, checkInDate, checkOutDate))
                .toList();
    }

    @Override
    public List<Room> getOccupiedRoomsForDate(LocalDate date) {
        Objects.requireNonNull(date, "Date cannot be null");
        if (hotel.getRooms().isEmpty()) {
            return List.of();
        }
        return hotel.getRooms().values().stream()
                .filter(room -> !isRoomAvailableForDate(room, date))
                .toList();
    }

    @Override
    public String getHotelName() {
        return hotel.getHotelName();
    }

    @Override
    public Room findRoomByNumber(int roomId) {
        return hotel.getRooms().get(roomId);
    }

    @Override
    public Guest findGuestById(int guestId) {
        return hotel.getGuests().get(guestId);
    }

    @Override
    public RoomType findRoomTypeByName(String roomTypeName) {
        if (roomTypeName == null || roomTypeName.isBlank()) {
            throw new IllegalArgumentException("Room type name cannot be empty");
        }
        roomTypeName = hotel.normalizeRoomTypeName(roomTypeName);
        return hotel.getRoomTypes().get(roomTypeName);
    }

    @Override
    public Booking findBookingById(int bookingId) {
        return hotel.getBookings().get(bookingId);
    }

    @Override
    public Map<String, RoomType> getRoomTypes() {
        return Collections.unmodifiableMap(hotel.getRoomTypes());
    }

    @Override
    public Map<Integer, Room> getRooms() {
        return Collections.unmodifiableMap(hotel.getRooms());
    }

    @Override
    public Map<Integer, Booking> getBookings() {
        return Collections.unmodifiableMap(hotel.getBookings());
    }

    @Override
    public Map<Integer, Guest> getGuests() {
        return Collections.unmodifiableMap(hotel.getGuests());
    }

    @Override
    public boolean isRoomAvailableForDate(Room room, LocalDate date) {
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(date, "Date cannot be null");
        return hotel.getBookings().values().stream()
                .noneMatch(booking -> booking.getRoom().equals(room) && booking.isActiveOn(date));
    }

    @Override
    public boolean isRoomAvailableForDates(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(checkInDate, "Date from cannot be null");
        Objects.requireNonNull(checkOutDate, "Date to cannot be null");
        return hotel.getBookings().values().stream()
                .filter(booking -> booking.getRoom().equals(room))
                .noneMatch(booking -> booking.overlaps(checkInDate, checkOutDate));
    }

    @Override
    public List<String> getMostPopularRoomTypesForAgeRange(Map<Integer, Booking> bookings, int minAge, int maxAge) {
        Objects.requireNonNull(bookings, "Bookings cannot be null");
        if (minAge > maxAge) {
            throw new IllegalArgumentException("Min age cannot be greater than max age");
        }
        if (minAge < 0) {
            throw new IllegalArgumentException("Min age and max age cannot be negative");
        }

        Map<String, Long> counts = bookings.values().stream()
                .filter(
                        b -> {
                            int age = b.getGuest().getAge();
                            return age >= minAge && age <= maxAge;
                        }
                )
                .collect(Collectors.groupingBy(
                                b -> b.getRoom().getType().getRoomTypeName(),
                                Collectors.counting()
                        )
                );
        if (counts.isEmpty()) {
            return List.of();
        }
        long max = Collections.max(counts.values());
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public List<String> getMostPopularRoomTypesForAgeRange(int minAge, int maxAge) {
        if (minAge > maxAge) {
            throw new IllegalArgumentException("Min age cannot be greater than max age");
        }
        return getMostPopularRoomTypesForAgeRange(hotel.getBookings(), minAge, maxAge);
    }

    @Override
    public List<Booking> getBookingsStartOn(LocalDate checkInDate) {
        Objects.requireNonNull(checkInDate, "checkInDate cannot be null");
        List<Booking> list = hotel.getBookingsCheckInDate().get(checkInDate);
        return list == null ? new ArrayList<>() : list;
    }

    @Override
    public List<Booking> getBookingsByGuestsId(int guestId) {
        return hotel.getBookings().values().stream()
                .filter(booking -> booking.getGuest().getId() == guestId)
                .toList();
    }
}
