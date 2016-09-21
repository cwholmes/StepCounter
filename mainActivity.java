package com.example.cody.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity implements SensorEventListener ,
        OnClickListener {
    private SensorManager _sensorManager;
    private Button btnStart, btnStop;
    private boolean started = false;
    private BlockingQueue<putData> sensorDataBuffer;
    private Sensor accel, gyro;
    private putData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorDataBuffer = new ArrayBlockingQueue<putData>(30);
        sharedQueuePC = new LinkedBlockingQueue<putData>(30);

        accel = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        gyro = _sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }

    @Override
    protected void onResume(){
        super.onResume();
        _sensorManager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_FASTEST);
                
        _sensorManager.registerListener(this, gyro,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started){
            _sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            long timestamp = event.timestamp;
            
            data = new putData(timestamp, "A", x, y, z);
            sensorDataBuffer.offer(data);
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            long timestamp = event.timestamp;
            
            data = new putData(timestamp, "G", x, y, z);
            sensorDataBuffer.offer(data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                started = true;
                
                _sensorManager.registerListener(this, accel,
                        SensorManager.SENSOR_DELAY_FASTEST);
                _sensorManager.registerListener(this, gyro,
                        SensorManager.SENSOR_DELAY_FASTEST);
                break;
            case R.id.btnStop:
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                started = false;
                Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
    
    public class ProducerConsumerPattern {

        //Creating Producer and Consumer Thread
        Thread prodThread = new Thread(new Producer(sensorDataBuffer));
        Thread consThread = new Thread(new Consumer(sharedQueuePC));

        /*Starting producer and Consumer thread
        prodThread.start()
        consThread.start();
        */
    }

    public class Producer implements Runnable {
        private BlockingQueue<putData> sharedQueue;

        public Producer(BlockingQueue sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sharedQueuePC.put(sharedQueue.take());
                }
                catch (InterruptedException ex) {

                }
            }
        }

    }

    class Consumer implements Runnable {
        private BlockingQueue<putData> writingQueue;
        private String fileName = "outputFile";

        public Consumer(BlockingQueue<putData> sharedQueue) {
            writingQueue = sharedQueue;
        }

        public void run() {
            try {
                while (true) {
                    consume(writingQueue.take());
                }
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        void consume(Object queueItem) {
            String content = queueItem.toString();

            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(content.getBytes());
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /** Called when the user clicks the Send button */
   /** public void startWalk(View view) {
        Intent intent = new Intent(this, sensorRun.class);
        startActivity(intent);
    }**/
    
    /* Creating a directory 

    File mydir = context.getDir("mydir", Context.MODE_PRIVATE); //Creating an internal dir;
    File fileWithinMyDir = new File(mydir, "myfile"); //Getting a file within the dir.
    FileOutputStream out = new FileOutputStream(fileWithinMyDir);


     */
}

