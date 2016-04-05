package com.ares89.scalingitem;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lshg on 2016/4/5.
 */
class ListAdapter extends BaseAdapter {

    private Context mContext;
    private float textHeight;

    public ListAdapter(Context mContext, float textHeight) {

        this.mContext = mContext;
        this.textHeight = textHeight;
    }

    @Override
    public int getCount() {
        return Constant.contries.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.imageView.setImageResource(Constant.contryFlag[position]);
        viewHolder.title.setText(Constant.contries[position]);
        viewHolder.slogan1.setText(Constant.capitals[position]);
        viewHolder.slogan2.setText(Constant.desc[position]);

        viewHolder.slogan1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        viewHolder.slogan2.setVisibility(View.GONE);
        viewHolder.slogan2.setTranslationY(-textHeight);

        return convertView;
    }


    public class ViewHolder {
        TextView title, slogan1, slogan2;
        ImageView imageView;

        ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.image);
            title = (TextView) convertView.findViewById(R.id.title);
            slogan1 = (TextView) convertView.findViewById(R.id.slogan1);
            slogan2 = (TextView) convertView.findViewById(R.id.slogan2);
        }
    }
}
