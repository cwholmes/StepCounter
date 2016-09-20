package com.example.rtalukder.hw1;

import java.util.ArrayList;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements SensorEventListener ,
        OnClickListener {
    private SensorManager _sensorManager;
    private Button btnStart, btnStop;
    private boolean started = false;
    private ArrayList sensorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorData = new ArrayList();

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started == true){
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

            putData data = new putData(timestamp, "A", x, y, z);
            sensorData.add(data);
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            long timestamp = event.timestamp;

            putData data = new putData(timestamp, "G", x, y, z);
            sensorData.add(data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                sensorData = new ArrayList();
                // save prev data if available]
                started = true;

                // acceleration sensor initialization
                Sensor accel = _sensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                // gyroscope sensor initialization
                Sensor gyro = _sensorManager
                        .getDefaultSensor(Sensor.TYPE_GYROSCOPE);

                // acceleration sensorManager
                _sensorManager.registerListener(this, accel,
                        SensorManager.SENSOR_DELAY_FASTEST);

                // gyroscope sensorManager
                _sensorManager.registerListener(this, gyro,
                        SensorManager.SENSOR_DELAY_FASTEST);
                break;
            case R.id.btnStop:
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                started = false;
                break;
            default:
                break;
        }

    }

    /** Called when the user clicks the Send button */
    /** public void startWalk(View view) {
     Intent intent = new Intent(this, sensorRun.class);
     startActivity(intent);
     }**/
}
