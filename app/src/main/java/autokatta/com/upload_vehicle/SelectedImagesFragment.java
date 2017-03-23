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
import java.util.List;

import autokatta.com.R;
import autokatta.com.other.ImageLoader;

/**
 * Created by ak-001 on 23/3/17.
 */

public class SelectedImagesFragment extends Fragment {
    View mSelectedImages;
    ArrayList<String> image = new ArrayList<>();
    String allimg = "";
    public List<String> map = new ArrayList<>();
    int count = 0;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSelectedImages = inflater.inflate(R.layout.fragment_selected_image, container, false);
        viewPager = (ViewPager) mSelectedImages.findViewById(R.id.viewPager);
        Bundle b = getArguments();
        ArrayList<String> ImgData = b.getStringArrayList("IMAGE");
        for (int i1 = 0; i1 < ImgData.size(); i1++) {
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
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(SelectedImagesFragment.this, image);
        viewPager.setAdapter(myPagerAdapter);
        return mSelectedImages;
    }

    private class MyPagerAdapter extends PagerAdapter {

        String[] mStrings;
        ImageLoader imageLoader;

        private MyPagerAdapter(SelectedImagesFragment sliderActivity, ArrayList<String> image) {
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
                    ImageEditFragment fragment2 = new ImageEditFragment();
                    fragment2.setArguments(b);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.vehicle_upload_container, fragment2);
                    fragmentTransaction.addToBackStack("imageeditfragment");
                    fragmentTransaction.commit();

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
