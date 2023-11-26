package com.example.kino22;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final public static String KEY_NAME = "name";
    final public static String KEY_INFO = "info";
    final public static String KEY_COMM = "comm";
    final public static String KEY_IMAGE = "image";
    final public static String KEY_POSITION = "position";

    private  static final int MY_PERMISSIONS_REQUEST_INTERNET = 777;
    private  static final String SERVICE_ADDRESS = "http://37.77.105.18/api/EntertainmentList";

    ListView ThemesListView;

  //  SimpleCursorAdapter noteAdapter;
 //   DataBaseAccessor db;

    static String raspoloz = "default";

    // создание launcher для получения данных из дочерней активити
   // ActivityResultLauncher<Intent> NotesLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
   //         new ActivityResultCallback<ActivityResult>() {
   //             @Override
   //             public void onActivityResult(ActivityResult result) {
   //                 // все ли хорошо при получении данных из дочерней активити?
   //                 if(result.getResultCode() == Activity.RESULT_OK)
   //                 {
   //                     //получить данные
   //                     Intent returnedIntent = result.getData();
   //                     int id = returnedIntent.getIntExtra(KEY_POSITION,-1);
   //                     String name = returnedIntent.getStringExtra(KEY_NAME);
   //                     String info = returnedIntent.getStringExtra(KEY_INFO);
   //                     String comm = returnedIntent.getStringExtra(KEY_COMM);
   //                     //String image = returnedIntent.getStringExtra(KEY_IMAGE);

    //                  //обновить БД и интерфейс
    //                  db.updateNote(id,name,info,comm);
    //                  noteAdapter = AdapterUpdate();
    //              }
//
 //               }
  //          });
    ArrayList<films> filmes;
    ArrayAdapter<String> filmAdapter;
    ServerAccessor serverAccessor = new ServerAccessor(SERVICE_ADDRESS);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // создать аксессор к бд
       // db = new DataBaseAccessor(this);

        setContentView(R.layout.activity_main);
        ThemesListView = findViewById(R.id.ListView);

       // noteAdapter = AdapterUpdate();
        filmAdapter = AdapterUpdate(new ArrayList<films>());
        Intent NoteIntent = new Intent(this, note.class);

   //    // обработка клика по listView
   //    ThemesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
   //        @Override
   //        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
   //        {
   //            //Добыть данные из адаптера

   //            String name = ((Cursor) noteAdapter.getItem(position)).getString(1);
   //            String info = ((Cursor) noteAdapter.getItem(position)).getString(2);
   //            String comm = ((Cursor) noteAdapter.getItem(position)).getString(3);
   //            String image = ((Cursor) noteAdapter.getItem(position)).getString(4);
   //            //отправить данные в дочернюю акливити
   //            NoteIntent.putExtra(KEY_NAME, name);
   //            NoteIntent.putExtra(KEY_INFO, info);
   //            NoteIntent.putExtra(KEY_COMM, comm);
   //            NoteIntent.putExtra(KEY_IMAGE, image);


   //            //id - идентификатор записи в БД
   //            //без приведения к int перидется и получать long а я не хотел переписывать дочернюю активити
   //            NoteIntent.putExtra(KEY_POSITION,String.valueOf((int) id));

   //            //запустить дочернюю активити
   //            NotesLauncher.launch(NoteIntent);
   //        }
   //    });
   //}
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросить его у пользователя
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        }

        //Запуск фоновой задачи
        ProgressTask progressTask = new ProgressTask();
        executorService.submit(progressTask);





    /**
     * Обновляет listView путем установки нового адаптера
     * @return Адаптер для обновления listView
     */
   // private SimpleCursorAdapter AdapterUpdate() {
   //     // получить адаптер из класса
   //     SimpleCursorAdapter adapter = db.getCursorAdapter(this,
   //             android.R.layout.two_line_list_item, // Разметка одного элемента ListView
   //             new int[]{android.R.id.text1,android.R.id.text2}); // текст этого элемента
        private ArrayAdapter<String> AdapterUpdate(ArrayList<films> filmes) {

            ArrayList<String> stringList = serverAccessor.getStringListFromNoteList(filmes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    stringList);

        // установить адаптер в listview
        ThemesListView.setAdapter(adapter);
        return adapter;
    }

  // @Override
  // protected void onDestroy() {
  //     super.onDestroy();
  //     // закрыть БД
  //     db.close();
  // }
  // // выбор режима отображеня

  // public void Default(View view) {
  //     raspoloz = "default";
  //     System.out.println(raspoloz);
  // }

  // public void Custom(View view) {
  //     raspoloz = "Custom";
  //     System.out.println(raspoloz);
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