package com.project.evebsafe.Dialogboxes;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import com.project.evebsafe.R;

public class LockPattern extends Dialog {
    public LockPattern( Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.patternlockview);
        show();
    }
}
