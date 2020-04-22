package com.project.evebsafe.menuoptions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.evebsafe.Adapters.CustomAdapters;
import com.project.evebsafe.Adapters.DisplayPicAdapters;
import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Database.UserInfo;
import com.project.evebsafe.Linkers.Backtrack;
import com.project.evebsafe.R;

public class SetNumber extends Fragment implements View.OnClickListener {

    View view;
    Context context;
    EditText editText1,editText2,editText3;
    TextView textView;
    Button button1,button2;
    Spinner spinner1,spinner2,spinner3;
   CustomAdapters adapter2, adapter3;
   DisplayPicAdapters displayPicAdapters;
    Backtrack backtrack;
    UserInfo userInfo;
    String occupation[]={"Choose Occupation","Student","Bussinessman","Doctor","Service Holder"};
    String gender[]={"Choose Gender","Male","Female"};
    String displaypic[]={"Choose Picture","M1","F1","M2","F2"};
    String oc="",ge="",dis="";
    public SetNumber (Context context, Backtrack backtrack) {
        this.context=context;
         this.backtrack=backtrack;
         userInfo=new UserInfo(context);




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

        displayPicAdapters=new DisplayPicAdapters(getContext(),R.layout.displayitems,displaypic);
        displayPicAdapters.setDropDownViewResource(R.layout.displayitems);
        spinner1.setAdapter(displayPicAdapters);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               dis=String.valueOf(position-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        adapter2=new CustomAdapters(getContext(),R.layout.spinneritems,occupation);
        adapter2.setDropDownViewResource(R.layout.spinneritems);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oc=occupation[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter3=new CustomAdapters(getContext(),R.layout.spinneritems,gender);
        adapter3.setDropDownViewResource(R.layout.spinneritems);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ge=gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.saveNumber:
                    Checker();

                break;

            case R.id.CancelNumber:
                backtrack.onCancel();
                break;






        }


    }
    public void Checker()
    {
        if(editText1.getText().toString().isEmpty() )
        {
        Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(editText2.getText().toString().isEmpty() )
            {
                Toast.makeText(context, "Number is empty", Toast.LENGTH_SHORT).show();

            }
            else

            {

                if(editText3.getText().toString().isEmpty() )
                {
                    Toast.makeText(context, "Address is empty", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(dis.equals("-1"))
                    {
                        Toast.makeText(context, "Choose a picture", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {

                        if(oc.equals("Choose Occupation"))
                        {
                            Toast.makeText(context, "Choose a occupation", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            if(ge.equals("Choose Gender"))
                            {
                                Toast.makeText(context, "Choose a gender", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                String S1,S2,S3;
                                S1=editText1.getText().toString();
                                S2=editText2.getText().toString();
                                S3=editText3.getText().toString();
                               if(userInfo.insert(S1,S2,S3,dis,oc,ge))
                               {
                                   Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                               }
                               else
                               {
                                   Toast.makeText(context, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                               }
                            }
                        }

                    }

                }

            }
        }




    }



}
