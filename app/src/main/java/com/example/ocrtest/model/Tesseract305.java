package com.example.ocrtest.model;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Tesseract305 {
    private TessBaseAPI mTess305;
    private int engineMode;
    private boolean dicDisabled; // disabled dictionary words

    public Tesseract305(String language, String tessDataPath) {
        mTess305 = new TessBaseAPI();
        System.out.println(mTess305.getVersion());


        mTess305.init(tessDataPath + "/", language);
    }

    public Tesseract305(String language, String tessDataPath, int segMode, int engineMode, boolean disableDictionary) {
        mTess305 = new TessBaseAPI();
        this.engineMode = engineMode;
        mTess305.setPageSegMode(segMode);
        mTess305.init(tessDataPath + "/", language, engineMode);
        if(disableDictionary) {
            dicDisabled = mTess305.setVariable("load_freq_dawg", "false");
        }
    }


    public String getResult(Bitmap bitmap) {
        mTess305.clear();
        mTess305.setImage(bitmap);
        return mTess305.getUTF8Text();
    }

    public void destroy() {
        if (mTess305 != null) {
            mTess305.end();
        }
    }

    public String getParamInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("*****************************");
        sb.append("\nVersion: " + mTess305.getVersion());
        sb.append("\nEngine mode: " + engineMode);
        sb.append("\nSegmentation mode:  " + mTess305.getPageSegMode());
        sb.append("\nDictionary disabled:  " + dicDisabled);
        sb.append("\n*****************************");
        return sb.toString();
    }
}
