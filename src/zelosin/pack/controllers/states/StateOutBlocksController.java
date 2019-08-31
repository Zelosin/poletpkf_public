package zelosin.pack.controllers.states;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import zelosin.pack.view.StateTableFrame;

public class StateOutBlocksController {

    private static StateTableFrame mOutBlocksState;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutBlocksNumber;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutBlocksChain;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutBlocksDom;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutBlocksDes;
    @FXML
    private TableView<StateTableFrame.StateTableValue> dOutBlocksTable;

    public void initialize(){
        mOutBlocksState = new StateTableFrame(
                dOutBlocksNumber,
                dOutBlocksDom,
                dOutBlocksDes,
                dOutBlocksChain,
                dOutBlocksTable
        );
    }
    public static void addElement(String pDenomination, String pDescription, String pChain){
        mOutBlocksState.newElement(pDenomination, pDescription, pChain);
    }
}

