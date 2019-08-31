package zelosin.pack.parsers;
import org.ini4j.Ini;
import zelosin.pack.utils.GlobalData;

import java.io.File;
import java.io.IOException;

public class INIParser {
    private static Ini mReader;
    private static final String INI_PATH = GlobalData.getINIPath();

    public static void parse(){
        mReader = new Ini();
        loader.loadData();
        mReader.clear();
    }

    public static class settingsPreset{
        private static Integer mFontSize;
        private static String mColorTier1;
        private static String mColorTier2;
        private static String mColorTier3;
        private static String mColorTier4;
        private static String mColorTier5;

        public static Integer getmFontSize() {
            return mFontSize;
        }
        public static String getmColorTier1() {
            return mColorTier1;
        }
        public static String getmColorTier2() {
            return mColorTier2;
        }
        public static String getmColorTier3() {
            return mColorTier3;
        }
        public static String getmColorTier4() {
            return mColorTier4;
        }
        public static String getmColorTier5() {
            return mColorTier5;
        }
    }

    public static class loader extends settingsPreset{
        private static void loadData() {
            try {
                mReader.load(new File(INIParser.class.getClassLoader().getResource(INI_PATH).getFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            settingsPreset.mFontSize = (mReader.get("DATA", "FONT_SIZE", Integer.class));
            settingsPreset.mColorTier1 = mReader.get("DATA", "COLOR_TIER_1", String.class);
            settingsPreset.mColorTier2 = mReader.get("DATA", "COLOR_TIER_2", String.class);
            settingsPreset.mColorTier3 = mReader.get("DATA", "COLOR_TIER_3", String.class);
            settingsPreset.mColorTier4 = mReader.get("DATA", "COLOR_TIER_4", String.class);
            settingsPreset.mColorTier5 = mReader.get("DATA", "COLOR_TIER_5", String.class);
        }
    }

}

