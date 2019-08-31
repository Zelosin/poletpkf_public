package zelosin.pack.controllers.states;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelosin.pack.view.StateTableFrame;

public class StateAnalogController {
    private static StateTableFrame mAnalogState;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAnalogNumber;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAnalogChain;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAnalogDom;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dAnalogDes;
    @FXML
    private TableColumn<StateTableFrame.AnalogParamValue, String> dAnalogValue;
    @FXML
    private TableColumn<StateTableFrame.AnalogParamValue, String> dAnalogUnits;
    @FXML
    private TableView<StateTableFrame.StateTableValue> dAnalogTable;

    public void initialize(){
        mAnalogState = new StateTableFrame(
                dAnalogNumber,
                dAnalogDom,
                dAnalogDes,
                dAnalogValue,
                dAnalogUnits,
                dAnalogChain,
                dAnalogTable
        );
    }
    public static void addElement(String pDenomination, String pDescription, String pChain, String pValue, String pUnits){
        mAnalogState.newElement(pDenomination, pDescription, pChain, pValue, pUnits);
    }
}
