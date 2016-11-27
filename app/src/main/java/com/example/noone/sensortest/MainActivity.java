package com.example.noone.sensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by NoOne on 2016/11/27.
 */

public class MainActivity extends Activity implements SensorEventListener{
    private static final String TAG = "Sensor";
    private SensorManager mSensorManager;
    private float[] gyroData;
    private float angle = 0f;
    private float internal = 0.02f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
    private void registerSensor() {
        Sensor gyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    private void unregisterSensor() {
        mSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerSensor();
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensor();
    }
    @Override
    public void onSensorChanged(final SensorEvent event) {
        int type = event.sensor.getType();
        switch (type) {
            case Sensor.TYPE_GYROSCOPE:
                gyroData = event.values.clone();
                Log.e(TAG, "gyro " + gyroData[0] + " " + gyroData[1] + " " + gyroData[2]);
                angle += gyroData[1] * internal;
                Log.e(TAG, "angle " + Math.toDegrees(angle));
                break;
            default:
                break;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
