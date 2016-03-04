package com.lujianzhi.photoalbum.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Lawson on 2016/2/15.
 */
public class MyException extends Exception {
    public MyException() {
        super();
    }

    public void showExceptionToast(Context context, int exceptionMessageId) {
        Toast.makeText(context, "出错了 : " + getString(context, exceptionMessageId), Toast.LENGTH_SHORT).show();
    }

    private String getString(Context context, int exceptionMessageId) {
        return context.getResources().getString(exceptionMessageId);
    }

}
