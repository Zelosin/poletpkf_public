package zelosin.pack.controllers.states;

import com.sun.javafx.scene.traversal.Direction;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import zelosin.pack.view.StateTableFrame;

public class StateDiscreteController {
    private static StateTableFrame mInDiscreteState;
    private static StateTableFrame mOutDiscreteState;
    /* IN_TABLE */
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dInNumber;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dInChain;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dInDom;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dInDes;
    @FXML
    private TableView<StateTableFrame.StateTableValue> dInTable;
    /* IN_TABLE */

    /* OUT_TABLE */
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutNumber;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutChain;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutDom;
    @FXML
    private TableColumn<StateTableFrame.StateTableValue, String> dOutDes;
    @FXML
    private TableColumn<StateTableFrame.AnalogParamValue, String> dOutValue;
    @FXML
    private TableColumn<StateTableFrame.AnalogParamValue, String> dOutUnits;
    @FXML
    private TableView<StateTableFrame.StateTableValue> dOutTable;
    /* OUT_TABLE */

    public void initialize(){
        mInDiscreteState = new StateTableFrame(
                dInNumber,
                dInDom,
                dInDes,
                dInChain,
                dInTable
        );

        mOutDiscreteState = new StateTableFrame(
                dOutNumber,
                dOutDom,
                dOutDes,
                dOutValue,
                dOutUnits,
                dOutChain,
                dOutTable
        );
        dInTable.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.RIGHT))
                dInTable.impl_traverse(Direction.NEXT);
        });
        dOutTable.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.LEFT))
                dOutTable.impl_traverse(Direction.PREVIOUS);
        });
    }

    public static void addElement(String pDenomination,
                                  String pDescription,
                                  String pChain,
                                  String pExDenomination,
                                  String pExDescription,
                                  String pExChain,
                                  String pExValue,
                                  String pExUnits){
        mInDiscreteState.newElement(pDenomination, pDescription, pChain);
        mOutDiscreteState.newElement(pExDenomination, pExDescription, pExChain, pExValue, pExUnits);
    }
}
