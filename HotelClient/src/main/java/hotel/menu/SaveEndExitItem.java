package hotel.menu;

import hotel.items.HotelItem;
import hotel.proxy.HotelTCPProxy;
import hotel.resources.HotelApplContext;

import java.io.IOException;

public class SaveEndExitItem extends HotelItem {

    public SaveEndExitItem(HotelApplContext context) {
        super(context);
    }

    @Override
    public String displayName() {
        return "Save, close connection end exit";
    }

    @Override
    public void perform() {
        try {
            ((HotelTCPProxy)managingService).saveData();
            inOut.outputLine("Data saved successfully");
        } catch (Exception e) {
            inOut.outputLine("Failed to save data" + e.getMessage());
        } finally {
            try {
                ((HotelTCPProxy)managingService).close();
                inOut.outputLine("Connection closed");
            } catch (IOException e) {
                inOut.outputLine("Failed to close connection" + e.getMessage());
            }
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
