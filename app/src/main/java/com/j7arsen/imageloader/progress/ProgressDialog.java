package com.j7arsen.imageloader.progress;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j7arsen.imageloader.R;
import com.j7arsen.imageloader.error.ErrorHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by arsen on 21.03.17.
 */

public class ProgressDialog extends DialogFragment {

    public static final String TAG = "ProgressDialog.TAG";

    @BindView(R.id.rl_dialog_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.pb_dialog_load)
    ProgressBar pbLoad;
    @BindView(R.id.ll_dialog_progress_error)
    LinearLayout llError;
    @BindView(R.id.tv_dialog_progress_error)
    TextView tvError;

    private Unbinder mUnbinder;

    private int mErrorCode;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_progress, null);
        mUnbinder = ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialog);
        builder.setView(view);
        return builder.create();
    }

    @OnClick(R.id.btn_dialog_progress_ok)
    void okClick() {
        ProgressDialogManager.getInstance().unsubscribe();
    }

    public void startLoading() {
        setCancelable(false);
    }

    public void completeLoading() {
        setCancelable(true);
        rlProgress.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        pbLoad.setVisibility(View.GONE);
    }

    public void errorLoading(ErrorHandler errorHandler) {
        tvError.setText(errorHandler.getMessage());
        mErrorCode = errorHandler.getErrorCode();
        setCancelable(true);
        rlProgress.setVisibility(View.VISIBLE);
        llError.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
