package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Booking implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int counter = 1;
    private final int bookingId;
    private Guest guest;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Booking(int bookingId, Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.guest = Objects.requireNonNull(guest, "Guest cannot be null");
        this.room = Objects.requireNonNull(room, "Room cannot be null");
        this.checkIn = Objects.requireNonNull(checkIn, "Check in cannot be null");
        this.checkOut = Objects.requireNonNull(checkOut, "Check out cannot be null");
        if (bookingId <= counter) {
            throw new IllegalArgumentException("Booking id not valid");
        }
        this.bookingId = bookingId;
        synchronizedCounter(bookingId);
    }

    public static void synchronizedCounter(int id) {
        counter = Math.max(counter, id + 1);
    }

    public Booking(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.guest = Objects.requireNonNull(guest, "Guest cannot be null");
        this.room = Objects.requireNonNull(room, "Room cannot be null");
        this.checkIn = Objects.requireNonNull(checkIn, "Check in cannot be null");
        this.checkOut = Objects.requireNonNull(checkOut, "Check out cannot be null");

        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be at least one day after check-in date.");        }
        this.bookingId = counter++;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = Objects.requireNonNull(guest, "Guest cannot be null");
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = Objects.requireNonNull(room, "Room cannot be null");
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = Objects.requireNonNull(checkIn, "Check in cannot be null");
        if (!this.checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check out date must be at least one day after check in date.");
        }
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = Objects.requireNonNull(checkOut, "Check out cannot be null");
        if (checkOut.isBefore(this.checkIn)) {
            throw new IllegalArgumentException("checkOut date cannot be before checkIn");
        }
    }

    public int getBookingId() {
        return bookingId;
    }

    public long getNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double getTotalPrice() {
        return getNights() * room.getType().getPricePerNight();
    }

    public boolean isActiveOn(LocalDate date) {
        return (date.isEqual(checkIn) || date.isAfter(checkIn)) && date.isBefore(checkOut);
    }

    public boolean overlaps(LocalDate requestedCheckIn, LocalDate requestedCheckOut) {
        // Бронирование пересекается, если:
        // (Новый въезд раньше существующего выезда) И (Новый выезд позже существующего въезда)
        return requestedCheckIn.isBefore(this.checkOut) && requestedCheckOut.isAfter(this.checkIn);
    }

    @Override
    public String toString() {
        return String.format("Booking #%d | Guest: %s | Room: %d | Dates: %s to %s (%d nights) | Total: %.2f$",
                bookingId,
                guest.getName(),
                room.getRoomNumber(),
                checkIn,
                checkOut,
                getNights(),
                getTotalPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingId == booking.bookingId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookingId);
    }
}
