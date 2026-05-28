package hotel.menu;

import cli.Item;

public class ExitWithoutSaveItem implements Item {
    @Override
    public String displayName() {
        return "Exit without save";
    }

    @Override
    public void perform() {

    }

    @Override
    public boolean isExit() {
        return true;
    }
}
