package com.example.cody.homework1;

/**
 * Created by Cody on 9/20/2016.
 */

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;

public class storeData extends Thread {
    private BlockingQueue<putData> writingQueue;
    private boolean stop = false;
    private Context ctx;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Stepping";

    public storeData (BlockingQueue<putData> writingDataQueue){
        writingQueue = writingDataQueue;
    }

    @Override
    public void run() {
        while (!stop){
            try{
                writeData(writingQueue.take());
            }
            catch (Exception ex){
                Thread.currentThread().interrupt();
            }
        }
    }

    public void doStop(){
        stop = true;
    }

    private void writeData(putData data){
        //File sdCard = Environment.getExternalStoragePublicDirectory("sdcard");
        //File dir = new File (path + "/Stepping");
        //dir.mkdirs();
        File file;
        FileOutputStream fos;
        /*if(!file.exists()){
            try{
                file.createNewFile();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }*/
        String content = "THIS SHOULD WORK BUT IT DOESN'T";
        String filename = "outputFile.txt";
        //FileOutputStream fos = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Stepping");

            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            /*fos = ctx.openFileOutput(filename, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.write(content);
            buf.newLine();
            buf.close();*/
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        /*try{
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
        }*/
    }
}
