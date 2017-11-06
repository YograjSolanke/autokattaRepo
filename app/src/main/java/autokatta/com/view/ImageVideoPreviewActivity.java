package autokatta.com.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

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
            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(ImageVideoPreviewActivity.this, image);
            viewPager.setAdapter(myPagerAdapter);
        }
    }


    private class MyPagerAdapter extends PagerAdapter {

        String[] mStrings;
        ImageLoader imageLoader;
        List<String> imageList;

        private MyPagerAdapter(ImageVideoPreviewActivity sliderActivity, List<String> image) {
            mStrings = new String[image.size()];
            mStrings = (String[]) image.toArray(mStrings);
            imageList = image;
            imageLoader = new ImageLoader(ImageVideoPreviewActivity.this);
        }

        @Override
        public int getCount() {
            return mStrings.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TextView textView = new TextView(ImageVideoPreviewActivity.this);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setText(String.valueOf(position));

            ImageView imageView = new ImageView(ImageVideoPreviewActivity.this);

            try {
                Glide.with(ImageVideoPreviewActivity.this)
                        .load(mStrings[position])
                        .override(320, 240)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);

            LinearLayout layout = new LinearLayout(ImageVideoPreviewActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(Color.parseColor("#000000"));
            layout.setLayoutParams(layoutParams);
            layout.addView(imageView);

            final int page = position;
           /* layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),
                            "Page " + page + " clicked",
                            Toast.LENGTH_LONG).show();


                    Bundle b = new Bundle();
                    try {
                        b.putString("path", image.get(page));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    b.putInt("Activity", 1);
                    b.putInt("number", page);

                    ImageEditFragment fragment2 = new ImageEditFragment();
                    fragment2.setArguments(b);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.vehicle_upload_container, fragment2, "imageeditfragment")
                            .addToBackStack("imageeditfragment")
                            .commit();

                }
            });*/
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
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
