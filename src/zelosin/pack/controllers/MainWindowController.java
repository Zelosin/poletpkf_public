package zelosin.pack.controllers;

import com.jfoenix.controls.JFXTabPane;
import com.sun.javafx.scene.traversal.Direction;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import zelosin.pack.controllers.frames.AlarmFrameController;
import zelosin.pack.controllers.frames.DataSetterController;
import zelosin.pack.controllers.frames.PasswordController;
import zelosin.pack.controllers.frames.WarningFrameController;
import zelosin.pack.controllers.states.*;
import zelosin.pack.utils.GlobalData;
import zelosin.pack.utils.Notificator;
import zelosin.pack.utils.Updater;

import java.io.IOException;
import java.util.EventListener;
import java.util.TimerTask;

public class MainWindowController {

    @FXML
    private Label dTabTitle;
    @FXML
    private JFXTabPane dMainTabPane;
    @FXML
    private VBox dLeftBox;
    @FXML
    private HBox dSubTitleBox;
    @FXML
    private HBox dImageBox;
    @FXML
    private Label dTimeLabel;
    @FXML
    private Label dDateLabel;
    @FXML
    private Label dNotificationLabel;

    private Notificator mNotificator;
    private Tab mTempTab;
    private static VBox mViewHolder;
    private String mCurrentTab = "";
    private String mRunnableNotification;

    private TimerTask mTimerTask;
    private TimerTask mUpdateTask;

    protected void allowRunLabel(){
        if(dNotificationLabel.getText().length() > 90)
            mRunnableNotification = dNotificationLabel.getText();
        else
            mRunnableNotification = null;
    }

    public void initialize(){
        generateTabs();
        initializeListeners();
        fillTabs();

        dLeftBox.getChildren().add(GlobalData.mLeftBarLoader.getRoot());
        dSubTitleBox.getChildren().add(GlobalData.mSubTitleBarLoader.getRoot());
        dDateLabel.setText((GlobalData.mDatePattern.format(GlobalData.mCurDate)));
        dMainTabPane.setFocusTraversable(false);
        mNotificator = new Notificator(dNotificationLabel);

        createNotification(22, false);
        createNotification(1, true);
        createNotification(2, false);
        createNotification(87, false);
        createNotification(55, false);
        createNotification(22, true);
        createNotification(33, false);
        createNotification(11, true);

        StateTumblerController.addElement("TB1", "ВКЛЮЧЕНИЕ ТД1", "411A");
        StateSensorController.addElement("A27_9", "РЕЗЕРВ", "7С:28");
        StateAnalogController.addElement("Im1", "Ток тягового двигателя", "19C:1", "0.000", "А");
        StateDiscreteController.addElement("КП1","Включение поездного контактора","2C:22",
                "КП1","Контакторы поездные","2C:2","","");
        StateDiscreteController.addElement("КП1","Включение поездного контактора","2C:22",
                "КП1","Контакторы поездные","2C:2","","");
        StateOutBlocksController.addElement("АКВТ","Превышение температуры компрессора","361А1 | К15-301");


        allowRunLabel();
        appearImage(4);
        dMainTabPane.getSelectionModel().select(1);
        initializeTasks();
    }

    private Tab generateTab(String title, String id){
        mTempTab = new Tab(title);
        mTempTab.setId(id);
        return mTempTab;
    }
    private void generateTabs(){
        dMainTabPane.getTabs().addAll(
                new Tab("ОСНОВНОЙ КАДР"),
                generateTab("ДОПОЛНИТЕЛЬНЫЙ КАДР", GlobalData.GeneratedId.SECONDTABID),
                new Tab("СХЕМА ПОЕЗДА"),
                new Tab("ТРЕВОЖНЫЕ и ИНФОРМАЦИОННЫЕ СООБЩЕНИЯ"),
                new Tab("МЕНЮ ДИАГНОСТИКИ"),
                new Tab("ВЫБОР РЕЖИМА СОСТОЯНИЯ ТЕПЛОВОЗА"),
                new Tab("ДИАГНОСТИКА"),
                new Tab("НАСТРОЙКА ДАТЫ И ВРЕМЕНИ"),
                new Tab("КОПИРОВАНИЕ ФАЙЛОВ РЕГИСТРАЦИИ"),
                new Tab("ВЕРСИИ ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ"),
                new Tab("СОСТОЯНИЕ ВСЕХ ДИСКРЕТНЫХ ПАРАМЕТРОВ"),
                new Tab("АНАЛОГОВЫЕ ПАРАМЕТРЫ"),
                new Tab("СОСТОЯНИЕ ТУМБЛЕРОВ"),
                new Tab("СОСТОЯНИЕ ДАТЧИКОВ"),
                new Tab("СОСТОЯНИЕ ВНЕШНИХ БЛОКОВ"),
                new Tab("ПРОВЕРКА ЭЛЕКТРИЧЕСКОЙ СХЕМЫ")
                );
    }
    private void fillTabs(){
        dMainTabPane.getTabs().get(0).setContent(GlobalData.mMenuLoader.getRoot());
        dMainTabPane.getTabs().get(1).setContent(GlobalData.mMainFrameLoader.getRoot());
        dMainTabPane.getTabs().get(2).setContent(GlobalData.mExFrameLoader.getRoot());
        dMainTabPane.getTabs().get(3).setContent(GlobalData.mSchemeLoader.getRoot());
        dMainTabPane.getTabs().get(4).setContent(GlobalData.mAlarmLoader.getRoot());
        dMainTabPane.getTabs().get(5).setContent(GlobalData.mExMenuLoader.getRoot());
        dMainTabPane.getTabs().get(6).setContent(GlobalData.mPasswordLoader.getRoot());
        dMainTabPane.getTabs().get(7).setContent(GlobalData.mDataSetterLoader.getRoot());
        dMainTabPane.getTabs().get(8).setContent(GlobalData.mUsbLoader.getRoot());
        dMainTabPane.getTabs().get(9).setContent(GlobalData.mProgramVersionLoader.getRoot());
        dMainTabPane.getTabs().get(10).setContent(GlobalData.mStateDiscreteLoader.getRoot());
        dMainTabPane.getTabs().get(11).setContent(GlobalData.mStateAnalogLoader.getRoot());
        dMainTabPane.getTabs().get(12).setContent(GlobalData.mStateTumblerLoader.getRoot());
        dMainTabPane.getTabs().get(13).setContent(GlobalData.mStateSensorLoader.getRoot());
        dMainTabPane.getTabs().get(14).setContent(GlobalData.mStateOutBlocksLoader.getRoot());
    }

    protected void initializeTasks(){
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GlobalData.mCurDate.setTime(System.currentTimeMillis());
                        dTimeLabel.setText((GlobalData.mTimePattern.format(GlobalData.mCurDate.getTime())));
                        createNotification(55, false);
                        if(mRunnableNotification != null){
                            dNotificationLabel.setText(mRunnableNotification.substring(2) + (mRunnableNotification.substring(0,2)));
                            mRunnableNotification = dNotificationLabel.getText();
                        System.gc();
                        }
                    }
                });
            }
        };

        mUpdateTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    GlobalData.updateData();
                    Updater.updateTab(dMainTabPane.getSelectionModel().getSelectedIndex());
                });
            }
        };
        GlobalData.mUpdateTimer.scheduleAtFixedRate(mUpdateTask, 0, 50);
        GlobalData.mSystemTimer.scheduleAtFixedRate(mTimerTask,0, 1000);
    }
    protected void initializeListeners(){
        dMainTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                dTabTitle.setText(newValue.getText());
                Platform.runLater(() -> {
                    try {
                        newValue.getContent().impl_traverse(Direction.NEXT);
                    }
                    catch (NullPointerException e){}
                });
            }
        });
    }
    public static void processingPairs(String id, String dom, String val){
        mViewHolder = (VBox) GlobalData.getmMainScene().lookup(id);
        ((Label)mViewHolder.getChildren().get(0)).setText(dom);
        ((Label)mViewHolder.getChildren().get(1)).setText(val);
    }
   /* public static void processingPairs(String id, DNValue tempDNValue){
        mViewHolder = (VBox) GlobalData.getmMainScene().lookup(id);
        mContentLabel = ((Label)mViewHolder.getChildren().get(0));
        mContentLabel.setText(tempDNValue.getDenomination());

        mContentLabel = ((Label)mViewHolder.getChildren().get(1));
        mContentLabel.setText(String.valueOf(tempDNValue.getValue()));

        if(tempDNValue.getMinWarning() > tempDNValue.getValue() | tempDNValue.getValue() > tempDNValue.getMaxWarning()){
            if(tempDNValue.getMinAlarm() > tempDNValue.getValue() | tempDNValue.getValue() > tempDNValue.getMaxAlarm())
                mContentLabel.setStyle("-fx-background-color: red;");
            else
                mContentLabel.setStyle("-fx-background-color: yellow;");
        }
    }*/
    public static void processingPairs(String id, String val){
        Platform.runLater(()->{
            mViewHolder = (VBox) GlobalData.getmMainScene().lookup(id);
            ((Label)mViewHolder.getChildren().get(0)).setText(val);
        });
    }
    public void selectPane(int pressedButton){
        mCurrentTab += String.valueOf(pressedButton);
        if(mCurrentTab.length() == 1) {
            switch (mCurrentTab) {
                case ("0"): {
                    dMainTabPane.getSelectionModel().select(0);
                    appearImage(6);
                    break;
                }
                case ("1"):
                case ("2"):
                case ("3"):
                case ("4"): {
                    dMainTabPane.getSelectionModel().select(Integer.valueOf(mCurrentTab));
                    mCurrentTab = "";
                    break;
                }
                default: {
                    mCurrentTab = "";
                    break;
                }
            }
        }
        else if(mCurrentTab.length() == 2) {
            switch (mCurrentTab) {
                case ("00"): {
                    dMainTabPane.getSelectionModel().select(1);
                    mCurrentTab = "";
                    appearImage(4);
                    break;
                }
                case ("01"):{
                    appearImage(6);
                    dMainTabPane.getSelectionModel().select(5);
                    mCurrentTab = "01";
                    break;
                }
                case ("02"):{
                    appearImage(9);
                    dMainTabPane.getSelectionModel().select(6);
                    mCurrentTab = "02";
                    break;
                }
                case ("03"): ((DataSetterController)GlobalData.mDataSetterLoader.getFXMLloader().getController()).setData();
                case ("04"):
                case ("05"): {
                    appearImage(0);
                    dMainTabPane.getSelectionModel().select(Integer.valueOf(mCurrentTab.substring(mCurrentTab.length() -1)) + 4);
                    mCurrentTab = "0" + pressedButton;
                    break;
                }
                default: {
                    mCurrentTab = "0";
                    break;
                }
            }
        }
        else if(mCurrentTab.length() == 3) {
            if(pressedButton == 0){
                mCurrentTab = "0";
                appearImage(6);
                dMainTabPane.getSelectionModel().select(0);
            }
            else {
                switch (mCurrentTab) {
                    case ("011"):
                    case ("012"):
                    case ("013"):
                    case ("014"):
                    case ("015"):
                    case ("016"): {
                        dMainTabPane.getSelectionModel().select(pressedButton + 9);
                        mCurrentTab = "01" +pressedButton;
                        appearImage(0);
                        break;
                    }
                    default: {
                        mCurrentTab = mCurrentTab.substring(0, mCurrentTab.length() - 1);
                        if(mCurrentTab.equals("02"))
                            ((PasswordController)GlobalData.mPasswordLoader.getFXMLloader().getController()).inputNumber();
                        break;
                    }
                }
            }
        }
        else if(mCurrentTab.length() == 4) {
            if(pressedButton == 0){
                mCurrentTab = "01";
                appearImage(6);
                dMainTabPane.getSelectionModel().select(5);
            }
            else
                mCurrentTab = mCurrentTab.substring(0, mCurrentTab.length() - 1);
        }
    }
    public void deleteNumberOfPassword(){
        if(mCurrentTab.equals("02"))
            ((PasswordController)GlobalData.mPasswordLoader.getFXMLloader().getController()).deleteNumber();
    }
    public void switchTable(){
        if(dMainTabPane.getSelectionModel().getSelectedIndex() == 4) {
            if (!GlobalData.isWarningTable)
                dMainTabPane.getTabs().get(4).setContent(GlobalData.mAlarmLoader.getRoot());
            else
                dMainTabPane.getTabs().get(4).setContent(GlobalData.mWarningLoader.getRoot());
            GlobalData.isWarningTable = !GlobalData.isWarningTable;
            dMainTabPane.getTabs().get(4).getContent().impl_traverse(Direction.NEXT);
        }
    }
    public void deleteNotification(){
        if(!mCurrentTab.equals("02"))
            mNotificator.processLastNotification(false);
        allowRunLabel();
    }
    public void createNotification(int messCode, boolean isWarning){
        GlobalData.mCurDate.setTime(System.currentTimeMillis());
        if(GlobalData.mMessageCodes.get(messCode)!=null) {
            if(!isWarning)
                AlarmFrameController.addElement(
                    GlobalData.mTimePattern.format(GlobalData.mCurDate.getTime()),
                    String.valueOf(messCode),
                    GlobalData.mMessageCodes.get(messCode)
                );
            else
                WarningFrameController.addElement(
                        GlobalData.mTimePattern.format(GlobalData.mCurDate.getTime()),
                        String.valueOf(messCode),
                        GlobalData.mMessageCodes.get(messCode)
                );
        }
        else {
            if(!isWarning)
                WarningFrameController.addElement(
                        GlobalData.mTimePattern.format(GlobalData.mCurDate.getTime()),
                        String.valueOf(messCode),
                        "Неизвестный код ошибки"
                );
            else
                AlarmFrameController.addElement(
                        GlobalData.mTimePattern.format(GlobalData.mCurDate.getTime()),
                        String.valueOf(messCode),
                        "Неизвестный код ошибки"
                );
        }
       /* if(!isWarning)
            dNotificationLabel.setStyle("-fx-background-image: url('" + GlobalData.mMailImagePath + "'); " +
                    "-fx-background-repeat: no-repeat;" +
                    "-fx-background-position: left;");
        else
            dNotificationLabel.setStyle("-fx-background-image: url('" + GlobalData.mFireImagePath + "'); " +
                    "-fx-background-repeat: no-repeat;" +
                    "-fx-background-position: left;");*/
        mNotificator.addNewNotification(messCode);
        mNotificator.processLastNotification(true);
    }
    public void appearImage(int pCount){
        for(Node node:dImageBox.getChildren())
            node.setVisible(false);
        for(int i = 0; i < pCount;i++) {
            dImageBox.getChildren().get(i).setVisible(true);
        }
        dImageBox.getChildren().get(9).setVisible(true);
    }
}









