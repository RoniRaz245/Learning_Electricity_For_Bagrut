package com.example.learningelectricityforbagrut;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class questionAmountForTest extends DialogFragment {
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(int num);
    }

    NoticeDialogListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface.
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface. Throw exception.
            throw new ClassCastException("must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_question_amount_for_test, null);
        builder.setView(dialogView);

        NumberPicker questionAmount=dialogView.findViewById(R.id.questionAmountPicker);
        questionAmount.setEnabled(true);
        questionAmount.setMinValue(1);
        questionAmount.setMaxValue(20);
        String[] answers=new String[20];
        for(int i=0; i<20; i++)
            answers[i]= String.valueOf(i+1);
        questionAmount.setDisplayedValues(answers);

        builder.setTitle(getString(R.string.question_amount))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
        .setPositiveButton(getString(R.string.start_test), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogPositiveClick(questionAmount.getValue());
                dialog.cancel();
            }
        }).setCancelable(false);
        // Create the AlertDialog object and return it.
        return builder.create();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
