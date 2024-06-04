package com.example.learningelectricityforbagrut;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;

public class getEmail extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_get_email, null);
        builder.setView(dialogView);

        EditText emailView=dialogView.findViewById(R.id.emailText);

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.sendPswrdReset), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get email from user and send password reset email
                        String email=emailView.getText().toString();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.sendPasswordResetEmail(email);
                        dialog.cancel();
                    }
                }).setCancelable(false);
        // Create the AlertDialog object and return it.
        return builder.create();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
