package ibdaa.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import ibdaa.bloodbank.R;
import ibdaa.bloodbank.view.fragment.splashCycle.OnBoardingFragment;

public class SlideAdapter extends PagerAdapter {
    private  Context context;
    private int[] GalImages = new int[]{R.drawable.item1, R.drawable.item2,R.drawable.item1};
    private String[] texts = new String[]{"The spelling mistakes in the text had been highlighted in green.\n" +
            "The text is finished, but the pictures will have to be pasted in later.\n" +
            "You need to demonstrate to the examiners that you have more than a literal understanding of the text.\n" +
            "The students are reading as one of their set texts this year.\n" +
            "At this point in the speech, the minister departed from his prepared text." , "The spelling mistakes in the text had been highlighted in green.\n" +
            "The text is finished, but the pictures will have to be pasted in later.\n" +
            "You need to demonstrate to the examiners that you have more than a literal understanding of the text.\n" +
            "The students are reading as one of their set texts this year.\n" +
            "At this point in the speech, the minister departed from his prepared text." , "The spelling mistakes in the text had been highlighted in green.\n" +
            "The text is finished, but the pictures will have to be pasted in later.\n" +
            "You need to demonstrate to the examiners that you have more than a literal understanding of the text.\n" +
            "The students are reading as one of their set texts this year.\n" +
            "At this point in the speech, the minister departed from his prepared text."};

    LayoutInflater mLayoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {

            View itemView = mLayoutInflater.inflate(R.layout.on_boarding_item, container, false);

            ImageView imageView = itemView.findViewById(R.id.imageview);
            imageView.setImageResource(GalImages[position]);
            TextView textView = itemView.findViewById(R.id.textview);
            textView.setText(texts[position]);

            container.addView(itemView);

            return itemView;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}