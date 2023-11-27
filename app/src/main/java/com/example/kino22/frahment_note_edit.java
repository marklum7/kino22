package com.example.kino22;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frahment_note_edit#newInstance} factory method to
 * create an instance of this fragment.note
 */
public class frahment_note_edit extends Fragment {
    TextView nameEditText;
    EditText infoEditText;
    EditText commEditText;
    ImageView imageView;
    Map<String,Integer> ImagesList = new HashMap<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frahment_note_edit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frahment_note_edit.
     */
    // TODO: Rename and change types and number of parameters
    public static frahment_note_edit newInstance(String param1, String param2) {
        frahment_note_edit fragment = new frahment_note_edit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vview = inflater.inflate(R.layout.fragment_note_edit, container, false);
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            //добавление картинок для отображения активити
            ImagesList.put("Sci-fi thrille", R.drawable.inc);
            ImagesList.put("Mystery and suspense", R.drawable.stranger);
            ImagesList.put("Classic crime drama", R.drawable.the);
            ImagesList.put("plus", R.drawable.plus);
            ImagesList.put("kuhn", R.drawable.kuhn);
            ImagesList.put("volk", R.drawable.volk);
            ImagesList.put("ataka", R.drawable.ataka);
            ImagesList.put("kavkaz", R.drawable.kavkaz);
            //создание переменных в которые передоются аргументы
            String noteName = bundle.getString(MainActivity.KEY_NAME);
            String noteInfo = bundle.getString(MainActivity.KEY_INFO);
            String noteComm = bundle.getString(MainActivity.KEY_COMM);
            String image = bundle.getString(MainActivity.KEY_IMAGE);
            //заполнение элементов
            commEditText = vview.findViewById(R.id.commEditText);
            commEditText.setText(noteComm);

            infoEditText = vview.findViewById(R.id.infoEditText);
            infoEditText.setText(noteInfo);

            nameEditText = vview.findViewById(R.id.nameEditText);
            nameEditText.setText(noteName);

            imageView = vview.findViewById(R.id.imageView);

            // выбор правильной картинки
            Integer id = ImagesList.get(image);
            if (id == null){
                imageView.setImageResource(R.drawable.inc);
            }else
                imageView.setImageResource(id);


           //сохранения данных по нажатию кнопки
           ImageButton backButton = vview.findViewById(R.id.imageButton);
           backButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   note activity = (note) getActivity();
                   if(activity != null)
                   {
                       activity.BackData(nameEditText.getText().toString(), infoEditText.getText().toString(), commEditText.getText().toString(), image);
                   }
               }
           });
        }
        return vview;
    }
}