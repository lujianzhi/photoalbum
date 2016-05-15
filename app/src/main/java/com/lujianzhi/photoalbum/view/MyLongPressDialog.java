package com.lujianzhi.photoalbum.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.utils.MyUtil;

/**
 * Created by Lawson on 2016/4/24.
 */
public class MyLongPressDialog extends Dialog {

    private Context context;
    private View popView;
    private TextView set_cover;
    private TextView delete;

    private IMyClickListener myDeleteListener;
    private IMyClickListener mySetCoverListener;

    public MyLongPressDialog(Context context) {
        super(context, R.style.emptyDialog);
        this.context = context;
        popView = View.inflate(context, R.layout.long_press_menu, null);
        setContentView(popView, new ViewGroup.LayoutParams(MyUtil.getWindowWidth((Activity) context),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        initView();
        initClickListener();
    }

    private void initView() {
        delete = (TextView) popView.findViewById(R.id.delete);
        set_cover = (TextView) popView.findViewById(R.id.set_cover);
        delete.setVisibility(View.GONE);
        set_cover.setVisibility(View.GONE);
    }

    public void setDeleteVisisble() {
        delete.setVisibility(View.VISIBLE);
    }

    public void setCoverVisisble() {
        set_cover.setVisibility(View.VISIBLE);
    }

    public void setDeleteClickListener(IMyClickListener myClickListener) {
        this.myDeleteListener = myClickListener;
    }

    public void setSetCoverClickListener(IMyClickListener mySetCoverListener) {
        this.mySetCoverListener = mySetCoverListener;
    }

    private void initClickListener() {

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDeleteListener.onClick();
                dismiss();
            }
        });

        set_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySetCoverListener.onClick();
                dismiss();
            }
        });

    }

    public interface IMyClickListener {
        void onClick();
    }

}
