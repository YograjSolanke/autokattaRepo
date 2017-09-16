package autokatta.com.upload_vehicle;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import autokatta.com.R;
import autokatta.com.other.ColorPickerDialog;

/**
 * Created by ak-001 on 23/3/17.
 */

public class ImageEditFragment extends Fragment implements ColorPickerDialog.OnColorChangedListener {
    View mImageEdit;
    String path;
    Bitmap bitmapMaster, image, tempBitmap;
    ImageView iv_ttx;
    int number;
    Paint paint;
    Canvas canvasMaster;

    public ImageEditFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageEdit = inflater.inflate(R.layout.fragment_image_edit, container, false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth((float) 3);

                iv_ttx = (ImageView) mImageEdit.findViewById(R.id.iv_ttx);
                iv_ttx.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                drawOnProjectedBitMap((ImageView) v, bitmapMaster, x, y);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                drawOnProjectedBitMap((ImageView) v, bitmapMaster, x, y);
                                break;
                            case MotionEvent.ACTION_UP:
                                drawOnProjectedBitMap((ImageView) v, bitmapMaster, x, y);
                                break;
                        }
                          /*
     * Return 'true' to indicate that the event have been consumed.
     * If auto-generated 'false', your code can detect ACTION_DOWN only,
     * cannot detect ACTION_MOVE and ACTION_UP.
     */
                        return true;
                    }
                });

                Bundle b1 = getArguments();
                path = b1.getString("path");
                number = b1.getInt("number");
                File file = new File(path); //your image file path
                Uri source = Uri.fromFile(file);
                try {
                    //tempBitmap is Immutable bitmap,
                    //cannot be passed to Canvas constructor
                    tempBitmap = BitmapFactory.decodeStream(
                            getActivity().getContentResolver().openInputStream(source));

                    Bitmap.Config config;
                    if (tempBitmap.getConfig() != null) {
                        config = tempBitmap.getConfig();
                    } else {
                        config = Bitmap.Config.RGB_565;
                    }
                    //bitmapMaster is Mutable bitmap
                    bitmapMaster = Bitmap.createBitmap(
                            tempBitmap.getWidth(),
                            tempBitmap.getHeight(),
                            config);
                    image = tempBitmap.copy(Bitmap.Config.RGB_565, true);
                    canvasMaster = new Canvas(bitmapMaster);
                    canvasMaster.drawBitmap(tempBitmap, 0, 0, null);

                    iv_ttx.setImageBitmap(bitmapMaster);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Button btn_save_img = (Button) mImageEdit.findViewById(R.id.btn_save_image);
                Button btn_clr_all = (Button) mImageEdit.findViewById(R.id.btn_clr_all);
                btn_clr_all.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //loading original bitmap again (undoing all editing)
                        image = tempBitmap.copy(Bitmap.Config.RGB_565, true);
                        iv_ttx.setImageBitmap(image);
                    }
                });

                btn_save_img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        //funtion save image is called with bitmap image as parameter
                        saveImage(bitmapMaster);

                    }
                });
            }
        });
        return mImageEdit;
    }

    /*
    * Project position on ImageView to position on Bitmap
    * draw on it
    */
    private void drawOnProjectedBitMap(ImageView iv, Bitmap bm, int x, int y) {
        if (x < 0 || y < 0 || x > iv.getWidth() || y > iv.getHeight()) {
            //outside ImageView
        } else {
            int projectedX = (int) ((double) x * ((double) bm.getWidth() / (double) iv.getWidth()));
            int projectedY = (int) ((double) y * ((double) bm.getHeight() / (double) iv.getHeight()));

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth((float) 3);
            canvasMaster.drawCircle(projectedX, projectedY, 10, paint);
            iv_ttx.invalidate();

        }
    }

    private Paint mPaint;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    /*
    SAVE EDITED IMAGE...
     */
    void saveImage(Bitmap img) {
        /*String RootDir = Environment.getExternalStorageDirectory()
                + File.separator;//+ "txt_imgs";
//String test =  "This is a sentence";
        String firstWords = path.substring(0, path.lastIndexOf("/"));
        System.out.println("image path;;" + firstWords);

        String lastWord = path.substring(path.lastIndexOf("/") + 1);
        String driveLetter = lastWord.split("\\.")[0];
// String firstWord = arr[0];
// String sec = arr[1];
// String firstWord = arr[0];
        System.out.println(RootDir);
        //  System.out.println("lastword:::"+firstWord);
        System.out.println("secword:::" + driveLetter);

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = driveLetter + "1.jpg";
        System.out.println("Whole image name:::" + fname);

        File myDir = new File(firstWords);
        boolean isDirectoryCreated = myDir.mkdirs();
        String newpath = "";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);

            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        newpath = "" + file;
        Toast.makeText(getActivity(), "Image saved " + file, Toast.LENGTH_LONG).show();*/


        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        ContextWrapper cw = new ContextWrapper(getActivity());
        String lastWord = path.substring(path.lastIndexOf("/") + 1);
        String driveLetter = lastWord.split("\\.")[0];
        // path to /data/data/yourapp/app_data/imageDir
        File MyDirectory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //File MyDirector = cw.getExternalCacheDir();

        // Create imageDir
        File MyPath = new File(MyDirectory, driveLetter + n + ".jpg");
        //File MyPath = new File(MyDirectory,"Image" + n + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(MyPath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            img.compress(Bitmap.CompressFormat.PNG, 100, fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String newpath = "";
        newpath = "" + MyPath;


        Bundle b = new Bundle();

        b.putInt("call", 3);
        b.putInt("number", number);
        b.putString("newpath", newpath);
        SelectedImagesFragment fragment2 = new SelectedImagesFragment();
        fragment2.setArguments(b);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.vehicle_upload_container, fragment2);
        fragmentTransaction.addToBackStack("selectedimagefragment");
        fragmentTransaction.commit();
    }

    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;
    private static final int BLUR_MENU_ID = Menu.FIRST + 2;
    private static final int ERASE_MENU_ID = Menu.FIRST + 3;
    private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;
    private static final int Image_gallery = Menu.FIRST + 5;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_autokatta_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
        menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId()) {
            case COLOR_MENU_ID:
                new ColorPickerDialog(getActivity(), this, mPaint.getColor()).show();
            case ERASE_MENU_ID:
                mPaint.setXfermode(new PorterDuffXfermode(
                        PorterDuff.Mode.CLEAR));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ColorPickerDialog.OnColorChangedListener listener = new ColorPickerDialog.OnColorChangedListener() {
        @Override
        public void colorChanged(int color) {
            // Toast.makeText(FullImageViewActivity.this, ""+color, Toast.LENGTH_LONG).show();
            //  new ColorPickerDialog(FullImageViewActivity.this, listener, mPaint.getColor()).show();
            // ll.setBackgroundColor(ColorAh);
            mPaint.setColor(color);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.vehicle_upload_container);

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }
}
