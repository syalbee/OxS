package com.ocdxsunnah.oxs.Views;

import androidx.appcompat.app.AppCompatActivity;

import com.ocdxsunnah.oxs.Models.ImsakModels;
import com.ocdxsunnah.oxs.R;
import com.ocdxsunnah.oxs.Receiver.AlarmAkhirReceiver;
import com.ocdxsunnah.oxs.Receiver.AlarmAwalReceiver;
import com.ocdxsunnah.oxs.Receiver.AlarmSahurReceiver;
import com.ocdxsunnah.oxs.Receiver.NotifAkhirReceiver;
import com.ocdxsunnah.oxs.Receiver.NotifAwalReceiver;
import com.ocdxsunnah.oxs.Retrofit.ApiService;
import com.ocdxsunnah.oxs.Service.AlarmAkhirService;
import com.ocdxsunnah.oxs.Service.AlarmAwalService;
import com.ocdxsunnah.oxs.Service.AlarmSahurService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    Switch nf, am, as;
    TextView waktuText;
    int jamSahur, menitSahur;
    String mode;


    private void getDataFromApi() {
        ApiService.endpoint().getData()
                .enqueue(new Callback<ImsakModels>() {
                    @Override
                    public void onResponse(Call<ImsakModels> call, Response<ImsakModels> response) {
                        if (response.isSuccessful()) {
                            ArrayList<ImsakModels.Times> time = response.body().getResults().getDatetime();
                            String hour = time.get(0).getTimes().getImsak().substring(0, 2);
                            String minute = time.get(0).getTimes().getImsak().substring(3);

                            jamSahur = Integer.parseInt(hour) - 1;
                            menitSahur = Integer.parseInt(minute);
                        }
                    }

                    @Override
                    public void onFailure(Call<ImsakModels> call, Throwable t) {
                        Log.d("SettingActivity", t.toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        nf = (Switch) findViewById(R.id.notif);
        am = (Switch) findViewById(R.id.alarmMakan);
        as = (Switch) findViewById(R.id.alarmSahur);
        waktuText = (TextView) findViewById(R.id.textSahur);

        saveState();
        getDataFromApi();

        final int hour = 12;

        //dari firebase
        final int endhour = 16;
        mode = "ocd";

        stopService();


        if (mode.equalsIgnoreCase("kolab")) {
            as.setVisibility(View.INVISIBLE);
            waktuText.setVisibility(View.INVISIBLE);
        }


        nf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nf.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    SharedPreferences.Editor editor1 = getSharedPreferences("save3", MODE_PRIVATE).edit();
                    editor1.putBoolean("value", true);
                    editor1.apply();
                    SharedPreferences.Editor editor2 = getSharedPreferences("save4", MODE_PRIVATE).edit();
                    editor2.putBoolean("value", true);
                    editor2.apply();
                    nf.setChecked(true);
                    notifOn();
                    startNotif(hour);
                    startNotifAkhir(endhour);
                } else {
                    notifOff();
                    cancelAlarm(1);
                    cancelAlarm(2);
                    cancelAlarm(3);
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    SharedPreferences.Editor editor1 = getSharedPreferences("save1", MODE_PRIVATE).edit();
                    editor1.putBoolean("value", false);
                    SharedPreferences.Editor editor2 = getSharedPreferences("save2", MODE_PRIVATE).edit();
                    editor2.putBoolean("value", false);
                    SharedPreferences.Editor editor3 = getSharedPreferences("save3", MODE_PRIVATE).edit();
                    editor3.putBoolean("value", false);
                    SharedPreferences.Editor editor4 = getSharedPreferences("save4", MODE_PRIVATE).edit();
                    editor4.putBoolean("value", false);
                    editor.apply();
                    editor1.apply();
                    editor2.apply();
                    editor3.apply();
                    editor4.apply();
                    nf.setChecked(false);
                }
            }
        });

        am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (am.isChecked()) {
                    cancelAlarm(hour);
                    startAlarm(hour);
                    startNotifAlarm(hour);
                    startAlarmAkhir(endhour);
                    startNotifAlarmAkhir(endhour);

                    SharedPreferences.Editor editor = getSharedPreferences("save1", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    am.setChecked(true);
                } else {
                    cancelAlarm(2);
                    SharedPreferences.Editor editor = getSharedPreferences("save1", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    am.setChecked(false);
                    startNotif(hour);
                    startNotifAkhir(endhour);
                }
            }
        });

        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (as.isChecked()) {
                    startSahur(jamSahur, menitSahur);
                    startNotifSahur(jamSahur, menitSahur);

                    SharedPreferences.Editor editor = getSharedPreferences("save2", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    as.setChecked(true);
                } else {
                    cancelAlarm(3);
                    SharedPreferences.Editor editor = getSharedPreferences("save2", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    as.setChecked(false);
                }
            }
        });

    }

    private void startNotif(int time) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotifAwalReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifAkhir(int time) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotifAkhirReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startAlarm(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAwalService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifAlarm(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAwalReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startAlarmAkhir(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAkhirService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 3, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifAlarmAkhir(int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmAkhirReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 4, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startSahur(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour - 1);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmSahurService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 5, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void startNotifSahur(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour - 1);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmSahurReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 6, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }

    private void cancelAlarm(int no) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (no == 1) {
            Intent intent = new Intent(this, NotifAwalReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            alarmManager.cancel(pendingIntent);

            Intent intent1 = new Intent(this, NotifAkhirReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 2, intent1, 0);
            alarmManager.cancel(pendingIntent1);
        } else if (no == 2) {
            Intent intent = new Intent(this, AlarmAwalService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, 0);
            alarmManager.cancel(pendingIntent);

            Intent intent1 = new Intent(this, AlarmAwalReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 2, intent1, 0);
            alarmManager.cancel(pendingIntent1);

            Intent intent2 = new Intent(this, AlarmAkhirService.class);
            PendingIntent pendingIntent2 = PendingIntent.getService(this, 3, intent2, 0);
            alarmManager.cancel(pendingIntent2);

            Intent intent3 = new Intent(this, AlarmAkhirReceiver.class);
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 4, intent3, 0);
            alarmManager.cancel(pendingIntent3);
        } else if (no == 3) {
            Intent intent = new Intent(this, AlarmSahurService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 5, intent, 0);
            alarmManager.cancel(pendingIntent);

            Intent intent1 = new Intent(this, AlarmSahurReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 6, intent1, 0);
            alarmManager.cancel(pendingIntent1);
        }
    }

    private void notifOn() {
        am.setEnabled(true);
        as.setEnabled(true);
    }

    private void notifOff() {
        am.setEnabled(false);
        as.setEnabled(false);
        am.setChecked(false);
        as.setChecked(false);
    }

    private void stopService() {
        stopService(new Intent(this, AlarmAwalService.class));
        stopService(new Intent(this, AlarmSahurService.class));
        stopService(new Intent(this, AlarmAkhirService.class));
    }

    private void saveState() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        nf.setChecked(sharedPreferences.getBoolean("value", false));
        SharedPreferences sharedPreferences1 = getSharedPreferences("save1", MODE_PRIVATE);
        am.setChecked(sharedPreferences1.getBoolean("value", false));
        SharedPreferences sharedPreferences2 = getSharedPreferences("save2", MODE_PRIVATE);
        as.setChecked(sharedPreferences2.getBoolean("value", false));

        SharedPreferences sharedPreferences3 = getSharedPreferences("save3", MODE_PRIVATE);
        am.setEnabled(sharedPreferences3.getBoolean("value", false));
        SharedPreferences sharedPreferences4 = getSharedPreferences("save4", MODE_PRIVATE);
        as.setEnabled(sharedPreferences4.getBoolean("value", false));
    }
}