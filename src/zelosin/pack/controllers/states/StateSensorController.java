package zelosin.pack.controllers.states;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelosin.pack.view.StateTableFrame;

public class StateSensorController {

    private static StateTableFrame mSensorState;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dSensorNumber;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dSensorChain;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dSensorDom;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dSensorDes;
    @FXML
    private TableView<StateTableFrame.StateTableValue> dSensorTable;
    public void initialize(){
        mSensorState = new StateTableFrame(
                dSensorNumber,
                dSensorDom,
                dSensorDes,
                dSensorChain,
                dSensorTable
        );
    }
    public static void addElement(String pDenomination, String pDescription, String pChain){
        mSensorState.newElement(pDenomination, pDescription, pChain);
    }
}
