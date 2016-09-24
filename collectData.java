package com.example.cody.homework1;

/**
 * Created by Cody on 9/22/2016.
 */

import java.util.concurrent.BlockingQueue;

public class collectData extends Thread {
    private BlockingQueue<putData> sharedQueue;
    private BlockingQueue<putData> writingQueue;
    private boolean stop = false;

    public collectData(BlockingQueue<putData> sensorDataBuffer, BlockingQueue<putData> writingDataQueue) {
        sharedQueue = sensorDataBuffer;
        writingQueue = writingDataQueue;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                writingQueue.put(sharedQueue.take());
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void doStop(){
        stop = true;
    }
}

