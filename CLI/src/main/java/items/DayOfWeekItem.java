package items;

import cli.InputOutput;
import cli.Item;

import java.time.LocalDate;

public class DayOfWeekItem implements Item {
    private final InputOutput inOut;
    private final String format;

    public DayOfWeekItem(InputOutput inOut, String format) {
        this.inOut = inOut;
        this.format = format;
    }

    @Override
    public String displayName() {
        return "Which day of the week";
    }

    @Override
    public void perform() {
        LocalDate day = inOut.inputDate("Type a date in format", format);
        inOut.outputLine(day.getDayOfWeek().toString());
    }
}
