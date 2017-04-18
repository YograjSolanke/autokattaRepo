package autokatta.com.search;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import autokatta.com.R;
import autokatta.com.networkreceiver.ConnectionDetector;
import autokatta.com.other.ImageLoader;

/**
 * Created by ak-001 on 18/4/17.
 */

public class ProductImageSlider extends Fragment {
    View mProductImage;
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    ArrayList<String> image = new ArrayList<String>();
    String all_img;
    ConnectionDetector mConnectionDetector = new ConnectionDetector(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProductImage = inflater.inflate(R.layout.fragment_product_image_slider, container, false);

        Bundle b1 = getArguments();
        all_img = b1.getString("images");

        //****** Shared Preference For Event Of Back Button Pressed
        getActivity().getSharedPreferences(getString(R.string.my_preference), Context.MODE_PRIVATE).edit()
                .putString("fragment", "ProductImageSliderFragment").apply();

        if (!mConnectionDetector.isConnectedToInternet()) {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        } else {
            if (all_img.contains(",")) {
                String arr[] = all_img.split(",");
                for (int i = 0; i < arr.length; i++) {
                    String url = "http://autokatta.com/mobile/Product_pics/";
                    String imageName = arr[i];
                    String fullurl = url + imageName;
                    image.add(fullurl);
                }
            } else {
                String url = "http://autokatta.com/mobile/Product_pics/";
                String fullurl = url + all_img;
                image.add(fullurl);
            }
            viewPager = (ViewPager) mProductImage.findViewById(R.id.myviewpager);
            myPagerAdapter = new MyPagerAdapter(this, image);
            viewPager.setAdapter(myPagerAdapter);
        }
        return mProductImage;
    }

    private class MyPagerAdapter extends PagerAdapter {

        String[] mStrings;
        ImageLoader imageLoader;

        MyPagerAdapter(ProductImageSlider sliderActivity, ArrayList<String> image) {
            mStrings = new String[image.size() - 1];
            mStrings = (String[]) image.toArray(mStrings);
            imageLoader = new ImageLoader(getActivity().getApplicationContext());
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
            //  layout.addView(textView);
            layout.addView(imageView);

            final int page = position;
            layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),
                            "Page " + page + " clicked",
                            Toast.LENGTH_LONG).show();
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
}
