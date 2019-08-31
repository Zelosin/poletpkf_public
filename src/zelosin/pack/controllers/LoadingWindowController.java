package zelosin.pack.controllers;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import zelosin.pack.utils.GlobalData;

public class LoadingWindowController {

    private Thread mLoadingThread;

    @FXML
    private Label dLoadStateLabel;
    @FXML
    private ProgressBar dLoadProgressBar;

    public void initialize(){
        dLoadStateLabel.textProperty().bind(GlobalData.getmLoadThread().messageProperty());
        dLoadProgressBar.progressProperty().bind(GlobalData.getmLoadThread().progressProperty());
        mLoadingThread = new Thread(GlobalData.getmLoadThread());
        mLoadingThread.setDaemon(true);

    }
    public void startLoading(){
        mLoadingThread.start();
    }
}
