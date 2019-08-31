package zelosin.pack.controllers.frames;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import zelosin.pack.utils.GlobalData;

import java.io.IOException;
import java.util.ArrayList;

public class DataSetterController {

    @FXML
    private Button dDayBt, dMonthBt, dYearBt, dHourBt, dMinuteBt, dAcceptBt;
    private ArrayList<Button> mFocusTraverse = new ArrayList<>();
    private int mFocusElem = 0;
    private int mTempInt;
    private String mCurDate, mCurTime;

    public void initialize(){
        mFocusTraverse.add(dDayBt);
        mFocusTraverse.add(dMonthBt);
        mFocusTraverse.add(dYearBt);
        mFocusTraverse.add(dHourBt);
        mFocusTraverse.add(dMinuteBt);
        mFocusTraverse.add(dAcceptBt);
        mFocusTraverse.forEach((pButton)->
                pButton.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if(event.getCode().equals(KeyCode.RIGHT) & mFocusElem < 5)
                        mFocusElem++;
                    mFocusTraverse.get(mFocusElem).requestFocus();
                    if(event.getCode().equals(KeyCode.LEFT) & mFocusElem > 0)
                        mFocusElem--;
                    if(event.getCode().equals(KeyCode.UP)) {
                        if(mFocusElem < 5) {
                            mTempInt = Integer.valueOf(mFocusTraverse.get(mFocusElem).getText());
                            if (mTempInt == 31 & mFocusElem == 0)
                                mFocusTraverse.get(mFocusElem).setText("1");
                            else if (mTempInt == 12 & mFocusElem == 1)
                                mFocusTraverse.get(mFocusElem).setText("1");
                            else if (mTempInt == 2050 & mFocusElem == 2)
                                mFocusTraverse.get(mFocusElem).setText("2019");
                            else if (mTempInt == 23 & mFocusElem == 3)
                                mFocusTraverse.get(mFocusElem).setText("0");
                            else if (mTempInt == 59 & mFocusElem == 4)
                                mFocusTraverse.get(mFocusElem).setText("0");
                            else
                                mFocusTraverse.get(mFocusElem).setText(String.valueOf(++mTempInt));
                        }
                    }
                    if(event.getCode().equals(KeyCode.DOWN)) {
                        if( mFocusElem < 5) {
                            mTempInt = Integer.valueOf(mFocusTraverse.get(mFocusElem).getText());
                            if (mTempInt == 1 & mFocusElem == 0)
                                mFocusTraverse.get(mFocusElem).setText("31");
                            else if (mTempInt == 1 & mFocusElem == 1)
                                mFocusTraverse.get(mFocusElem).setText("12");
                            else if (mTempInt == 2000 & mFocusElem == 2)
                                mFocusTraverse.get(mFocusElem).setText("2050");
                            else if (mTempInt == 0 & mFocusElem == 3)
                                mFocusTraverse.get(mFocusElem).setText("23");
                            else if (mTempInt == 0 & mFocusElem == 4)
                                mFocusTraverse.get(mFocusElem).setText("59");
                            else
                                mFocusTraverse.get(mFocusElem).setText(String.valueOf(--mTempInt));
                        }
                    }
                    mFocusTraverse.get(mFocusElem).requestFocus();
                }));
        dDayBt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue)
                    mFocusElem = 0;
            }
        });
        dAcceptBt.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                try {
                    Runtime.getRuntime().exec("cmd /C date "+dDayBt.getText()+"/"+dMonthBt+"/"+dYearBt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setData(){
        GlobalData.mCurDate.setTime(System.currentTimeMillis());
        mCurDate = GlobalData.mDatePattern.format(GlobalData.mCurDate);
        mCurTime = GlobalData.mTimePattern.format(GlobalData.mCurDate);

        dDayBt.setText(mCurDate.substring(0, 2));
        dMonthBt.setText(mCurDate.substring(3, 5));
        dYearBt.setText(mCurDate.substring(6, 10));

        dHourBt.setText(mCurTime.substring(0, 2));
        dMinuteBt.setText(mCurTime.substring(3, 5));
    }
    public void onActionSetSystemTime(ActionEvent actionEvent) {

    }
}
