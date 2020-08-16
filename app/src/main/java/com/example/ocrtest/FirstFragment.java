package com.example.ocrtest;

import android.app.ActivityManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocrtest.model.Tesseract305;
import com.example.ocrtest.model.Tesseract411;
import com.example.ocrtest.model.Test;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import static android.content.Context.ACTIVITY_SERVICE;

public class FirstFragment extends Fragment {

    private static Tesseract305 tesseract305;
    private static Tesseract411 tesseract411;

    private String TESS305_DATA_PATH;
    private String TESS411_DATA_PATH;
    private final String LANGUAGE = "eng";
    private final String LANG_FILE_EXT = ".traineddata";

    //adb shell dumpsys batterystats > C:\Users\matej\OneDrive\Namizje\thesis\tests\tess305\battery\batterystats.txt
    //adb bugreport > C:\Users\matej\OneDrive\Namizje\thesis\tests\tess305\battery\batterystats.txt\bugreport.zip

    private final int[] SEG_MODE = {TessBaseAPI.PageSegMode.PSM_SPARSE_TEXT, TessBaseAPI.PageSegMode.PSM_SPARSE_TEXT_OSD, TessBaseAPI.PageSegMode.PSM_SINGLE_BLOCK,
            TessBaseAPI.PageSegMode.PSM_AUTO};
    private final int[] ENGINE_MODE = { TessBaseAPI.OEM_TESSERACT_ONLY, TessBaseAPI.OEM_LSTM_ONLY, TessBaseAPI.OEM_TESSERACT_LSTM_COMBINED,TessBaseAPI.OEM_DEFAULT}; // TessBaseAPI.OEM_CUBE_ONLY, TessBaseAPI.OEM_TESSERACT_CUBE_COMBINED for tess305
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        TESS305_DATA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/Tesseract/305";
        TESS411_DATA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/Tesseract/411";

     //   tesseract305 = new Tesseract305(LANGUAGE, TESS305_DATA_PATH, SEG_MODE[1], ENGINE_MODE[0], true);
        System.out.println();
        tesseract411 = new Tesseract411(LANGUAGE, TESS411_DATA_PATH, SEG_MODE[1], ENGINE_MODE[1], true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
//            Test test305 = new Test(tesseract305);
//            test305.runTess305Test(getContext(),false);
//            Test test411 = new Test(tesseract411);
//            test411.runTess411Test(getContext(),false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //adb shell dumpsys batterystats > C:/Users/matej/OneDrive/Documents/logs/batterystats.txt
                //adb bugreport > C:/Users/matej/OneDrive/Documents/logs/bugreport.zip
                //adb pull /data/user_de/0/com.android.shell/files/bugreports/bugreport-ELE-L29EEA-HUAWEIELE-L29-2020-07-27-14-26-38.zip C:/Users/matej/OneDrive/Documents/logs
                // docker --run -p 23232:9999 gcr.io/android-battery-historian:2.1 --port 9999

                //  test(srcDir);


//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }



    private ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    private void createTrainDataFile(File f) {
        AssetManager assetManager = getContext().getAssets();
        if (!f.exists())
            try {
                Log.i("TESS INIT", "copied trained data files");
                InputStream is = new FileInputStream(f.getAbsolutePath() + "tessdata/" + LANGUAGE + LANG_FILE_EXT);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    public static Bitmap decodeBitmap(String path,
                                      int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}