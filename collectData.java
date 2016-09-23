package com.example.cody.homework1;

/**
 * Created by Cody on 9/22/2016.
 */

import java.util.concurrent.BlockingQueue;

public class collectData implements Runnable {
    private BlockingQueue<putData> sharedQueue;
    private BlockingQueue<putData> writingQueue;

    public collectData(BlockingQueue<putData> sensorDataBuffer, BlockingQueue<putData> writingDataQueue) {
        sharedQueue = sensorDataBuffer;
        writingQueue = writingDataQueue;
    }

    @Override
    public void run() {
        try {
            writingQueue.put(sharedQueue.take());
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
