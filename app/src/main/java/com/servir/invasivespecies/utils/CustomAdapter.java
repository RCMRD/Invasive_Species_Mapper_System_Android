package com.servir.invasivespecies.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.servir.invasivespecies.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int images[];
    String[] feature;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] flags, String[] feature) {
        this.context = applicationContext;
        this.images = flags;
        this.feature = feature;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.image_spinner_kolek, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(images[i]);
        names.setText(feature[i]);
        return view;
    }
}