package com.project.evebsafe.Adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.evebsafe.Database.UserInfo;
import com.project.evebsafe.Dialogboxes.Details;
import com.project.evebsafe.Linkers.DeleteHandeller;
import com.project.evebsafe.R;

import java.util.ArrayList;

public class CustomListViewAdapters extends ArrayAdapter<String> {
    int array[]={R.drawable.frnd1,R.drawable.pro1,R.drawable.pro2,R.drawable.frnd2};
    ArrayList<String>Name,Number,Profilepic;
    Context context;
    DeleteHandeller deleteHandeller;
    View view;
    UserInfo userInfo;
    Details details;
    TextView profilepic,name,number,delete;
    public CustomListViewAdapters(Context context, ArrayList<String> Name, ArrayList<String> Number, ArrayList<String> Profilepic, DeleteHandeller deleteHandeller, UserInfo userInfo) {
        super(context, R.layout.singleview,Name);
        this.context=context;
        this.Name=Name;
        this.Number=Number;
        this.Profilepic=Profilepic;
        this.deleteHandeller=deleteHandeller;
        this.userInfo=userInfo;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        view= LayoutInflater.from(context).inflate(R.layout.singleview,parent,false);
        profilepic=view.findViewById(R.id.profilepic);
        name=view.findViewById(R.id.nameoffriend);
        number=view.findViewById(R.id.phonenumberfriend);
        delete=view.findViewById(R.id.deleteId);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delayFunction(position);

            }
        });
        name.setText(Name.get(position));
        number.setText(Number.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details=new Details(context,Number.get(position),userInfo);
            }
        });
        switch (Profilepic.get(position)){
            case "0":
                profilepic.setBackgroundResource(array[0]);
                break;

            case "1":
                profilepic.setBackgroundResource(array[1]);
                break;
            case "2":
                profilepic.setBackgroundResource(array[2]);
                break;
            case "3":
                profilepic.setBackgroundResource(array[3]);
                break;

            default:
                    break;


        }




        return view;
    }

    @Override
    public int getCount() {

        return Name.size();
    }

    public void delayFunction(final int position)
    {
        CountDownTimer countDownTimer=new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                userInfo.DeleteSingleUser(Number.get(position));
                deleteHandeller.delete();

            }
        }.start();
    }
}
