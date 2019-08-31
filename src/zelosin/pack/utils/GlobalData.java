package zelosin.pack.utils;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import zelosin.pack.base.Main;
import zelosin.pack.parsers.INIParser;
import zelosin.pack.parsers.XMLParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GlobalData {

    public class GeneratedId {
        public final static String FIRSTTABID = "FirstPane";
        public final static String SECONDTABID = "SecondPane";
        public final static String THIRDTABID = "ThirdPane";
        public final static String FOURTHTABID = "FourthPane";
        public final static String ZEROTABID = "ZeroPane";
        public final static String LEFTBOXID = "LeftColumn";
    }

    public class FilesPath{
        private final static String mBaseDir = "C:\\";
        private final static String MAIN_WINDOW_FXML_PATH = "zelosin/pack/fxml/MainWindowFXML.fxml";
        private final static String LOADING_WINDOW_FXML_PATH = "zelosin/pack/fxml/LoadingWindowFXML.fxml";
        private final static String USB_FRAME_FXML_PATH = "zelosin/pack/fxml/frames/USBFrameFXML.fxml";
        private final static String EX_FRAME_FXML_PATH = "zelosin/pack/fxml/frames/ExFrameFXML.fxml";
        private final static String WARNING_MESSAGE_FXML_PATH = "zelosin/pack/fxml/frames/messages/WarningMessagesFrameFXML.fxml";
        private final static String ALARM_MESSAGE_FXML_PATH = "zelosin/pack/fxml/frames/messages/AlarmMessagesFrameFXML.fxml";
        private final static String MENU_FRAME_FXML_PATH = "zelosin/pack/fxml/menu/MenuFrameFXML.fxml";
        private final static String SCHEME_FRAME_FXML_PATH = "zelosin/pack/fxml/frames/SchemeFrameFXML.fxml";
        private final static String MAIN_FRAME_FXML_PATH = "zelosin/pack/fxml/frames/MainFrameFXML.fxml";
        private final static String LEFT_BAR_FXML_PATH = "zelosin/pack/fxml/bars/LeftBarFXML.fxml";
        private final static String SUBTITLE_BAR_FXML_PATH = "zelosin/pack/fxml/bars/SubTitleBarFXML.fxml";
        private final static String PROGRAM_VERSION_FXML_PATH = "zelosin/pack/fxml/frames/ProgramVersionFrameFXML.fxml";
        private final static String PASSWORD_FXML_PATH = "zelosin/pack/fxml/frames/PasswordFrameFXML.fxml";
        private final static String DATE_SETTER_FXML_PATH = "zelosin/pack/fxml/frames/DateSetterFrameFXML.fxml";
        private final static String EX_MENU_FRAME_FXML_PATH = "zelosin/pack/fxml/menu/ExMenuFrameFXML.fxml";
        private final static String STATE_TABLE_DISCRETE_FRAME_FXML_PATH = "zelosin/pack/fxml/states/DiscreteFrameFXML.fxml";
        private final static String STATE_TABLE_TUMBLER_FRAME_FXML_PATH = "zelosin/pack/fxml/states/TumblerFrameFXML.fxml";
        private final static String STATE_TABLE_SENSOR_FRAME_FXML_PATH = "zelosin/pack/fxml/states/SensorFrameFXML.fxml";
        private final static String STATE_TABLE_ANALOG_FRAME_FXML_PATH = "zelosin/pack/fxml/states/AnalogFrameFXML.fxml";
        private final static String STATE_OUT_BLOCKS_FRAME_FXML_PATH = "zelosin/pack/fxml/states/OutBlocksFrame.fxml";
        private final static String INI_PATH = "zelosin/pack/ini/preset.ini";
    }

    public static final int TABWIDTH = 810;
    public static final int TABHEIGHT = 530;
    public static final int MAXNOTIFICATION = 1000;
    public static int mDepthLevel = 1;
    public static boolean isWarningTable = true;

    public final static Timer mUpdateTimer = new Timer();
    public final static Timer mSystemTimer = new Timer();
    public static final SimpleDateFormat mTimePattern = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat mDatePattern = new SimpleDateFormat("dd-MM-yyyy");
/*    public final static String mMailImagePath = GlobalData.class.getResource("src/zelosin/pack/images/mail.png").toExternalForm();
    public final static String mFireImagePath = GlobalData.class.getResource("src/zelosin/pack/images/fire.png").toExternalForm();*/

    public static final Date mCurDate =  new Date(System.currentTimeMillis());

    public static Map<Integer, String> mMessageCodes = new HashMap<>();
    public static Map<Integer, String> mColorLevel = new HashMap<>();
    public static Map<Integer, Integer> mMessageLevel = new HashMap<>();

    public static Loader mStateOutBlocksLoader;
    public static Loader mStateDiscreteLoader;
    public static Loader mStateSensorLoader;
    public static Loader mStateTumblerLoader;
    public static Loader mDataSetterLoader;
    public static Loader mExMenuLoader;
    public static Loader mWarningLoader;
    public static Loader mPasswordLoader;
    public static Loader mProgramVersionLoader;
    public static Loader mAlarmLoader;
    public static Loader mExFrameLoader;
    public static Loader mStateAnalogLoader;
    public static Loader mUsbLoader;
    public static Loader mLeftBarLoader;
    public static Loader mSubTitleBarLoader;
    public static Loader mSchemeLoader;
    public static Loader mMainFrameLoader;
    public static Loader mMenuLoader;
    public static Loader mLoadingWindowLoader;
    public static Loader mMainLoader;

    private static Scene mMainScene;

    private static int[] mInputData = new int[256];
    private static Random mGenerator = new Random();
    private static Task<Void> mMainLoadTask;

    public static class Loader{
        private Parent mRoot;
        private FXMLLoader mFXMLLoader = new FXMLLoader();

        public Loader(String path){
            mFXMLLoader.setLocation(GlobalData.class.getClassLoader().getResource(path));
            try {
                mRoot = mFXMLLoader.load();
                if (!path.equals(FilesPath.LOADING_WINDOW_FXML_PATH)){
                    mRoot.styleProperty().bind(Bindings.format("-fx-font-size: %dpt;", INIParser.settingsPreset.getmFontSize()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Parent getRoot() {
            return mRoot;
        }

        public FXMLLoader getFXMLloader() {
            return mFXMLLoader;
        }
    }

    static{
        mMainLoadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(100);
                INIParser.parse();
                updateProgress(10, 100);
                updateMessage("Загрузка шаблонов");
                Thread.sleep(1000);

               // mFontSize = new SimpleDoubleProperty(INIParser.settingsPreset.getmFontSize());
                updateProgress(20, 100);
                mStateOutBlocksLoader = new Loader(FilesPath.STATE_OUT_BLOCKS_FRAME_FXML_PATH);
                mStateDiscreteLoader = new Loader(FilesPath.STATE_TABLE_DISCRETE_FRAME_FXML_PATH);
                updateMessage("Загрузка шаблонов.");
                Thread.sleep(500);
                mStateSensorLoader = new Loader(FilesPath.STATE_TABLE_SENSOR_FRAME_FXML_PATH);
                mStateTumblerLoader = new Loader(FilesPath.STATE_TABLE_TUMBLER_FRAME_FXML_PATH);
                updateMessage("Загрузка шаблонов..");
                Thread.sleep(500);
                updateProgress(30, 100);
                mDataSetterLoader = new Loader(FilesPath.DATE_SETTER_FXML_PATH);
                mExMenuLoader = new Loader(FilesPath.EX_MENU_FRAME_FXML_PATH);
                updateProgress(40, 100);
                mWarningLoader = new Loader(FilesPath.WARNING_MESSAGE_FXML_PATH);
                updateMessage("Загрузка шаблонов...");
                updateProgress(50, 100);


                mPasswordLoader = new Loader(FilesPath.PASSWORD_FXML_PATH);
                mProgramVersionLoader = new Loader(FilesPath.PROGRAM_VERSION_FXML_PATH);
                updateProgress(60, 100);
                mAlarmLoader = new Loader(FilesPath.ALARM_MESSAGE_FXML_PATH);
                mExFrameLoader = new Loader(FilesPath.EX_FRAME_FXML_PATH);
                updateMessage("Загрузка шаблонов");
                mStateAnalogLoader = new Loader(FilesPath.STATE_TABLE_ANALOG_FRAME_FXML_PATH);
                mUsbLoader = new Loader(FilesPath.USB_FRAME_FXML_PATH);
                mLeftBarLoader = new Loader(FilesPath.LEFT_BAR_FXML_PATH);
                updateMessage("Загрузка шаблонов.");
                updateProgress(70, 100);
                mSubTitleBarLoader = new Loader(FilesPath.SUBTITLE_BAR_FXML_PATH);
                mSchemeLoader = new Loader(FilesPath.SCHEME_FRAME_FXML_PATH);
                updateMessage("Загрузка шаблонов..");
                mMainFrameLoader = new Loader(FilesPath.MAIN_FRAME_FXML_PATH);
                mMenuLoader = new Loader(FilesPath.MENU_FRAME_FXML_PATH);
                updateMessage("Загрузка шаблонов...");
                Thread.sleep(1000);
                updateMessage("Загрузка файлов");
                XMLParser.loadData();
                XMLParser.loadCodes();
                updateData();
                updateProgress(90, 100);
                updateMessage("Применение настроек");
                Thread.sleep(500);
                mColorLevel.put(1, INIParser.settingsPreset.getmColorTier1());
                mColorLevel.put(2, INIParser.settingsPreset.getmColorTier2());
                mColorLevel.put(3, INIParser.settingsPreset.getmColorTier3());
                mColorLevel.put(4, INIParser.settingsPreset.getmColorTier4());
                mColorLevel.put(5, INIParser.settingsPreset.getmColorTier5());

                updateProgress(100, 100);

                mMainLoader = new Loader(FilesPath.MAIN_WINDOW_FXML_PATH);
                mMainScene = new Scene(mMainLoader.getRoot());
                Main.initializeSettings();
                updateMessage("Загрузка завершена");
                Thread.sleep(1000);
                updateMessage("Done!");
                return null;
            }
        };
        mMainLoadTask.messageProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Done!")) {
                Main.setContent();
            }
        });
        mLoadingWindowLoader = new Loader(FilesPath.LOADING_WINDOW_FXML_PATH);
    }

    public static void updateData(){
        for (int i = 0; i < mInputData.length; i++)
            mInputData[i] = mGenerator.nextInt(255);
    }
    public static int randomValue(){
        return mGenerator.nextInt(255);
    }
    public static int processingValue(int[] paramSlice){
        int mValue = 0;
        for(int index = 0; index<paramSlice.length; index++)
            mValue+=paramSlice[index]*Math.pow(256,(paramSlice.length-1-index));
        return mValue;
    }

    public static String getmBaseDir() {
        return FilesPath.mBaseDir;
    }
    public static Scene getmMainScene() {
        return mMainScene;
    }
    public static int[] getmInputData() {
        return mInputData;
    }
    public static Random getmGenerator() {
        return mGenerator;
    }
    public static String getINIPath(){
        return FilesPath.INI_PATH;
    }
    public static Task<Void> getmLoadThread() {
        return mMainLoadTask;
    }
}












