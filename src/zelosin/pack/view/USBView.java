package zelosin.pack.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class USBView {

    private static ListView<String> mUSBListView;

    public static void fillUSBView(ListView<String> pListView){
        mUSBListView = pListView;
        mUSBListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mUSBListView.setCellFactory(param -> new ListCell<String>(){
            public final Image mUSBImage = new Image("zelosin/pack/images/usb.png");
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                }
                else {
                    VBox mVBox = new VBox();
                    Label mTempLabel = new Label(name);
                    HBox tempHBox = new HBox();
                    HBox imageHBox = new HBox();
                    tempHBox.setAlignment(Pos.CENTER);
                    tempHBox.getChildren().add(mTempLabel);
                    imageHBox.setAlignment(Pos.CENTER);
                    imageHBox.getChildren().add(new ImageView(mUSBImage));
                    mTempLabel.setAlignment(Pos.CENTER);
                    mTempLabel.setTextFill(Color.web("white"));
                   // mTempLabel.setPadding(new Insets(0, 0, 0, 10));
                    mVBox.getChildren().addAll( imageHBox, tempHBox);
                    mVBox.setScaleX(0.75);
                    mVBox.setScaleY(0.75);
                    mVBox.setPadding(new Insets(10, 10, 0, 0));
                    setGraphic(mVBox);
                }
            }
        });
    }
}
