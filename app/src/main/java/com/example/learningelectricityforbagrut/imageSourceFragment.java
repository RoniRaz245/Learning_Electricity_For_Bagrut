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
        builder.setTitle(getString(R.string.image_source));
        builder.setPositiveButton(getString(R.string.gallery), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                result.putCharSequence(getString(R.string.source),getString(R.string.gallery_english));
                getParentFragmentManager().setFragmentResult(getString(R.string.image_source_english),result);
            }
        });
        builder.setNeutralButton(getString(R.string.camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result.putCharSequence(getString(R.string.source),getString(R.string.camera_english));
                getParentFragmentManager().setFragmentResult(getString(R.string.image_source_english),result);
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
    public static String TAG = "ImageSourceDialog";
}
