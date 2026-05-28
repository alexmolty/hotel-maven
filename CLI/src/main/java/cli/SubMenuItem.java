package cli;

import java.util.List;

public class SubMenuItem implements Item {
    private final String name;
    private final InputOutput inOut;
    private final List<Item> items;

    public SubMenuItem(String name, InputOutput inOut, List<Item> items) {
        this.name = name;
        this.inOut = inOut;
        this.items = items;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public void perform() {
        Menu menu = new Menu(items, inOut);
        menu.runMenu();
    }
}
