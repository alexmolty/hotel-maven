package cli;

import java.util.List;

public class Menu {
    List<Item> items;
    InputOutput inOut;

    public Menu(List<Item>  items, InputOutput inOut) {
        this.items = items;
        this.inOut = inOut;
    }

    public void runMenu() {
        if (items == null || items.isEmpty()) {
            return;
        }
        while (true) {
            for (int i = 0; i < items.size(); i++) {
                inOut.outputLine((i + 1) + ". " + items.get(i).displayName());
            }
            Integer selected = inOut.inputInteger("Please enter item number", 1, items.size());
            if (selected == null)
                return;
            Item selectedItem = items.get(selected - 1);
            try {
                selectedItem.perform();
            } catch (Exception e) {
                inOut.outputLine("Error: " + e.getMessage());
            }
            if (selectedItem.isExit()) {
                return;
            }
        }
    }
}
