package com.example.kino22;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    final public static String KEY_NAME = "name";
    final public static String KEY_INFO = "info";
    final public static String KEY_COMM = "comm";
    final public static String KEY_IMAGE = "image";
    final public static String KEY_POSITION = "position";
    private  static final int MY_PERMISSIONS_REQUEST_INTERNET = 777;
    private  static final String SERVICE_ADDRESS = "http://37.77.105.18/api/EntertainmentList";
    ListView ThemesListView;

    // создание launcher для получения данных из дочерней активити
    ActivityResultLauncher<Intent> NotesLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // все ли хорошо при получении данных из дочерней активити?
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        //получить данные
                        Intent returnedIntent = result.getData();
                        String name = returnedIntent.getStringExtra(KEY_NAME);
                        String info = returnedIntent.getStringExtra(KEY_INFO);
                        String comm = returnedIntent.getStringExtra(KEY_COMM);
                        String image = returnedIntent.getStringExtra(KEY_IMAGE);

                    }
                }
            });

//

    ArrayList<films> filmes;
    ArrayAdapter<String> filmAdapter;
    ServerAccessor serverAccessor = new ServerAccessor(SERVICE_ADDRESS);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThemesListView = findViewById(R.id.ListView);
        filmAdapter = AdapterUpdate(new ArrayList<films>());
        Intent NoteIntent = new Intent(this, note.class);

    //   // обработка клика по listView
       ThemesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> parent, View v, int position, long id)
           {
               serverAccessor.getObject(filmes.get(position));
               //Добыть данные
               Map<String, String> data = serverAccessor.getObject(filmes.get(position));
               String name = data.get("name");
               String info = data.get("info");
               String comm = data.get("comm");
               String image = data.get("image");
               //отправить данные в дочернюю акливити
               NoteIntent.putExtra(KEY_NAME, name);
               NoteIntent.putExtra(KEY_INFO, info);
               NoteIntent.putExtra(KEY_COMM, comm);
               NoteIntent.putExtra(KEY_IMAGE, image);
               //запустить дочернюю активити
               NotesLauncher.launch(NoteIntent);
           }
       });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросить его у пользователя
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        }

        //Запуск фоновой задачи
        ProgressTask progressTask = new ProgressTask();
        executorService.submit(progressTask);
    }

    private ArrayAdapter<String> AdapterUpdate(ArrayList<films> list) {
        ArrayList<String> stringList = serverAccessor.getStringListFromNoteList(list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,stringList);
        // установить адаптер в listview
        ThemesListView.setAdapter(adapter);
        return adapter;
    }
    class ProgressTask implements Runnable {
        String connectionError = null;
        @Override
        public void run() {
            try {
                // выполнение в фоне
                filmes = serverAccessor.getData();

                // Обновление UI осуществляется в основном потоке
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connectionError == null) {
                            filmAdapter = AdapterUpdate(filmes);
                        } else {
                            //проблемы с интернетом
                        }
                    }
                });
            } catch (Exception ex) {
                connectionError = ex.getMessage();
            }
        }
    }
}