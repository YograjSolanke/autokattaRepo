package autokatta.com.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import autokatta.com.R;
import autokatta.com.other.ImageLoader;

public class ImageVideoPreviewActivity extends AppCompatActivity {

    String videoPath, videoWithoutPath = "", imagesPath, imagesWithoutPath, statusText;
    EditText mStatusText;
    String myContact, updatedImages = "";
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_video_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Post Preview");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            videoPath = getIntent().getExtras().getString("videoPath", "");
            imagesPath = getIntent().getExtras().getString("imagesPath", "");
            statusText = getIntent().getExtras().getString("statusText", "");
            imagesWithoutPath = getIntent().getExtras().getString("images", "");
        }

        myContact = getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE)
                .getString("loginContact", "");

        ImageView mClose = (ImageView) findViewById(R.id.close);
        final VideoView mVideoView = (VideoView) findViewById(R.id.VideoView);
        RelativeLayout mVideoRel = (RelativeLayout) findViewById(R.id.relVideo);
        RelativeLayout mImageRel = (RelativeLayout) findViewById(R.id.relImage);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mStatusText = (EditText) findViewById(R.id.status);
        //final Button play = (Button) findViewById(R.id.play);
        if (!statusText.equals("")) {
            mStatusText.setText(statusText);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.postStatus);
        fab.setVisibility(View.GONE);
        
        /*play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoView.start();
                play.setVisibility(View.GONE);
            }
        });*/

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mBottomSheetDialog.dismiss();
            }
        });

        //video code
        if (!videoPath.equals("") && imagesPath.equals("")) {
            mVideoRel.setVisibility(View.VISIBLE);
            mImageRel.setVisibility(View.GONE);
            try {
                String lastWord = videoPath.substring(videoPath.lastIndexOf("/") + 1);
                videoWithoutPath = lastWord.replace(" ", "");
                // Start the MediaController
                final MediaController mediacontroller = new MediaController(
                        ImageVideoPreviewActivity.this);
                mediacontroller.setAnchorView(mVideoView);
                // set media controller object for a video view
                mVideoView.setMediaController(mediacontroller);
                // Get the URL from String VideoURL
               /* Uri video = Uri.parse(videoPath);
                mVideoView.setVideoURI(video);*/
                mVideoView.setVideoPath(videoPath);
                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediacontroller.show();
                        mVideoView.start();
                    }
                });

                // mVideoView.start();

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        }

        //Imagecode
        else {
            mVideoRel.setVisibility(View.GONE);
            mImageRel.setVisibility(View.VISIBLE);
            videoWithoutPath = "";
            List<String> ImgData2 = Arrays.asList(imagesPath.split(","));
            List<String> image = new ArrayList<>();
            image.addAll(ImgData2);
            for (int k = 0; k < image.size(); k++) {
                if (updatedImages.equals(""))
                    updatedImages = image.get(k);
                else
                    updatedImages = updatedImages + "," + image.get(k);
            }
            myPagerAdapter = new MyPagerAdapter(ImageVideoPreviewActivity.this, image);
            viewPager.setAdapter(myPagerAdapter);
        }
    }


    private class MyPagerAdapter extends PagerAdapter {
        Context context;
        String[] mStrings;
        ImageLoader imageLoader;
        List<String> imageList;
        private LayoutInflater layoutInflater;

        private MyPagerAdapter(ImageVideoPreviewActivity sliderActivity, List<String> image) {
            context = sliderActivity;
            mStrings = new String[image.size()];
            mStrings = (String[]) image.toArray(mStrings);
            imageList = image;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageLoader = new ImageLoader(ImageVideoPreviewActivity.this);
        }


//        public void addView(View view, int index) {
//            mList.add(index, view);
//            notifyDataSetChanged();
//        }

        public void removeView(int index) {
            System.out.println("hee before remove=" + imageList.size());
            imageList.remove(index);
            notifyDataSetChanged();

        }

        @Override
        public int getItemPosition(Object object) {
            if (imageList.contains((View) object)) {
                return imageList.indexOf((View) object);
            } else {
                return POSITION_NONE;
            }
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        public List<String> getImageList() {
            System.out.println(" hee after remove=" + imageList.size());
            return imageList;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = this.layoutInflater.inflate(R.layout.image_preview_pager_list_item, container, false);

            ImageView remove = (ImageView) view.findViewById(R.id.remove);
            ImageView image = (ImageView) view.findViewById(R.id.image);


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeView(position);
                }
            });

            Glide.with(context)
                    .load(imageList.get(position))
                    // .bitmapTransform(new CropCircleTransformation(mActivity))
                    .override(320, 320)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //For caching diff versions of image.
                    .into(image);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.post_status:
                imagesPath = "";
                imagesWithoutPath = "";
                if (mStatusText.getText().toString().equals("") ||
                        (mStatusText.getText().toString().startsWith(" ") &&
                                mStatusText.getText().toString().endsWith(" "))) {
                    mStatusText.setError("Enter status");
                    mStatusText.requestFocus();
                } else {

                    /*code to encode the status string*/
                    byte[] data = new byte[0];
                    try {
                        data = mStatusText.getText().toString().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String encodedString = Base64.encodeToString(data, Base64.DEFAULT);


                    for (int i = 0; i < myPagerAdapter.getImageList().size(); i++) {
                        String path = myPagerAdapter.getImageList().get(i);
                        String lastWord = path.substring(path.lastIndexOf("/") + 1);
                        if (imagesWithoutPath.equalsIgnoreCase("") && imagesPath.equalsIgnoreCase("")) {
                            imagesPath = path;
                            imagesWithoutPath = lastWord;
                        } else {
                            imagesPath = imagesPath + "," + path;
                            imagesWithoutPath = imagesWithoutPath + "," + lastWord;
                        }
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("videoPath", videoPath);
                    bundle.putString("imagesPath", imagesPath);
                    bundle.putString("images", imagesWithoutPath);
                    bundle.putString("videos", videoWithoutPath);
                    bundle.putString("statusText", encodedString);
                    Intent intent = new Intent(this, AddInterestTagsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
