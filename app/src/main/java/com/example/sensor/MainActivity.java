package com.example.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorm;
    private TextView tv_sensor;
    public int[] vedios ={R.raw.aa,R.raw.bb,R.raw.cc,R.raw.dd};//设置音乐 的 数组
    public int index=0;//设置下标位置
    private MediaPlayer mediaPlayer;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        sensorm = (SensorManager) getSystemService(SENSOR_SERVICE);
//        getAllSensor();
//        userOre();//方向传感器
//        userlight();
//        userAccess();//监听加速度传感器
        userAccess2();
    }

    private void userAccess2() {
        player = MediaPlayer.create(MainActivity.this, vedios[index]);//音乐播放器
        player.start();//开始播放
        Sensor userAcc = sensorm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//sensorgetDefa 获取传感器
        sensorm.registerListener(new accLou(),userAcc,SensorManager.SENSOR_DELAY_NORMAL);//注册传感器
    }

    /**
     * 获得加速度传感器
     * 监听器
     * 注册‘
     */
    private void userAccess() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, vedios[index]);
        mediaPlayer.start();
        Sensor accmusin = sensorm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorm.registerListener(new listMuiset(),accmusin,SensorManager.SENSOR_DELAY_NORMAL);
    }


    private void userlight() {
        Sensor Lught = sensorm.getDefaultSensor(Sensor.TYPE_LIGHT);//光线传感器
        sensorm.registerListener(new lightLis(),Lught,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void userOre() {
        //1.获得方向传感器
        Sensor defaultSensor = sensorm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //2.创建传感器的监听器 注册
        sensorm.registerListener(new lis1(), defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);//SENSOR_DELAY_NORMAL时间
    }

    private void getAllSensor() {
        //管理器获取集合

        List<Sensor> sensorList = sensorm.getSensorList(Sensor.TYPE_ALL);//得到所有的传感器7
        for (int i = 0; i < sensorList.size(); i++) {
            Sensor sensor = sensorList.get(i);
            String name = sensor.getName();//传感器名字
            String vendor = sensor.getVendor();//生产厂商
            int version = sensor.getVersion();//版本号
            Log.e("TAG", "onCreate: " + "传感器名字：" + name + "传感器厂商：" + vendor + "传感器版本号:" + version);
        }
    }
    private void initView() {
        tv_sensor = (TextView) findViewById(R.id.tv_sensor);
    }

    /**
     * 方向传感器的监听事件
     */
    private class lis1 implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    /**
     * 光线传感器的监听事件
     */
    private class lightLis implements  SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    /**
     * 光感传感器
     */
    class lisAll implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){//event.sensor.getType（）类型是光线传感器
                float f=event.values[0];
                Log.e("TAG", "onSensorChanged: "+f);
                if(f>600){//屏幕接受光线大于600的时候


                }
                if(f<40){//屏幕接收光线小于40的时候


                }
            }else {
                float left = event.values[0];//方向传感器位置打印
                int a= (int) left;
                tv_sensor.setText(a+" + 亮度");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorm.unregisterListener(new lightLis());
        sensorm.unregisterListener(new listMuiset());
    }

    /**
     * 加速度的传感器
     * 需要创建音乐播放器
     */
     class listMuiset implements  SensorEventListener {



        @Override
         public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                   float x =event.values[0];
                   float y =event.values[1];
                   float z =event.values[2];
                   if(z>14){//z轴大于12切换音乐
                       if(mediaPlayer!=null){
                           mediaPlayer.stop();
                           mediaPlayer.release();
                       }
                       index++;
                       index=index%vedios.length;
                       mediaPlayer = MediaPlayer.create(MainActivity.this,vedios[index]);
                       mediaPlayer.start();

                   }
                }
         }

         @Override
         public void onAccuracyChanged(Sensor sensor, int accuracy) {

         }
     }

    /**
     * 加速度监听器
     */
    private class accLou implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
               float x = event.values[0];
               float y = event.values[1];
               float z = event.values[2];
               if(z>14){//根据z的力度来进行判断
                   if(player !=null){
                       player.stop();//音乐停止
                       player.release();//释放缓存资源
                   }
                    index++;//如果大于14今夏下一首
                   index=index%vedios.length;//循环播放

                   MediaPlayer.create(MainActivity.this,vedios[index]).start();//到下标结束为止时从心开始播放


               }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
