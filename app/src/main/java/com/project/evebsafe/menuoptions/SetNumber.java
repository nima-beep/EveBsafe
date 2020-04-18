package com.project.evebsafe.menuoptions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Linkers.Backtrack;
import com.project.evebsafe.R;

public class SetNumber extends Fragment implements View.OnClickListener {

    View view;
    Context context;
    SharedPreference preference;
    EditText editText1,editText2,editText3;
    TextView textView;
    Button button1,button2;
    Spinner spinner1,spinner2,spinner3;
    ArrayAdapter<String> adapter1,adapter2, adapter3;
    Backtrack backtrack;
    String occupation[]={"Student","Bussinessman","Doctor","Service Holder"};
    String gender[]={"Male","Female"};
    String displaypic[]={"M1","F1","M2","F2"};

    public SetNumber (Context context, Backtrack backtrack) {
        this.context=context;
         this.backtrack=backtrack;



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setnumber, container, false);
        editText1=view.findViewById(R.id.nameEdit);
        editText2=view.findViewById(R.id.numberEdit);
        editText3=view.findViewById(R.id.addressEdit);
        button1=view.findViewById(R.id.saveNumber);
        button2=view.findViewById(R.id.CancelNumber);
        spinner1=view.findViewById(R.id.displayPicspinner);
        spinner2=view.findViewById(R.id.occupationspinner);
        spinner3=view.findViewById(R.id.genderspinner);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        adapter1=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,displaypic);
        adapter1.setDropDownViewResource(android. R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setPrompt("Set Picture");



        adapter2=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,occupation);
        adapter2.setDropDownViewResource(android. R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setPrompt("Occupation");




        adapter3=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,gender);
        adapter3.setDropDownViewResource(android. R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setPrompt("Gender");







        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.saveNumber:

                break;

            case R.id.CancelNumber:
                backtrack.onCancel();
                break;






        }


    }
}
