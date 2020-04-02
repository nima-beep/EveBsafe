package com.project.evebsafe.menuoptions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pallob.lib.PatternLockView;
import com.project.evebsafe.Linkers.Backtrack;
import com.project.evebsafe.R;

public class PatternSet  extends Fragment implements View.OnClickListener {

    View view;
    String samestring="";
    Backtrack backtrack;
    Context context;
    PatternLockView patternLockView;
    Button next,cancel;
    public PatternSet(Context context, Backtrack backtrack) {
        this.context=context;
        this.backtrack=backtrack;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.setpattern,container,false);
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
                backtrack.onNext(samestring);
                break;
            case R.id.setpatteerncancel:
                backtrack.onCancel();
                break;

        }

    }
}
