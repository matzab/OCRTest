package com.example.ocrtest.model;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.example.ocrtest.model.Utils.FileExporter;
import com.example.ocrtest.model.Utils.Timer;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {
    private final String TESS_LOG_DIR = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/Logs";
    private final String IMAGE_SRC_DIR = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/Test_Images_OCR/100";
    private int SAMPLE_SIZE = 100;


    private String srcText;
    private static Bitmap imageBitmap;
    private static Timer timer;
    private Timer unitTimer;
    private SimpleDateFormat dateFormat;
    private String logDate;
    private FileExporter fe;
    private String startTemp;
    private String endTemp;

    private Tesseract305 tess305;
    private Tesseract411 tesseract411;

    public Test(Tesseract305 tess305) {
        this.tess305 = tess305;
    }

    public Test(Tesseract411 tesseract411) {
        this.tesseract411 = tesseract411;
    }

//    public void runTess305Test(Context context, boolean debugMode){
//        File src = new File(IMAGE_SRC_DIR);
//        final File[] files = src.listFiles();
//
//        timer = new Timer();
//        fe = new FileExporter();
//
//        dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.getDefault());
//        logDate = dateFormat.format(new Date());
//        String testLogDir = TESS_LOG_DIR + "/test-" + logDate;
//        String dataLogDir = testLogDir + "/data";
//        initDir(testLogDir);
//        initDir(dataLogDir);
//        fe.exportCSV(testLogDir + "/tess.csv");
//
//        Log.i("TEST", "test started");
//
//        startTemp = batteryTemperature(context);
//        timer.start();
//
//        String traceFileName = "/trace-" + logDate;
//        if (debugMode) {
//            Debug.startMethodTracing(testLogDir + traceFileName);
//        }
//        for (int c = 0; c < SAMPLE_SIZE; c++) {
//            if (imageBitmap != null) {
//                imageBitmap.recycle();
//                imageBitmap = null;
//            }
//
//            File image = files[c];
//            imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
//            unitTimer = new Timer();
//            System.out.println("Image " + (c + 1) + "/" + SAMPLE_SIZE + ", image size: " + imageBitmap.getAllocationByteCount());
//
//            unitTimer.start();
//            //   srcText = mTess.getResult(decodeBitmap(files[c].getAbsolutePath(), 300, 300));
//            srcText = tess305.getResult(imageBitmap);
//            unitTimer.end();
//
//            System.out.println("Time: " + unitTimer.getMillis());
//            System.out.println();
//
//            fe.appendCSVLine(image.getName(), imageBitmap.getAllocationByteCount(), unitTimer.getMillis());
//            fe.exportTXT(dataLogDir + "/" + files[c].getName() + ".txt", srcText);
//        }
//        timer.end();
//        tess305.destroy();
//        String hprofName = "hprof-" + logDate;
//        if (debugMode) {
//            try {
//                Debug.dumpHprofData(testLogDir + "/hprof-" + logDate);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Debug.stopMethodTracing();
//        }
//        endTemp = batteryTemperature(context);
//        Log.i("TEST", "test ended");
//        Log.i("TEST", "IMAGES PROCESSED: " + SAMPLE_SIZE + "\n" + "TOTAL TIME: " + timer.getMillis() + " ms " + "ALG INFO: \n" + tess305.getParamInfo());
//        String endRes = String.format("Debug mode: %b%nTrace file name: %s%nHprof file name: %s%nNr. of images processed: %d%nTotal time: %.2f ms%n Start temp: %s%n" +
//                "End temp: %s%nAlgorithm info: %n%s", debugMode, traceFileName, hprofName, SAMPLE_SIZE, timer.getMillis(), startTemp, endTemp, tess305.getParamInfo());
//        fe.exportTXT(testLogDir + "/a_test_info.txt", endRes);
//        fe.closeCSVFile();
//    }
    public void runTess411Test(Context context, boolean debugMode){
        File src = new File(IMAGE_SRC_DIR);
        final File[] files = src.listFiles();

        timer = new Timer();
        fe = new FileExporter();

        dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.getDefault());
        logDate = dateFormat.format(new Date());
        String testLogDir = TESS_LOG_DIR + "/test-" + logDate;
        String dataLogDir = testLogDir + "/data";
        initDir(testLogDir);
        initDir(dataLogDir);
        fe.exportCSV(testLogDir + "/tess.csv");

        Log.i("TEST", "test started");

        startTemp = batteryTemperature(context);
        timer.start();

        String traceFileName = "/trace-" + logDate;
        if (debugMode) {
            Debug.startMethodTracing(testLogDir + traceFileName);
        }
        for (int c = 0; c < SAMPLE_SIZE; c++) {
            if (imageBitmap != null) {
                imageBitmap.recycle();
                imageBitmap = null;
            }

            File image = files[c];
            imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            unitTimer = new Timer();
            System.out.println("Image " + (c + 1) + "/" + SAMPLE_SIZE + ", image size: " + imageBitmap.getAllocationByteCount());

            unitTimer.start();
            //   srcText = mTess.getResult(decodeBitmap(files[c].getAbsolutePath(), 300, 300));
            srcText = tesseract411.getResult(imageBitmap);
            unitTimer.end();

           // System.out.println("Memory: " + getUsedMemorySize());
            System.out.println("Time: " + unitTimer.getMillis());
            System.out.println();

            fe.appendCSVLine(image.getName(), imageBitmap.getAllocationByteCount(), unitTimer.getMillis());
            fe.exportTXT(dataLogDir + "/" + files[c].getName() + ".txt", srcText);
        }
        timer.end();
        String hprofName = "hprof-" + logDate;
        if (debugMode) {
            try {
                Debug.dumpHprofData(testLogDir + "/hprof-" + logDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Debug.stopMethodTracing();
        }
        endTemp = batteryTemperature(context);
        Log.i("TEST", "test ended");
        Log.i("TEST", "IMAGES PROCESSED: " + SAMPLE_SIZE + "\n" + "TOTAL TIME: " + timer.getMillis() + " ms " + "ALG INFO: \n" + tesseract411.getParamInfo());
        String endRes = String.format("Debug mode: %b%nTrace file name: %s%nHprof file name: %s%nNr. of images processed: %d%nTotal time: %.2f ms%n Start temp: %s%n" +
                "End temp: %s%nAlgorithm info: %n%s", debugMode, traceFileName, hprofName, SAMPLE_SIZE, timer.getMillis(), startTemp, endTemp, tesseract411.getParamInfo());
        fe.exportTXT(testLogDir + "/a_test_info.txt", endRes);
        fe.closeCSVFile();
        tesseract411.destroy();
    }

    public static String batteryTemperature(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        float temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
        return String.valueOf(temp) + "*C";
    }

    private void initDir(String dest) {
        File dir = new File(dest);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static long getUsedMemorySize() {

        long freeSize = 0L;
        long totalSize = 0L;
        long usedSize = -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            usedSize = totalSize - freeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usedSize;

    }
}
