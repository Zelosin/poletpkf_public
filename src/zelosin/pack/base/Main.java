package zelosin.pack.base;

import com.sun.jna.platform.win32.User32;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import zelosin.pack.controllers.LoadingWindowController;
import zelosin.pack.controllers.frames.UsbFrameController;
import zelosin.pack.utils.GlobalData;
import zelosin.pack.utils.GlobalKeyListener;

import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static Stage mPrimaryStage;

    @Override
    public void start(Stage primaryStage){
        mPrimaryStage = primaryStage;
        mPrimaryStage.setScene(new Scene(GlobalData.mLoadingWindowLoader.getRoot()));
        mPrimaryStage.setTitle("FolderBase");
        mPrimaryStage.setResizable(false);
        mPrimaryStage.show();
        ((LoadingWindowController)GlobalData.mLoadingWindowLoader.getFXMLloader().getController()).startLoading();
        mPrimaryStage.setOnCloseRequest(e ->{
            Platform.exit();
            System.exit(0);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static void setContent(){
        mPrimaryStage.setScene(GlobalData.getmMainScene());
        mPrimaryStage.show();
        mPrimaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                mPrimaryStage.requestFocus();
            }
        });
    }

    public static void initializeSettings(){
        ((UsbFrameController)GlobalData.mUsbLoader.getFXMLloader().getController()).sethWnd(User32.INSTANCE.FindWindow(null, "FolderBase"));
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }
}
