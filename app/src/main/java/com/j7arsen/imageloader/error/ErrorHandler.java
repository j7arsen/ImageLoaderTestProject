package com.j7arsen.imageloader.error;

import com.j7arsen.imageloader.R;
import com.j7arsen.imageloader.utils.FieldConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by arsen on 21.03.17.
 */

public class ErrorHandler {

    public static final int UNKNOWN_HOST_EXCEPTION = 500;
    public static final int SOCKET_TIMEOUT_EXCEPTION = 408;
    public static final int DEFAULT_EXCEPTION = 0;

    private static final String MESSAGE = "message";

    private int mErrorCode;
    private String mMessage;


    public ErrorHandler(Throwable throwable) {
        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            mErrorCode = UNKNOWN_HOST_EXCEPTION;
            mMessage = FieldConverter.getString(R.string.message_error_connect);
        } else if (throwable instanceof SocketTimeoutException) {
            mErrorCode = SOCKET_TIMEOUT_EXCEPTION;
            mMessage = FieldConverter.getString((R.string.message_error_socket));
        } else if (throwable instanceof HttpException) {
            mErrorCode = ((HttpException) throwable).code();
            try {
                String json = ((HttpException) throwable).response().errorBody().string();
                JSONObject object = new JSONObject(json);
                if (object.has(MESSAGE) && !object.isNull(MESSAGE)) {
                    mMessage = object.getString(MESSAGE);
                }else {
                    mMessage = FieldConverter.getString((R.string.message_error_default));
                }
            } catch (IOException | JSONException e) {
                mMessage = FieldConverter.getString((R.string.message_error_default));
            }

        } else if (throwable instanceof SSLException) {
            mErrorCode = DEFAULT_EXCEPTION;
            mMessage = FieldConverter.getString((R.string.message_error_connect));

        } else {
            mErrorCode = DEFAULT_EXCEPTION;
            mMessage = FieldConverter.getString((R.string.message_error_default));
        }
    }

    public ErrorHandler(String error){
        mMessage = error;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
