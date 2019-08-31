package zelosin.pack.controllers.frames;

import com.jfoenix.controls.JFXButton;
import com.sun.javafx.scene.traversal.Direction;
import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import zelosin.pack.utils.FileProcessing;
import zelosin.pack.utils.GlobalData;
import zelosin.pack.view.USBView;

public class UsbFrameController {
    @FXML
    private ListView<String> dFoldersList;
    @FXML
    private JFXButton dCopyButton;
    @FXML
    private ProgressBar dCopyProgressBar;
    @FXML
    private Label dFileSizeLabel;
    @FXML
    private Label dMessageLabel;
    @FXML
    private Label dNotConLabel;
    @FXML
    private Label dFolderEmptyLabel;
    @FXML
    private ListView<String> dUSBList;

    int mTemp;

    private WinDef.HWND hWnd;

    public void initialize() {
        fillListWithFolders();
        FileProcessing.updateUSBConnection();
        dFileSizeLabel.setText("Объём файлов: 0 Кб");
        dFoldersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        updateUSBList();
        USBView.fillUSBView(dUSBList);
        initializeListeners();
    }

    public interface MyUser32 extends User32 {

        public static final MyUser32 MYINSTANCE = (MyUser32) Native.loadLibrary("user32", MyUser32.class, W32APIOptions.UNICODE_OPTIONS);

        interface WNDPROC extends StdCallCallback {
            LRESULT callback(HWND hWnd, int uMsg, WPARAM uParam, LPARAM lParam);
        }

        LRESULT CallWindowProc(LONG_PTR proc, HWND hWnd, int uMsg, WPARAM uParam, WinDef.LPARAM lParam) throws LastErrorException;

        LONG_PTR SetWindowLongPtr(HWND hWnd, int nIndex, WNDPROC wndProc) throws LastErrorException;
    }

    private BaseTSD.LONG_PTR baseWndProc;

    public interface SetupApi extends StdCallLibrary {

        public static SetupApi INSTANCE = Native.load("setupapi", SetupApi.class, W32APIOptions.DEFAULT_OPTIONS);

        int CM_Locate_DevNode(IntByReference pdnDevInst,
                              String pDeviceID,
                              int ulFlags);

        int CM_Request_Device_Eject(IntByReference dnDevInst, int pVetoType, StringBuilder pszVetoName, int ulNameLength, int ulFlags);
    }

    public MyUser32.WNDPROC listener = new MyUser32.WNDPROC () {
        public WinDef.LRESULT callback(WinDef.HWND hWnd, int uMsg, WinDef.WPARAM uParam,
                                       WinDef.LPARAM lParam) {
            switch(uMsg) {
                case(537): {
                    FileProcessing.updateUSBConnection();
                    updateUSBList();
                    break;
                }
            }
            try {
                return MyUser32.MYINSTANCE.CallWindowProc(baseWndProc, hWnd, uMsg, uParam, lParam);
            }
            catch (LastErrorException l){
                return new WinDef.LRESULT(1);
            }
        }
    };

    public void sethWnd(WinDef.HWND hWnd) {
        this.hWnd = hWnd;
        this.baseWndProc = MyUser32.MYINSTANCE.SetWindowLongPtr(hWnd, MyUser32.GWL_WNDPROC, listener);
    }

    public void fillListWithFolders(){
        dFoldersList.getItems().clear();
        dFoldersList.getItems().addAll(FileProcessing.loadFileFromDir(GlobalData.getmBaseDir()));
        FileProcessing.setProgressBar(dCopyProgressBar);
        dCopyButton.setDisable(true);
        if(dFoldersList.getItems().size() == 0)
            dFolderEmptyLabel.setVisible(true);
        else
            dFolderEmptyLabel.setVisible(false);
    }

    public void updateUSBList(){
        dUSBList.getItems().clear();
        dUSBList.getSelectionModel().clearSelection();
        dUSBList.getItems().addAll(FileProcessing.getmUSBFlashDrivers());
        dUSBList.getFocusModel().focus(0);
        if(dUSBList.getItems().size() == 0)
            dNotConLabel.setVisible(true);
        else
            dNotConLabel.setVisible(false);
    }
    private void initializeListeners(){
        dFoldersList.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int mFocusedElement = dFoldersList.getFocusModel().getFocusedIndex();
            if(event.getCode().equals(KeyCode.DOWN) && mFocusedElement != dFoldersList.getItems().size()) {
                if(mFocusedElement+2> dFoldersList.getItems().size()){
                    dFoldersList.getFocusModel().focus(0);
                    dFoldersList.scrollTo(0);
                }
                else {
                    dFoldersList.getFocusModel().focus(mFocusedElement + 1);
                    dFoldersList.scrollTo(mFocusedElement - 1);
                }
                event.consume();
            }
            if(event.getCode().equals(KeyCode.UP) &&mFocusedElement != 0) {
                dFoldersList.scrollTo(mFocusedElement-2);
                dFoldersList.getFocusModel().focus(mFocusedElement - 1);
                event.consume();
            }
            if(event.getCode().equals(KeyCode.ENTER)){
                if(dFoldersList.getSelectionModel().isSelected(mFocusedElement)) {
                    dFoldersList.getSelectionModel().clearSelection(mFocusedElement);
                    if(dFoldersList.getSelectionModel().getSelectedItems().size()==0) {
                        dFoldersList.getFocusModel().focus(mFocusedElement);
                    }
                    event.consume();
                }
            }
            if(event.getCode().equals(KeyCode.RIGHT) & !dCopyButton.isDisable())
                dFoldersList.impl_traverse(Direction.NEXT);
            if(event.getCode().equals(KeyCode.LEFT))
                dFoldersList.impl_traverse(Direction.PREVIOUS);
        });
        dUSBList.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int mFocusedElement = dUSBList.getFocusModel().getFocusedIndex();
            if(event.getCode().equals(KeyCode.DOWN) && mFocusedElement != dUSBList.getItems().size()) {
                if(mFocusedElement+2> dUSBList.getItems().size()){
                    dUSBList.getFocusModel().focus(0);
                }
                else{
                    dUSBList.getFocusModel().focus(mFocusedElement + 1);
                }
                event.consume();
            }
            if(event.getCode().equals(KeyCode.UP) &&mFocusedElement != 0) {
                dUSBList.getFocusModel().focus(mFocusedElement - 1);
                event.consume();
            }
            if(event.getCode().equals(KeyCode.ENTER)) {
                mTemp = dUSBList.getFocusModel().getFocusedIndex();
                if(dUSBList.getSelectionModel().isSelected(dUSBList.getFocusModel().getFocusedIndex())) {
                    dUSBList.getSelectionModel().clearSelection();
                    dUSBList.getFocusModel().focus(mTemp);
                    event.consume();
                }
                else {
                    dUSBList.getSelectionModel().clearSelection();
                    dUSBList.getSelectionModel().select(mTemp);
                    event.consume();
                }
            }
            dUSBList.scrollTo(dUSBList.getFocusModel().getFocusedIndex());
            if(event.getCode().equals(KeyCode.RIGHT))
                dUSBList.impl_traverse(Direction.NEXT);
            event.consume();
        });
        dFoldersList.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                FileProcessing.setmFoldersObservableList(dFoldersList.getSelectionModel().getSelectedItems());
                long tempSize = FileProcessing.processingSize();
                if(tempSize >= 1024)
                    dFileSizeLabel.setText("Объём файлов: "+Long.toString(FileProcessing.processingSize()/1024) + " Мб");
                else
                    dFileSizeLabel.setText("Объём файлов: "+Long.toString(FileProcessing.processingSize())+" Кб");
            }
        });
        dUSBList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String parsedString = dUSBList.getSelectionModel().getSelectedItem();
                if(parsedString == null)
                    dCopyButton.setDisable(true);
                else{
                    dCopyButton.setDisable(false);
                    parsedString = parsedString.charAt(parsedString.length()-3) + ":\\";
                    FileProcessing.setmDirDestination(parsedString);
                }
            }
        });
        dCopyButton.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER))
                    FileProcessing.copyProcessing();
                if(event.getCode().equals(KeyCode.LEFT))
                    dCopyButton.impl_traverse(Direction.PREVIOUS);
                event.consume();
            }
        });
    }

    public void updateMessage(String pText){
        dMessageLabel.setText(pText);
    }
}
























