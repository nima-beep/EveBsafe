package com.project.evebsafe.menuoptions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pallob.lib.PatternLockView;
import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Linkers.CancelListener;
import com.project.evebsafe.R;

public class PatternConfirm extends Fragment implements View.OnClickListener{

    View view;
    String passedstring="",samestring;
    Context context;
    PatternLockView patternLockView;
    Button next,cancel;
    SharedPreference  preference;

    CancelListener cancelListener;
    public PatternConfirm(Context context, String passedstring,CancelListener cancelListener) {
        this.cancelListener=cancelListener;
        this.context=context;
        preference=new SharedPreference(context);
        this.passedstring=passedstring;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.confirmpattern,container,false);
        patternLockView=view.findViewById(R.id.setpatternlockview);
        next=view.findViewById(R.id.setpatternnnext);
        cancel=view.findViewById(R.id.setpatteerncancel);
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        patternLockView.setListener(new PatternLockView.Listenser() {
            @Override
            public void onProgress(String digit) {

            }

            @Override
            public void onFinish(String pattern) {
               samestring=pattern;

            }
        });


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setpatternnnext:
                if(passedstring.equals(samestring))
                {
                    preference.savePattern(samestring);
                    Toast.makeText(context, "Successfully saved", Toast.LENGTH_SHORT).show();
                    cancelListener.onComplete();


                }
                else
                {
                    Toast.makeText(context, "Pattern doesn't matched", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.setpatteerncancel:
                    cancelListener.onComplete();
                break;

        }

    }
}
