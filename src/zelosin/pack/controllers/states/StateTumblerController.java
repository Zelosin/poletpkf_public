package zelosin.pack.controllers.states;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelosin.pack.view.StateTableFrame;

public class StateTumblerController {

    private static StateTableFrame mTumblerState;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dTumblerNumber;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dTumblerChain;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dTumblerDom;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dTumblerDes;
    @FXML
    private TableView<StateTableFrame.StateTableValue> dTumblerTable;

    public void initialize(){
        mTumblerState = new StateTableFrame(
                dTumblerNumber,
                dTumblerDom,
                dTumblerDes,
                dTumblerChain,
                dTumblerTable
        );
    }
    public static void addElement(String pDenomination, String pDescription, String pChain){
        mTumblerState.newElement(pDenomination, pDescription, pChain);
    }
}

