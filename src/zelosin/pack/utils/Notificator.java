package zelosin.pack.utils;

import javafx.scene.control.Label;
import java.util.*;

public class Notificator {
    private Label mNotificationLabel;
    private Map<Integer, Integer> mNotificationCollection = new LinkedHashMap<>();
    private Integer mTempInt;
    public Notificator(Label notificationLabel) {
        mNotificationLabel = notificationLabel;
    }

    public Integer getKey(Map<Integer, Integer> map, int value) {
        Integer val = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(value))
                val = entry.getKey();
        }
        return val;
    }

    protected void checkContainsAndPut(int messCode, int alarmLevel){
        if(mNotificationCollection.containsKey(messCode))
            mNotificationCollection.remove(messCode);
        mNotificationCollection.put(messCode, alarmLevel);
    }

    public void addNewNotification(int messCode){
        checkContainsAndPut(messCode, GlobalData.mMessageLevel.get(messCode));
    }

    protected boolean findNotification(int notLevel, boolean forDisplay){
        mTempInt = getKey(mNotificationCollection, notLevel);
        if(mTempInt!= null) {
            if(forDisplay) {
                if(GlobalData.mMessageCodes.get(mTempInt) == null)
                    mNotificationLabel.setText("Неизвестный код сообщения");
                else
                    mNotificationLabel.setText(GlobalData.mMessageCodes.get(mTempInt));
                mNotificationLabel.setStyle(
                        "-fx-background-color: " + GlobalData.mColorLevel.get(notLevel) +";");
            }
            else{
                mNotificationLabel.setStyle("-fx-background-color: #1b1b1b");
                mNotificationLabel.setText("");
                mNotificationCollection.remove(mTempInt);
                processLastNotification(true);
            }
            return true;
        }
        return false;
    }

    public void processLastNotification(boolean forDisplay){
        for(int i = 5; i!=0 ; i--){
            if(findNotification(i, forDisplay))
                break;
        }
    }
}
