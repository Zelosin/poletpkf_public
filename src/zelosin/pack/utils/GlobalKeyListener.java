package zelosin.pack.utils;

import javafx.application.Platform;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import zelosin.pack.controllers.MainWindowController;

public class GlobalKeyListener implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        switch (NativeKeyEvent.getKeyText(e.getKeyCode())){
            case("0"):
            case("1"):
            case("2"):
            case("3"):
            case("4"):
            case("5"):
            case("6"):
            case("7"):
            case("8"):
            case("9"):{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ((MainWindowController)GlobalData.mMainLoader.getFXMLloader().getController()).selectPane(Integer.valueOf(NativeKeyEvent.getKeyText(e.getKeyCode())));
                    }
                });
                break;
            }
            case("Enter"):{
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ((MainWindowController)GlobalData.mMainLoader.getFXMLloader().getController()).switchTable();
                    }
                });
            }
                break;
            case("Backspace"):{
                Platform.runLater(()->{
                    ((MainWindowController)GlobalData.mMainLoader.getFXMLloader().getController()).deleteNumberOfPassword();
                    ((MainWindowController)GlobalData.mMainLoader.getFXMLloader().getController()).deleteNotification();
                });
            }break;
            default: break;
        }
    }
    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}