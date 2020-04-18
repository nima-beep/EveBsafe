package com.project.evebsafe.menuoptions;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.R;

public class SetMessage extends Fragment  implements View.OnClickListener{
    View view;
    Context context;
    SharedPreference preference;
    EditText editText;
    TextView textView;
    Button button;

    public SetMessage( Context context) {
        this.context=context;
        preference=new SharedPreference(context);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container , @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.setmessage, container,false);
        button=view.findViewById(R.id.sendtxtid);
        button.setOnClickListener(this);
        editText=view.findViewById(R.id.setmsgid);
        textView=view.findViewById(R.id.savetxtid);

        textView.setText(preference.getMessage());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    textView.setText(editText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
    preference.saveMessage(editText.getText().toString());


    }
}
