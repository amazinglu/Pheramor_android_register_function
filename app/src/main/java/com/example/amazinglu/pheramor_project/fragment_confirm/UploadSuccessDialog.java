package com.example.amazinglu.pheramor_project.fragment_confirm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.amazinglu.pheramor_project.R;

public class UploadSuccessDialog extends android.support.v4.app.DialogFragment {

    public static final String TAG = "upload_success_dialog";

    public static UploadSuccessDialog newInstance() {
        return new UploadSuccessDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.upload_success_message)
                .setPositiveButton(R.string.success_confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        getTargetFragment().onActivityResult(getRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.success_cancel_button, null);
        return builder.create();
    }

    public int getRequestCode() {
        return ConfirmFragment.REQ_CODE_UPLOAD_SUCCESS_CONFIRM;
    }
}
