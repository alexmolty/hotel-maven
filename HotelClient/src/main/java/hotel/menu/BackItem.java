package hotel.menu;

import cli.Item;

public class BackItem implements Item {
    @Override
    public String displayName() {
        return "Back";
    }

    @Override
    public void perform() {

    }

    @Override
    public boolean isExit() {
        return true;
    }
}
