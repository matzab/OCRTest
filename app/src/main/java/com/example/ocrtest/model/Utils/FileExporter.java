package com.example.ocrtest.model.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileExporter {
    private ArrayList<String> data;

    private PrintWriter writerCSV;
    private StringBuilder sb;


    public void exportCSV (String destination) {
        try {
            File file = new File(destination);
            file.createNewFile();
            PrintWriter w = new PrintWriter(file);
            this.writerCSV = w;

            sb = new StringBuilder();
            sb.append("File Name");
            sb.append(',');
            sb.append("Image Size");
            sb.append(',');
            sb.append("Time (ms)");
            sb.append('\n');

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportTXT (String destination, String data) {
        try {
            File file = new File(destination);
            file.createNewFile();
            PrintWriter w = new PrintWriter(file);

            w.write(data);

            w.flush();
            w.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void appendCSVLine(String fileName, long imageSize, float elapsedTimeMillis) {
        sb.append(fileName);
        sb.append(',');
        sb.append(imageSize);
        sb.append(',');
        sb.append(String.format("%.2f",elapsedTimeMillis));
        sb.append('\n');
    }

    public void closeCSVFile() {
        if (writerCSV == null)
            return;

        writerCSV.write(sb.toString());

        writerCSV.flush();
        writerCSV.close();

    }
}
