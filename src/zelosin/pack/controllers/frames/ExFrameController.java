package zelosin.pack.controllers.frames;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import zelosin.pack.objects.DNValue;

public class ExFrameController {
    private static VBox mViewHolder;
    private static Label mContentLabel;
    @FXML
    private HBox dExHolder0, dExHolder1,dExHolder2,dExHolder3,dExHolder4,dExHolder5;

    public void updateExFrame(DNValue tempDNValue){
        if(tempDNValue.getPosition() < 8)
            processingPairs((VBox) dExHolder0.getChildren().get(tempDNValue.getPosition()), tempDNValue);
        else if(tempDNValue.getPosition() < 16)
            processingPairs((VBox) dExHolder1.getChildren().get(tempDNValue.getPosition()-8), tempDNValue);
        else if(tempDNValue.getPosition() < 22)
            processingPairs((VBox) dExHolder2.getChildren().get(tempDNValue.getPosition()-16), tempDNValue);
        else if(tempDNValue.getPosition() < 28)
            processingPairs((VBox) dExHolder3.getChildren().get(tempDNValue.getPosition()-22), tempDNValue);
        else if(tempDNValue.getPosition() < 33)
            processingPairs((VBox) dExHolder4.getChildren().get(tempDNValue.getPosition()-28), tempDNValue);
        else
            processingPairs((VBox) dExHolder5.getChildren().get(tempDNValue.getPosition()-33), tempDNValue);
    }

    public static void processingPairs(VBox tempVBox, DNValue tempDNValue){
        mContentLabel = ((Label)tempVBox.getChildren().get(0));
        mContentLabel.setStyle("-fx-background-color: green;");
        mContentLabel.setText(tempDNValue.getDenomination());

        mContentLabel = ((Label)tempVBox.getChildren().get(1));
        mContentLabel.setText(String.valueOf(tempDNValue.getValue()));

        if(tempDNValue.getMinWarning() > tempDNValue.getValue() | tempDNValue.getValue() > tempDNValue.getMaxWarning()){
            if(tempDNValue.getMinAlarm() > tempDNValue.getValue() | tempDNValue.getValue() > tempDNValue.getMaxAlarm())
                mContentLabel.setStyle("-fx-background-color: red;");
            else
                mContentLabel.setStyle("-fx-background-color: yellow;");
        }
        mContentLabel = null;
    }
}
