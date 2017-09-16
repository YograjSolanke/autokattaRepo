package autokatta.com.upload_vehicle;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import autokatta.com.R;
import autokatta.com.other.ImageLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ak-001 on 23/3/17.
 */

public class SelectedImagesFragment extends Fragment implements View.OnClickListener {
    View mSelectedImages;
    List<String> image = new ArrayList<>();
    String allimg = "";
    public List<String> map = new ArrayList<>();
    int count = 0;
    ViewPager viewPager;
    Button btnGo, btnBack;
    int call;
    Bundle b;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String updatedImages;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSelectedImages = inflater.inflate(R.layout.fragment_selected_image, container, false);
        viewPager = (ViewPager) mSelectedImages.findViewById(R.id.viewPager);
        btnGo = (Button) mSelectedImages.findViewById(R.id.btnGo);
        btnBack = (Button) mSelectedImages.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        Bundle b = getArguments();
        call = b.getInt("call");
        Log.i("call in image", String.valueOf(call));

        if (call == 3) {

            int a = b.getInt("number", 0);
            String newpath = b.getString("newpath");
            Log.i("path on back", newpath);
//            SharedPreferences settings1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_WORLD_READABLE | Context.MODE_PRIVATE);
//            Editor editor1 = settings1.edit();

            String text1 = getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).getString("images", "");
            List<String> ImgData2 = Arrays.asList(text1.split(","));
            image.clear();
            for (int i1 = 0; i1 < ImgData2.size(); i1++) {
                if (allimg.equalsIgnoreCase("")) {
                    allimg = "" + ImgData2.get(i1);
                } else {
                    allimg = allimg + "," + ImgData2.get(i1);
                }
                System.out.println("image list-> " + ImgData2.get(i1));
                map.add(ImgData2.get(i1));
                image.add(ImgData2.get(i1));
                count++;
            }
            image.set(a, newpath);


        } else {
            List<String> ImgData = b.getStringArrayList("IMAGE");
            image.clear();
            for (int i1 = 0; i1 < (ImgData != null ? ImgData.size() : 0); i1++) {
                if (allimg.equalsIgnoreCase("")) {
                    allimg = "" + ImgData.get(i1);
                } else {
                    allimg = allimg + "," + ImgData.get(i1);
                }
                Log.i("All Image", "->->" + allimg);
                System.out.println(ImgData.get(i1));
                map.add(ImgData.get(i1));
                image.add(ImgData.get(i1));
                count++;
            }
        }
        updatedImages = "";
        for (int k = 0; k < image.size(); k++) {
            if (updatedImages.equals(""))
                updatedImages = image.get(k);
            else
                updatedImages = updatedImages + "," + image.get(k);
        }
        getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().
                putString("images", updatedImages).apply();

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(SelectedImagesFragment.this, image);
        viewPager.setAdapter(myPagerAdapter);
        return mSelectedImages;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
               /* b = new Bundle();
                b.putInt("call", 2);
                SubTypeFragment fragment2 = new SubTypeFragment();
                fragment2.setArguments(b);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.vehicle_upload_container, fragment2);
                fragmentTransaction.commit();*/
                break;

            case R.id.btnGo:

                getActivity().getSharedPreferences(getString(R.string.my_preference), MODE_PRIVATE).edit().putString("images", allimg).apply();
                b = new Bundle();
                b.putInt("call", call);

                b.putStringArrayList("images", (ArrayList<String>) image);

                PriceFragment fragment = new PriceFragment();
                fragment.setArguments(b);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.vehicle_upload_container, fragment).addToBackStack("pricefragment");
                fragmentTransaction.commit();
                break;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        String[] mStrings;
        ImageLoader imageLoader;

        private MyPagerAdapter(SelectedImagesFragment sliderActivity, List<String> image) {
            mStrings = new String[image.size()];
            mStrings = (String[]) image.toArray(mStrings);
            imageLoader = new ImageLoader(sliderActivity.getActivity());
        }

        @Override
        public int getCount() {
            return image.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            TextView textView = new TextView(getActivity());
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setText(String.valueOf(position));

            ImageView imageView = new ImageView(getActivity());

            try {
                Glide.with(getActivity())
                        .load(mStrings[position]).override(320, 240)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(Color.parseColor("#000000"));
            layout.setLayoutParams(layoutParams);
            layout.addView(imageView);

            final int page = position;
            layout.setOnClickListener(new View.OnClickListener() {

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
                    /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.vehicle_upload_container, fragment2);
                    fragmentTransaction.addToBackStack("imageeditfragment");
                    fragmentTransaction.commit();*/
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.vehicle_upload_container, fragment2, "imageeditfragment")
                            .addToBackStack("imageeditfragment")
                            .commit();

                }
            });

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

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
