package items;

import cli.InputOutput;
import cli.Item;

import java.time.LocalDate;
import java.util.List;

public class DateAfterBeforeItem implements Item {
    private final InputOutput inOut;
    private final String format;
    private final List<String> options = List.of("after", "before");
    public DateAfterBeforeItem(InputOutput inOut, String format) {
        this.inOut = inOut;
        this.format = format;
    }
    @Override
    public String displayName() {
        return "Calculate date after or before";
    }

    @Override
    public void perform() {
        LocalDate initialDate = inOut.inputDate("Enter initial date in format", format);
        if (initialDate == null) {
            return;
        }
        Long days = inOut.inputLong("Enter number of days");
        if(days == null) {
            return;
        }
        String options = inOut.inputString("Enter options", this.options);
        if (options == null) {
            return;
        }
        inOut.outputLine(options.equals("after") ? initialDate.plusDays(days) : initialDate.minusDays(days));
    }
}
