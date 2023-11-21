package com.example.kino22;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            String noteName = bundle.getString(MainActivity.KEY_NAME);
            String noteInfo = bundle.getString(MainActivity.KEY_INFO);
            String noteComm = bundle.getString(MainActivity.KEY_COMM);
            String imag = bundle.getString(MainActivity.KEY_IMAGE);

            commEditText = view.findViewById(R.id.commEditText);
            commEditText.setText(noteComm);

            infoEditText = view.findViewById(R.id.infoEditText);
            infoEditText.setText(noteInfo);

            nameEditText = view.findViewById(R.id.nameEditText);
            nameEditText.setText(noteName);



           //Button backButton = view.findViewById(R.id.imageButton);
           //backButton.setOnClickListener(new View.OnClickListener() {
           //    @Override
           //    public void onClick(View view) {
           //        note activity = (note) getActivity();
           //        if(activity != null)
           //        {
           //            activity.BackData(infoEditText.getText().toString(),nameEditText.getText().toString(),commEditText.getText().toString());
           //        }
           //    }
           //});
        }
        return view;
    }

}