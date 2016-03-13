package com.lujianzhi.photoalbum.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lujianzhi.photoalbum.R;

/**
 * Created by lujianzhi on 2016/1/29.
 */
public class MyAddAlbumDialog extends Dialog {

    private Context context;
    private View popView;
    private TextView negative;
    private TextView positive;
    private EditText albumName;
    private RadioGroup radioGroup;
    private int type;

    private IMyClickListener myClickListener;

    public MyAddAlbumDialog(Context context) {
        super(context, R.style.emptyDialog);
        this.context = context;
        popView = View.inflate(context, R.layout.dialog_add_photo_album, null);
        setContentView(popView);
        initView();
        initClickListener();
    }

    private void initView() {
        positive = (TextView) popView.findViewById(R.id.regist);
        negative = (TextView) popView.findViewById(R.id.cancel);
        albumName = (EditText) popView.findViewById(R.id.album_name);
        radioGroup = (RadioGroup) popView.findViewById(R.id.radio_group);
        positive = (TextView) popView.findViewById(R.id.add);
        negative = (TextView) popView.findViewById(R.id.cancel);

        radioGroup.check(R.id.album_private);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.album_public:
                        type = 1;
                        break;
                    case R.id.album_private:
                        type = 0;
                        break;
                }
            }
        });
    }

    public String getAddAlbumName() {
        return albumName.getText().toString();
    }

    public int getAddAlbumType(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.album_public:
                        type = 1;
                        break;
                    case R.id.album_private:
                        type = 0;
                        break;
                }
            }
        });
        return type;
    }

    public void setPositiveClickListener(IMyClickListener myClickListener){
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

    public interface IMyClickListener{
        void onClick();
    }

}
