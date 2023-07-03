package com.mad.sdcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity{
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;

    protected Button retrieveLocationButton;

//    EditText text, text2;
//    Button buttonWrite, buttonRead, buttonClear, notify;
//    String CHANNEL_ID = "ch1";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        createNotificationChannel();
//
//        text = findViewById(R.id.editText);
//        buttonWrite = findViewById(R.id.button);
//        buttonRead = findViewById(R.id.button2);
//        buttonClear = findViewById(R.id.button3);
//
//
//        buttonWrite.setOnClickListener(this);
//        buttonRead.setOnClickListener(this);
//        buttonClear.setOnClickListener(this);
//
//
//        text2 = findViewById(R.id.editText2);
//        notify = findViewById(R.id.notify);
//
//        notify.setOnClickListener(this);
//
//    }
//
//    public void onClick(View view) {
//        String message= text.getText().toString();
//        String buf="";
//        try {
//            File file = new File("/sdcard/myfile.txt");
//            if (!file.exists())
//                file.createNewFile();
//            if (view == buttonWrite) {
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(message.getBytes());
//                fos.close();
//                Toast.makeText(this, "Data Written to SD Card", Toast.LENGTH_SHORT).show();
//            } else if (view == buttonRead) {
//                FileInputStream fis = new FileInputStream(file);
//                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//                while ((message = br.readLine()) != null) {
//                    buf += message;
//                }
//                text.setText(buf);
//                br.close();
//                fis.close();
//                Toast.makeText(this, "Data Read from SD Card", Toast.LENGTH_SHORT).show();
//
//            } else if (view == buttonClear) {
//                text.setText("");
//            }
//            else if(view == notify){
////                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
////                PendingIntent pending = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
////                Notification noti = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID).setContentTitle("New Message").setContentText(text2.getText().toString()).setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pending).build();
////                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////
////
////                noti.flags |= Notification.FLAG_AUTO_CANCEL;
////                manager.notify(0, noti);
//
////                askForPermissions();
//                Toast.makeText(this, "MOUNTED", Toast.LENGTH_SHORT).show();
//
////                if(isExternalStorageWritable()){
////
////                }
//            }
//        }
//        catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is not in the Support Library.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system. You can't change the importance
//            // or other notification behaviors after this.
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
////    public void askForPermissions() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
////            if (!Environment.isExternalStorageManager()) {
////                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
////                startActivity(intent);
////                return;
////            }
////            createDir();
////        }
////    }
//
////    public void createDir(){
////        if (!dir.exists()){
////            dir.mkdirs();
////        }
////    }
//
//    private boolean isExternalStorageWritable(){
//        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//    }

    EditText e1;
    Button write,read,clear;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button notify;
        EditText e;

        e1= (EditText) findViewById(R.id.editText);
        write= (Button) findViewById(R.id.button);
        read= (Button) findViewById(R.id.button2);
        clear= (Button) findViewById(R.id.button3);


//        setContentView(R.layout.activity_main);

        notify= (Button) findViewById(R.id.notify);
        e= (EditText) findViewById(R.id.editText2);

        notify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                PendingIntent pending = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                Notification noti = new Notification.Builder(MainActivity.this).setContentTitle("New Message").setContentText(e.getText().toString()).setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pending).build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                manager.notify(0, noti);
            }
        });
        write.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String message=e1.getText().toString();
                try
                {

                    boolean mExternalStorageAvailable = false;
                    boolean mExternalStorageWriteable = false;
                    String state = Environment.getExternalStorageState();

                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        // We can read and write the media
                        mExternalStorageAvailable = mExternalStorageWriteable = true;
                    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                        // We can only read the media
                        mExternalStorageAvailable = true;
                        mExternalStorageWriteable = false;
                    } else {
                        // Something else is wrong. It may be one of many other states, but all we need
                        //  to know is we can neither read nor write
                        mExternalStorageAvailable = mExternalStorageWriteable = false;
                    }


                    if(mExternalStorageAvailable && mExternalStorageWriteable){
                        Log.d("path", Environment.getExternalStorageDirectory().toString());
                        FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory() +"/out.txt");
                        writer.write("This\n is\n an\n example\n");
                        writer.flush();
                        writer.close();
                    }
//                    File f=new File("/sdcard/myfile.txt");


                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File (sdCard.getAbsolutePath());
                    dir.mkdirs();
                    File file = new File(dir, "output.txt");
                    if(!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fout = new FileOutputStream(file);

//                    FileOutputStream fout=new FileOutputStream(f);
                    fout.write(message.getBytes());
                    fout.close();
                    Toast.makeText(getBaseContext(),"Data Written in SDCARD",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String message;
                String buf = "";
                try
                {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File (sdCard.getAbsolutePath());
                    dir.mkdirs();
                    File f = new File(dir, "output.txt");

                    FileInputStream fin = new FileInputStream(f);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fin));
                    while ((message = br.readLine()) != null)
                    {
                        buf += message;
                    }
                    e1.setText(buf);
                    br.close();
                    fin.close();
                    Toast.makeText(getBaseContext(),"Data Recived from SDCARD",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                e1.setText("");
            }
        });
    }
}