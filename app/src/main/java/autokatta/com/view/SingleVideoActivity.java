package autokatta.com.view;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import autokatta.com.R;

public class SingleVideoActivity extends AppCompatActivity {


    // Declare variables

    ProgressDialog pDialog;
    VideoView videoview;
    // Insert your Video URL
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                if (getIntent().getExtras() != null) {

                    VideoURL = getIntent().getExtras().getString("url");
                }

                videoview = (VideoView) findViewById(R.id.VideoView);
                // Execute StreamVideo AsyncTask

                // Create a progressbar
                pDialog = new ProgressDialog(SingleVideoActivity.this);
                // Set progressbar title
                pDialog.setTitle("Please Wait");
                // Set progressbar message
                pDialog.setMessage("Buffering...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                // Show progressbar
                pDialog.show();

                try {
                    // Start the MediaController
                    MediaController mediacontroller = new MediaController(
                            SingleVideoActivity.this);
                    mediacontroller.setAnchorView(videoview);
                    // Get the URL from String VideoURL
                    Uri video = Uri.parse(VideoURL);
                    videoview.setMediaController(mediacontroller);
                    videoview.setVideoURI(video);

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                videoview.requestFocus();
                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        pDialog.dismiss();
                        videoview.start();
                    }
                });
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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

}
