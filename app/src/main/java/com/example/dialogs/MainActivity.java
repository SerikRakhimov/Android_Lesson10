package com.example.dialogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView datePickerDialogTextView, timePickerDialogTextView;
    private SimpleDateFormat dateFormat, timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        TextView alertDialogTextView = findViewById(R.id.alertDialogTextView);
        alertDialogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogTextView();
            }
        });

        datePickerDialogTextView = findViewById(R.id.datePickerDialogTextView);
        datePickerDialogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        timePickerDialogTextView = findViewById(R.id.timePickerDialogTextView);
        timePickerDialogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog();
            }
        });

        TextView loadTextView = findViewById(R.id.loadTextView);
        loadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProgressDialog();
            }
        });

        TextView customDialogTextView = findViewById(R.id.customDialogTextView);
        customDialogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog();
            }
        });
    }

    private void showAlertDialogTextView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Опрос");
        builder.setMessage("Вы хотите предупреждение?");
        builder.setIcon(R.drawable.ic_message);
        builder.setCancelable(false);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showAlertDialogWarning();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlertDialogWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Предупреждение");
        builder.setMessage("Это диалог для предупреждения");
        builder.setIcon(R.drawable.ic_warning);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDatePickerDialog() {
        java.util.Calendar c = java.util.Calendar.getInstance();

        DatePickerDialog tpd =
                new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);
                                datePickerDialogTextView.setText(dateFormat.format(calendar.getTime()));
                            }
                        },
                        c.get(java.util.Calendar.YEAR),
                        c.get(java.util.Calendar.MONTH),
                        c.get(java.util.Calendar.DAY_OF_MONTH)
                );
        tpd.show();
    }

    private void openTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(0, 0, 0, hourOfDay, minute);
                        timePickerDialogTextView.setText(timeFormat.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void openProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Downloading...");
        dialog.show();
        // Задержка на 3 секунды
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }

    private void openCustomDialog() {
        final int[] i = new int[1];
        View view = LayoutInflater
                .from(MainActivity.this)
                .inflate(R.layout.layout_custom, null);
        i[0] = 0;
        ImageView image = view.findViewById(R.id.dialogImageView);
        TextView text = view.findViewById(R.id.dialogTextView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i[0] = i[0] + 1;
                text.setText(Integer.toString(i[0]));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        builder.setTitle("Custom dialog (нажмите '+1')");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(
                        MainActivity.this,
                        "Количество = " + text.getText(),
                        Toast.LENGTH_LONG
                ).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}