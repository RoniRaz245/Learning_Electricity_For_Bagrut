package com.example.learningelectricityforbagrut;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class infoFragment extends DialogFragment {
    private Dialog alert;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.level_info, null);
        builder.setView(dialogView);

        TextView level1=(TextView)dialogView.findViewById(R.id.level1);
        String pt1=level1.getText().toString();

        TextView level2=(TextView)dialogView.findViewById(R.id.level2);
        String pt2=level2.getText().toString();

        TextView higherLevels=(TextView)dialogView.findViewById(R.id.higherLevels);
        String pt3=higherLevels.getText().toString();
        builder.setMessage(pt1+"\n"+pt2+"\n"+pt3)
                .setNegativeButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog object and return it.
        return builder.create();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
