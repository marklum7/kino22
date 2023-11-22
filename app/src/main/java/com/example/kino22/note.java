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


public class note extends AppCompatActivity {
    String Position;

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


        Position = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_POSITION);
        // изменение фрагмента в зависимости от нажатой кнопки
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainerView);
        if(MainActivity.raspoloz.equals("default")){
            frameLayout.setTransitionName("com.example.kino22.frahment_note_edit");
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
            // изменение фрагмента в зависимости от нажатой кнопки
        }
        if (MainActivity.raspoloz.equals("Custom")){
            frameLayout.setTransitionName("com.example.kino22.frahment_note_edit2");
            frahment_note_edit2 fragment2 = new frahment_note_edit2();
            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.KEY_NAME,name);
            bundle.putString(MainActivity.KEY_INFO,info);
            bundle.putString(MainActivity.KEY_COMM,comm);
            bundle.putString(MainActivity.KEY_IMAGE,imag);
            fragment2.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment2)
                    .commit();
        }


    }
    // данные в бд
   public void BackData(String name,String info,String comm)
   {
       Intent returnIntent = new Intent();
       returnIntent.putExtra(MainActivity.KEY_NAME,name);
       returnIntent.putExtra(MainActivity.KEY_INFO,info);
       returnIntent.putExtra(MainActivity.KEY_COMM,comm);
       returnIntent.putExtra(MainActivity.KEY_POSITION, Integer.valueOf(Position));
       setResult(RESULT_OK,returnIntent);
       finish();
   }
}