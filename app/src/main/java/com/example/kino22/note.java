package com.example.kino22;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class note extends AppCompatActivity {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        // сохранение данных в переменные
        Intent fromMainActivityIntent = getIntent();
        String info = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_INFO);
        String comm = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_COMM);
        String name = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_NAME);
        String imag = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_IMAGE);
        // изменение фрагмента в зависимости от нажатой кнопки
            frahment_note_edit fragment = new frahment_note_edit();
            Bundle bundle = new Bundle();
            //передача данных во фрагмент
            bundle.putString(MainActivity.KEY_NAME,name);
            bundle.putString(MainActivity.KEY_INFO,info);
            bundle.putString(MainActivity.KEY_COMM,comm);
            bundle.putString(MainActivity.KEY_IMAGE,imag);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .commit();
        }

    class alex implements Runnable {
        String connectionError = null;
        String name;
        String info;
        String comm;
        String image;
        Boolean DoDelete;
        @Override
        public void run() {
            try {
                postReq postReq = new postReq();
                final String SERVICE_ADDRESS = "http://37.77.105.18/api/EntertainmentList";
                if (DoDelete){
                    postReq.doInBackground(SERVICE_ADDRESS,name, info, comm, image);
                }else {
                    postReq.DELETE(SERVICE_ADDRESS,name);
                    DoDelete = true;
                }
            } catch (Exception ex) {
                connectionError = ex.getMessage();
            }
        }

    }
   public void BackData(String name,String info,String comm, String image)
   {
       Intent returnIntent = new Intent();
       returnIntent.putExtra(MainActivity.KEY_NAME,name);
       returnIntent.putExtra(MainActivity.KEY_INFO,info);
       returnIntent.putExtra(MainActivity.KEY_COMM,comm);
       returnIntent.putExtra(MainActivity.KEY_IMAGE, image);
       setResult(RESULT_OK,returnIntent);
       note.alex progressTask = new note.alex();
       progressTask.name = name;
       progressTask.info = info;
       progressTask.comm = comm;
       progressTask.image = image;
       progressTask.DoDelete = true;
       executorService.submit(progressTask);
       progressTask.DoDelete = false;
       executorService.submit(progressTask);
       finish();
   }

}