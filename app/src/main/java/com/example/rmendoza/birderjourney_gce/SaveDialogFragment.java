package com.example.rmendoza.birderjourney_gce;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.logging.Handler;


public class SaveDialogFragment extends DialogFragment {

    String sisrecID = "";
    String commonName = "";
    String currentLat = "";
    String currentLong = "";
    String nearcity = "unknown";

    public interface SaveDialogListener {
        public void onDialogSaveClick(DialogFragment dialog, String note, String sisrecID, String commonName, String currentLat, String currentLong, String nearcity);
    }

    SaveDialogListener mListener;
    private Handler mResponseHandler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SaveDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SaveDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            sisrecID = arguments.getString("sisrecID");
            commonName = arguments.getString("commonName");
            currentLat = arguments.getString("currentLat");
            currentLong = arguments.getString("currentLong");
            nearcity = arguments.getString("nearcity");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupview = inflater.inflate(R.layout.fragment_save_dialog, null);
        final EditText dialog_note =    (EditText)popupview.findViewById(R.id.dialog_note);
        builder.setView(popupview)
                //.setTitle("SAVE OBSERVATION")
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String note = dialog_note.getText().toString();
                        mListener.onDialogSaveClick(SaveDialogFragment.this,note, sisrecID, commonName, currentLat, currentLong, nearcity);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SaveDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
