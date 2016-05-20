package com.lujianzhi.photoalbum.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lujianzhi.photoalbum.R;
import com.lujianzhi.photoalbum.config.NetWorkConfig;
import com.lujianzhi.photoalbum.entity.Comment;
import com.lujianzhi.photoalbum.entity.Photo;
import com.lujianzhi.photoalbum.net.PhotoAlbumManager;
import com.lujianzhi.photoalbum.net.networktask.INetWorkListener;
import com.lujianzhi.photoalbum.ui.base.BaseActivity;
import com.lujianzhi.photoalbum.ui.viewpager.HackyViewPager;
import com.lujianzhi.photoalbum.utils.ToastUtils;
import com.lujianzhi.photoalbum.view.MyVoteDialog;
import com.lujianzhi.photoalbum.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujianzhi on 2016/1/22.
 */
public class PhotoActivity extends BaseActivity {
    private final String TAG = PhotoActivity.class.getCanonicalName();

    private List<Photo> photoList = new ArrayList<Photo>();
    private List<Comment> commentList = new ArrayList<>();

    private int photoPosition;
    private HackyViewPager hackyViewPager;
    private ImageView commentImg;
    private LinearLayout comment_area;
    private EditText comment_content;
    private Button commit;
    private RecyclerView commentRecyclerView;
    private LinearLayout bottom;

    private MyCommentAdapter commentAdapter;

    private boolean showComment;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comment:
                showCommentArea();
                break;
            case R.id.vote:
                showVoteArea();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initTopViews() {
    }

    @Override
    protected void initViews() {
        hackyViewPager = (HackyViewPager) findViewById(R.id.photo_area);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        hackyViewPager.setAdapter(adapter);
        hackyViewPager.setCurrentItem(photoPosition);
        hackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                photoPosition = position;
                commentList = photoList.get(position).getComment();
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        commentRecyclerView = (RecyclerView) findViewById(R.id.comment_list);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyCommentAdapter commentAdapter = new MyCommentAdapter();
        commentRecyclerView.setAdapter(commentAdapter);

    }

    @Override
    protected void initBottomViews() {
        bottom = (LinearLayout) findViewById(R.id.bottom);
        ImageView back = (ImageView) bottom.findViewById(R.id.back);
        ImageView add = (ImageView) bottom.findViewById(R.id.add);
        ImageView vote = (ImageView) bottom.findViewById(R.id.vote);
        vote.setVisibility(View.VISIBLE);
        add.setVisibility(View.GONE);
        commentImg = (ImageView) bottom.findViewById(R.id.comment);
        comment_area = (LinearLayout) bottom.findViewById(R.id.comment_area);
        comment_content = (EditText) bottom.findViewById(R.id.comment_content);
        commit = (Button) bottom.findViewById(R.id.commit);
        back.setOnClickListener(getOnClickListener());
        commentImg.setOnClickListener(getOnClickListener());
        vote.setOnClickListener(getOnClickListener());

        commentRecyclerView = (RecyclerView) bottom.findViewById(R.id.comment_list);
        commentAdapter = new MyCommentAdapter();
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected View getJavaCodeView() {
        return null;
    }

    @Override
    protected void initIntentData() {
        Bundle data = getIntent().getBundleExtra("data");
        photoPosition = data.getInt("position");
        photoList = data.getParcelableArrayList("photoList");
        if (photoList != null) {
            commentList = photoList.get(photoPosition).getComment();
        }
    }

    @Override
    protected void initData() {
    }

    private void showVoteArea() {
        MyVoteDialog myVoteDialog = new MyVoteDialog(this);
        final RatingBar ratingBar = myVoteDialog.getRatingBar();
        ratingBar.setRating((float) photoList.get(photoPosition).getVote());
        myVoteDialog.setPositiveClickListener(new MyVoteDialog.IMyClickListener() {
            @Override
            public void onClick() {
                PhotoAlbumManager.getInstance().voteRequest(String.valueOf(photoList.get(photoPosition).getId()), String.valueOf(photoList.get(photoPosition).getBelongId()), String.valueOf(ratingBar.getRating()), new INetWorkListener() {
                    @Override
                    public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                        String jsonStr = responseInfo.result.toString();

                        if (PhotoAlbumManager.getInstance().parserVote(jsonStr) == 1) {
                            float newRate = PhotoAlbumManager.getInstance().parserVotePoint(jsonStr);
                            photoList.get(photoPosition).setVote(newRate);
                            ratingBar.setRating(newRate);
                        } else {
                            ToastUtils.showShortToast("投票失败");
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
            }
        });
        myVoteDialog.show();
    }

    private void showCommentArea() {
        if (showComment) {
            comment_area.setVisibility(View.GONE);
            showComment = false;
        } else {
            comment_area.setVisibility(View.VISIBLE);
            showComment = true;
        }

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoAlbumManager.getInstance().commitCommentRequest(comment_content.getText().toString(), photoList.get(photoPosition).getId(), new INetWorkListener() {
                    @Override
                    public <T> void onSuccess(ResponseInfo<T> responseInfo) {
                        String jsonStr = responseInfo.result.toString();
                        if (PhotoAlbumManager.getInstance().parserComment(jsonStr) == 1) {
                            photoList.get(photoPosition).setComment(PhotoAlbumManager.getInstance().parserAllComment(jsonStr));
                            commentList.addAll(0, PhotoAlbumManager.getInstance().parserAllComment(jsonStr));
                            commentAdapter.notifyDataSetChanged();
                            comment_content.setText("");
                        } else {
                            ToastUtils.showShortToast(R.string.comment_failure);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
            }
        });

    }

    private class ImagePagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        ImagePagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);

            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.photo);
            Glide.with(PhotoActivity.this).load(NetWorkConfig.getHttpApiPath() + photoList.get(position).getPhotoUrl()).into(imageView);
            (view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Comment comment = commentList.get(position);

            holder.comment_user_name.setText(comment.getName());
            holder.comment_content.setText(comment.getContent());
            holder.comment_time.setText(comment.getDate());

        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView comment_user_name;
            TextView comment_time;
            TextView comment_content;

            public MyViewHolder(View itemView) {
                super(itemView);
                comment_user_name = (TextView) itemView.findViewById(R.id.comment_user_name);
                comment_time = (TextView) itemView.findViewById(R.id.comment_time);
                comment_content = (TextView) itemView.findViewById(R.id.comment_content);
            }
        }

    }
}
