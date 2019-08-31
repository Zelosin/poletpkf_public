package zelosin.pack.controllers.frames;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelosin.pack.view.StateTableFrame;
import javafx.scene.control.Label;


public class AlarmFrameController {

    private static StateTableFrame mAlarmState;
    @FXML
    private TableView dAlarmTableView;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAlarmTableIndex;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAlarmTableText;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAlarmTableTime;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAlarmTableCode;
    @FXML
    private Label dNotificationCounter;

    public void initialize(){
        mAlarmState = new StateTableFrame(
                null,
                dAlarmTableTime,
                dAlarmTableCode,
                dAlarmTableText,
                dAlarmTableView
        );
        mAlarmState.allowCheck(dNotificationCounter);
        mAlarmState.setFromTop(true);
    }
    public static void addElement(String pTime, String pCode, String pText){
        mAlarmState.newElement(pTime, pCode, pText);
    }
}
