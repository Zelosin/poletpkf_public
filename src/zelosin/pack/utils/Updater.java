package zelosin.pack.utils;

import zelosin.pack.controllers.frames.ExFrameController;
import zelosin.pack.objects.DNValue;

public class Updater {
    private static float mTempValue;
    public static void updateTab(int curTab){
            switch (curTab) {
                case (2): {
                    for(DNValue tempDNValue:DNValue.mDNValuesCollection.get(1)){
                        tempDNValue.updateValue();
                        //MainWindowController.processingPairs("#dExPair"+tempDNValue.getPosition(), tempDNValue);
                        ((ExFrameController)GlobalData.mExFrameLoader.getFXMLloader().getController()).updateExFrame(tempDNValue);
                    }
                    break;
                }
                default: break;
            }
    }
}





















