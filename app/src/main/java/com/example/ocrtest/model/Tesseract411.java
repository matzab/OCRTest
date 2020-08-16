package com.example.ocrtest.model;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Tesseract411 {
    private TessBaseAPI mTess411;
    private int engineMode;
    private boolean dicDisabled; // disabled dictionary words

    public Tesseract411(String language, String tessDataPath) {
        mTess411 = new TessBaseAPI();
        System.out.println(mTess411.getVersion());


        mTess411.init(tessDataPath + "/", language);
    }

    public Tesseract411(String language, String tessDataPath, int segMode, int engineMode, boolean disableDictionary) {
        mTess411 = new TessBaseAPI();
        this.engineMode = engineMode;
        mTess411.setPageSegMode(segMode);
        mTess411.init(tessDataPath + "/", language, engineMode);
        if(disableDictionary) {
            dicDisabled = mTess411.setVariable("load_freq_dawg", "false");
        }
    }


    public String getResult(Bitmap bitmap) {
        mTess411.setImage(bitmap);
        return mTess411.getUTF8Text();
    }

    public void destroy() {
        if (mTess411 != null) {
            mTess411.end();
        }
    }

    public String getParamInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("*****************************");
        sb.append("\nVersion: " + mTess411.getVersion());
        sb.append("\nEngine mode: " + engineMode);
        sb.append("\nSegmentation mode:  " + mTess411.getPageSegMode());
        sb.append("\nDictionary disabled:  " + dicDisabled);
        sb.append("\n*****************************");
        return sb.toString();
    }
}
