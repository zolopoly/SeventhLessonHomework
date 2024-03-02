package com.msaggik.seventhlessonhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // поля
    private TextView output;
    private SensorManager sensorManager; // менеджер сенсоров устройства
    private Sensor sensorProximity; // сенсоры датчика приближения

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка разметки к полям
        output = findViewById(R.id.output);

        // инициализация сенсор менеджера (получение доступа к управлению сенсорами)
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // инициализация сенсоров
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    // создание слушателя для сенсоров
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        // обработчик события (вызывается всякий раз при измерении показаний сенсора)
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            output.setText("Расстояние до приближающегося объекта " + String.format("%.2f", sensorEvent.values[0]));
            Log.i("sensorProximity", "onSensorChanged: " + String.format("%.2f", sensorEvent.values[0]));
        }

        // метод onAccuracyChanged() вызывается при изменении точности показаний датчика
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // регистрация сенсоров (задание слушателя)
        sensorManager.registerListener(sensorEventListener, sensorProximity, SensorManager.SENSOR_DELAY_UI); // (слушатель, сенсор, время обновления - среднее)
    }

    @Override
    protected void onPause() {
        super.onPause();
        // отзыв регистрации сенсоров (отключение слушателя)
        sensorManager.unregisterListener(sensorEventListener);
    }
}