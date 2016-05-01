package com.lujianzhi.photoalbum.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lujianzhi.photoalbum.R;

/**
 * Created by Lawson on 2016/4/24.
 */
public class MyConfirmDialog extends Dialog {

    private Context context;
    private View popView;
    private TextView negative;
    private TextView positive;
    private TextView confirm_text;

    private IMyClickListener myClickListener;

    public MyConfirmDialog(Context context) {
        super(context, R.style.emptyDialog);
        this.context = context;
        popView = View.inflate(context, R.layout.dialog_confirm, null);
        setContentView(popView);
        initView();
        initClickListener();
    }

    public void setConfirmText(int strRes) {
        confirm_text.setText(strRes);
    }

    private void initView() {
        positive = (TextView) popView.findViewById(R.id.confirm);
        negative = (TextView) popView.findViewById(R.id.cancel);
        confirm_text = (TextView) popView.findViewById(R.id.confirm_text);
    }

    public void setPositiveClickListener(IMyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    private void initClickListener() {

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onClick();
                dismiss();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public interface IMyClickListener {
        void onClick();
    }

}
