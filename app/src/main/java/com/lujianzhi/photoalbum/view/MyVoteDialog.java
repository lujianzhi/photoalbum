package com.lujianzhi.photoalbum.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lujianzhi.photoalbum.R;

/**
 * Created by Lawson on 2016/4/24.
 */
public class MyVoteDialog extends Dialog {

    private Context context;
    private View popView;
    private TextView negative;
    private TextView positive;
    private RatingBar voteStar;

    private IMyClickListener myClickListener;

    public MyVoteDialog(Context context) {
        super(context, R.style.emptyDialog);
        this.context = context;
        popView = View.inflate(context, R.layout.dialog_vote, null);
        setContentView(popView);
        initView();
        initClickListener();
    }

    public RatingBar getRatingBar() {
        return voteStar;
    }

    private void initView() {
        positive = (TextView) popView.findViewById(R.id.confirm);
        negative = (TextView) popView.findViewById(R.id.cancel);
        voteStar = (RatingBar) popView.findViewById(R.id.voteStar);
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
