package items;

import cli.InputOutput;
import cli.Item;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class GetNextFriday13Item implements Item {
    private final InputOutput inOut;
    private final String format;

    public GetNextFriday13Item(InputOutput inOut, String format) {
        this.inOut = inOut;
        this.format = format;
    }

    @Override
    public String displayName() {
        return "Get next Friday 13";
    }

    @Override
    public void perform() {
        LocalDate day = inOut.inputDate("Type your date in format, and ", format);
        if (day.getDayOfWeek() == DayOfWeek.FRIDAY && day.getDayOfMonth() == 13) {
            inOut.outputLine("TODAY!!!");
        }
        LocalDate next13th = day.withDayOfMonth(13);
        if (!next13th.isAfter(day)) {
            next13th = next13th.plusMonths(1);
        }
        while(next13th.getDayOfWeek() != DayOfWeek.FRIDAY) {
            next13th = next13th.plusMonths(1);
        }
        inOut.outputLine("Next Friday the 13th from your date is: " + next13th);
    }
}
