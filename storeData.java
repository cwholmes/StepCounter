package com.example.cody.homework1;

/**
 * Created by Cody on 9/20/2016.
 */

import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;

public class storeData implements Runnable {
    private BlockingQueue<putData> writingQueue;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Stepping";

    public storeData (BlockingQueue<putData> writingDataQueue){
        writingQueue = writingDataQueue;
    }

    @Override
    public void run() {
        try{
            writeData(writingQueue.take());
        }
        catch (Exception ex){
            Thread.currentThread().interrupt();
        }
    }

    private void writeData(putData data){
        //File sdCard = Environment.getExternalStoragePublicDirectory("sdcard");
        File dir = new File (path + "/Stepping");
        dir.mkdirs();
        File file = new File(Environment.getExternalStoragePublicDirectory(null), "outputFile.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        String content = data.toString();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dir);
            /*BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.write(content);
            buf.newLine();
            buf.close();*/
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            try{
                fos.write(content.getBytes());
                fos.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
