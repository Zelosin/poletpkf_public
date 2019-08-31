package zelosin.pack.controllers.frames;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelosin.pack.view.StateTableFrame;

public class WarningFrameController {

    private static StateTableFrame mWarningState;
    @FXML
    private TableView dWarningTableView;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dWarningTableIndex;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dWarningTableText;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dWarningTableTime;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dWarningTableCode;
    @FXML
    private Label dNotificationCounter;

    public void initialize(){
        mWarningState = new StateTableFrame(
                null,
                dWarningTableTime,
                dWarningTableCode,
                dWarningTableText,
                dWarningTableView
        );
        mWarningState.allowCheck(dNotificationCounter);
        mWarningState.setFromTop(true);
    }
    public static void addElement(String pTime, String pCode, String pText){
        mWarningState.newElement(pTime, pCode, pText);
    }
}
