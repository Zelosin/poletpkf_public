package zelosin.pack.controllers.frames;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class PasswordController {

    @FXML
    private Label dFirNum, dSecNum, dThrNum, dForNum, dFifNum;
    private ArrayList<Label> mLabelFlow = new ArrayList<>();
    private int mCurNum = 0;

    public void initialize(){
        mLabelFlow.add(dFirNum);
        mLabelFlow.add(dSecNum);
        mLabelFlow.add(dThrNum);
        mLabelFlow.add(dForNum);
        mLabelFlow.add(dFifNum);
    }

    public void inputNumber(){
        if(mCurNum != 5) {
            mLabelFlow.get(mCurNum).setText("*");
            mCurNum++;
        }
    }
    public void deleteNumber(){
        if(mCurNum != 0)
            mCurNum--;
        mLabelFlow.get(mCurNum).setText(null);
    }
}



















