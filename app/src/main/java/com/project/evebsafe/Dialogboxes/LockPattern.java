package com.project.evebsafe.Dialogboxes;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import com.pallob.lib.PatternLockView;
import com.project.evebsafe.R;

public class LockPattern extends Dialog {
    PatternLockView lockView;
    Context context;
    String pat="12369";

    public LockPattern( Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        this.context=context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.patternlockview);
        lockView=findViewById(R.id.confirmpatternlockview);
        show();
        lockView.setListener(new PatternLockView.Listenser() {
            @Override
            public void onProgress(String digit) {

            }

            @Override
            public void onFinish(String pattern) {

                Toast.makeText(getContext(), pattern, Toast.LENGTH_SHORT).show();
                if(pattern.equals(pat))
                {
                   dismiss();

                }
                }
        });
    }
}
