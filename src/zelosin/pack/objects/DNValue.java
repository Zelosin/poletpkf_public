package zelosin.pack.objects;

import zelosin.pack.utils.GlobalData;
import java.util.ArrayList;
import java.util.Arrays;

public class DNValue {
    public static ArrayList<ArrayList<DNValue>> mDNValuesCollection = new ArrayList<>();

    private String mDenomination;
    private int mSlice;
    private int mWindow;
    private int mPosition;
    private int mCapacity;
    private int mMaxValue;
    private int mMinValue;
    private float mKRation;
    private float mBRation;
    private int mMinWarning;
    private int mMaxWarning;
    private int mMinAlarm;
    private int mMaxAlarm;
    private float mValue;

    static {
        for(int i = 0; i< 10; i++)
            mDNValuesCollection.add(new ArrayList<>());
    }

    public DNValue(String denomination, int slice, int window,
                   int position, int capacity, int maxValue,
                   int minValue, float KRation, float BRation,
                   int minWarning, int maxWarning, int minAlarm,
                   int maxAlarm)
    {
        mDenomination = denomination;
        mSlice = slice;
        mWindow = window;
        mPosition = position;
        mCapacity = capacity;
        mMaxValue = maxValue;
        mMinValue = minValue;
        mKRation = KRation;
        mBRation = BRation;
        mMinWarning = minWarning;
        mMaxWarning = maxWarning;
        mMinAlarm = minAlarm;
        mMaxAlarm = maxAlarm;
        mDNValuesCollection.get(window).add(this);
    }

    public void updateValue(){
        mValue =GlobalData.processingValue(Arrays.copyOfRange(GlobalData.getmInputData(), mSlice, mSlice + mCapacity));
    }

    public float getValue() {
        return mValue;
    }

    public String getDenomination() {
        return mDenomination;
    }

    public int getSlice() {
        return mSlice;
    }

    public int getWindow() {
        return mWindow;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public float getKRation() {
        return mKRation;
    }

    public float getBRation() {
        return mBRation;
    }

    public int getMinWarning() {
        return mMinWarning;
    }

    public int getMaxWarning() {
        return mMaxWarning;
    }

    public int getMinAlarm() {
        return mMinAlarm;
    }

    public int getMaxAlarm() {
        return mMaxAlarm;
    }

    @Override
    public String toString() {
        return "DNValue{" +
                "mDenomination='" + mDenomination + '\'' +
                ", mSlice=" + mSlice +
                ", mWindow=" + mWindow +
                ", mPosition=" + mPosition +
                ", mCapacity=" + mCapacity +
                ", mMaxValue=" + mMaxValue +
                ", mMinValue=" + mMinValue +
                '}';
    }
}


















