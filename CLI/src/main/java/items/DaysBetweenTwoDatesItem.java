package items;

import cli.InputOutput;
import cli.Item;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DaysBetweenTwoDatesItem implements Item {
    private final InputOutput inOut;
    private final String format;

    public DaysBetweenTwoDatesItem(InputOutput inOut, String format) {
        this.inOut = inOut;
        this.format = format;
    }

    @Override
    public String displayName() {
        return "Calculate days between two dates";
    }

    @Override
    public void perform() {
        LocalDate firstDate = inOut.inputDate("Enter first date in format", format);
        if (firstDate == null) {
            return;
        }
        LocalDate secondDate = inOut.inputDate("Enter second date in format", format);
        if (secondDate == null) {
            return;
        }
        inOut.outputLine(Math.abs(ChronoUnit.DAYS.between(firstDate, secondDate)));
    }
}
