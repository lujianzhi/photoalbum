package com.lujianzhi.photoalbum.net.parser;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseParser {

    protected String parseMessage(String responseStr) {
        String message = "";
        try {
            JSONObject obj = new JSONObject(responseStr);
            if (obj.has("code")) {
                int code = obj.getInt("code");
                if (code == 0) {
                    // 弹出显示错误信息
                    ToastUtils.showShortToast(obj.getString("message"));
                    return "";
                } else if (code == 1) {
                    if (obj.has("message")) {
                        message = obj.getString("message");
                    } else {
                        ToastUtils.showShortToast(R.string.json_message_error);
                    }
                }
            } else {
                ToastUtils.showShortToast(R.string.json_code_error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    protected int parseCode(String responseStr) {
        int code = -1;
        try {
            JSONObject obj = new JSONObject(responseStr);
            if (obj.has("code")) {
                code = obj.getInt("code");
            } else {
                ToastUtils.showShortToast(R.string.json_code_error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

}
