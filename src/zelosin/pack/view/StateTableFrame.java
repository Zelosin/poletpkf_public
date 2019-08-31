package zelosin.pack.view;

import com.sun.istack.internal.Nullable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import zelosin.pack.utils.GlobalData;

public class StateTableFrame{
    public class StateTableValue{
        private String denomination;
        private String description;
        private String chain_state;
        private String count;
        private String check = "*";

        public StateTableValue(String denomination, String description, String chain_state) {
            this.denomination = denomination;
            this.description = description;
            this.chain_state = chain_state;
            if(mCountOfNode < 999)
                this.count = String.valueOf(++mCountOfNode);
        }
        public void setCheck(String check) {
            this.check = check;
        }
        public String getDenomination() {
            return denomination;
        }
        public String getDescription() {
            return description;
        }
        public String getChain_state() {
            return chain_state;
        }
        public String getCount() {
            return String.valueOf(mCountOfNode - Integer.valueOf(count)+1);
        }
        public String getCheck() {
            return check;
        }
    }

    private int mCountOfNode = 0;
    private int mUnreadNotification;
    private boolean isFromTop = false;
    private ObservableList<StateTableValue> mStateValueCollection = FXCollections.observableArrayList();

    public class AnalogParamValue extends StateTableValue{
        private String value;
        private String units;
        public AnalogParamValue(String denomination, String description, String chain_state, String value, String units) {
            super(denomination, description, chain_state);
            this.value = value;
            this.units = units;
        }

        public String getValue() {
            return value;
        }
        public String getUnits() {
            return units;
        }

    }

    @Nullable
    private TableColumn<StateTableValue, String> dNumberCol;
    private TableColumn<StateTableValue, String> dChainCol;
    private TableColumn<StateTableValue, String> dDomCol;
    private TableColumn<StateTableValue, String> dDesCol;
    private TableColumn<AnalogParamValue, String> dValueCol;
    private TableColumn<AnalogParamValue, String> dUnitsCol;
    private TableView<StateTableValue> dFrameTable;
    private Label mNotificationCounter;

    public StateTableFrame(TableColumn<StateTableValue, String> dNumberCol,
                           TableColumn<StateTableValue, String> dDomCol,
                           TableColumn<StateTableValue, String> dDesCol,
                           TableColumn<StateTableValue, String> dChainCol,
                           TableView<StateTableValue> dFrameTable) {
        this.dNumberCol = dNumberCol;
        this.dChainCol = dChainCol;
        this.dDomCol = dDomCol;
        this.dDesCol = dDesCol;
        this.dFrameTable = dFrameTable;
        bindValue();
    }

    public StateTableFrame(TableColumn<StateTableValue, String> dNumberCol,
                           TableColumn<StateTableValue, String> dDomCol,
                           TableColumn<StateTableValue, String> dDesCol,
                           TableColumn<AnalogParamValue, String> dValueCol,
                           TableColumn<AnalogParamValue, String> dUnitsCol,
                           TableColumn<StateTableValue, String> dChainCol,
                           TableView<StateTableValue> dFrameTable) {
        this.dNumberCol = dNumberCol;
        this.dChainCol = dChainCol;
        this.dDomCol = dDomCol;
        this.dDesCol = dDesCol;
        this.dValueCol = dValueCol;
        this.dUnitsCol = dUnitsCol;
        this.dFrameTable = dFrameTable;
        bindValue();
    }

    protected void bindValue(){
        dDomCol.setCellValueFactory(new PropertyValueFactory<StateTableValue, String>("denomination"));
        dDesCol.setCellValueFactory(new PropertyValueFactory<StateTableValue, String>("description"));
        dChainCol.setCellValueFactory(new PropertyValueFactory<StateTableValue, String>("chain_state"));
        if(dNumberCol!=null)
            dNumberCol.setCellValueFactory(new PropertyValueFactory<StateTableValue, String>("count"));
        if(dUnitsCol != null){
            dValueCol.setCellValueFactory(new PropertyValueFactory<AnalogParamValue, String>("value"));
            dUnitsCol.setCellValueFactory(new PropertyValueFactory<AnalogParamValue, String>("units"));
        }
        dFrameTable.setItems(mStateValueCollection);
    }
    public void newElement(String pDenomination, String pDescription, String pChain){
        if(isFromTop)
            mStateValueCollection.add( new StateTableValue(pDenomination, pDescription, pChain));
        else
            mStateValueCollection.add(0, new StateTableValue(pDenomination, pDescription, pChain));
        mUnreadNotification++;
        if(mNotificationCounter!=null) {
            if(mStateValueCollection.size() > GlobalData.MAXNOTIFICATION){
                if(isFromTop){
                    if (mStateValueCollection.get(0).getCheck().equals("*"))
                        mUnreadNotification--;
                    mStateValueCollection.remove(0);
                }
                else {
                    if (mStateValueCollection.get(999).getCheck().equals("*"))
                        mUnreadNotification--;
                    mStateValueCollection.remove(GlobalData.MAXNOTIFICATION);
                }
            }
            mNotificationCounter.setText(mUnreadNotification + "/" + mCountOfNode);
        }
    }
    public void newElement(String pDenomination, String pDescription, String pChain, String pValue, String pUnits){
        mStateValueCollection.add(0, new AnalogParamValue(pDenomination, pDescription, pChain, pValue, pUnits));
    }
    public void allowCheck(Label pNotificationCounter){
        mNotificationCounter = pNotificationCounter;
        dDesCol.setCellValueFactory(new PropertyValueFactory<StateTableValue, String>("check"));
        mUnreadNotification = mCountOfNode;
        dFrameTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                dFrameTable.getSelectionModel().selectFirst();
            }
        });
        dFrameTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StateTableValue>() {
            @Override
            public void changed(ObservableValue<? extends StateTableValue> observable, StateTableValue oldValue, StateTableValue newValue) {
                if(newValue.getCheck().equals("*")) {
                    newValue.setCheck("");
                    mUnreadNotification--;
                    mNotificationCounter.setText(mUnreadNotification+"/"+mCountOfNode);
                    dFrameTable.refresh();
                }
            }
        });
    }

    public void setFromTop(boolean fromTop) {
        isFromTop = fromTop;
    }
}



















