package com.example.learningelectricityforbagrut;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;


public class imageSourceFragment extends DialogFragment {
    Bundle result= new Bundle();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("מקור התמונה");
        builder.setPositiveButton("גלריה", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                result.putCharSequence("source","gallery");
                getParentFragmentManager().setFragmentResult("image source",result);
            }
        });
        builder.setNeutralButton("מצלמה", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result.putCharSequence("source","camera");
                getParentFragmentManager().setFragmentResult("image source",result);
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
    public static String TAG = "source for image";
}
