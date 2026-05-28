package items;

import cli.*;

import java.util.List;

public class ItemsAppl2 {
    private static final String FORMAT = "dd.MM.yyyy";

    public static void main(String[] args) {
        InputOutput inOut = new ConsoleInputOutput();
        List<Item> items = List.of(
                new SubMenuItem("Dates", inOut,
                        List.of(
                                new DateAfterBeforeItem(inOut, FORMAT),
                                new DaysBetweenTwoDatesItem(inOut, FORMAT),
                                new DayOfWeekItem(inOut, FORMAT),
                                new GetNextFriday13Item(inOut, FORMAT),
                                new ExitItem()
                        )
                ),
                new SubMenuItem("Numbers", inOut,
                        List.of(
                                new CalculatorItem(inOut),
                                new ExitItem()
                        )
                ),
                new ExitItem()
        );
        Menu menu = new Menu(items, inOut);
        menu.runMenu();
    }
}
