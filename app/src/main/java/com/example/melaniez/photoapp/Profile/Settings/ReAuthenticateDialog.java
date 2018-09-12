package com.example.melaniez.photoapp.Profile.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.melaniez.photoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReAuthenticateDialog extends DialogFragment {
    private static final String TAG = "ReAuthenticateDialog";

    @BindView(R.id.password) EditText mPassword;
    @OnClick(R.id.cancel_button) public void onClickCancel() {
        dismiss();
    }
    @OnClick(R.id.confirm_button) public void onClickConfirm() {
        mOnConfirmButtonClickedListener.onConfirmButtonClicked(mPassword.getText().toString());
        dismiss();
    }

    private OnConfirmButtonClickedListener mOnConfirmButtonClickedListener;
    public interface OnConfirmButtonClickedListener {
        void onConfirmButtonClicked(String password);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_re_authenticate, null);
        ButterKnife.bind(this, view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Re-authentication")
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnConfirmButtonClickedListener = (OnConfirmButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            Log.w(TAG, "onAttach: ClassCastException = "+ e.getMessage());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
    }
}
