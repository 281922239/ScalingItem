package com.ares89.scalingitem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ListView mListview;
    private float textHeight;
    private int itemBigHeight;
    private int itemHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initHeight();

        mListview = (ListView) findViewById(R.id.listView);
        ListAdapter listAdapter = new ListAdapter(this, textHeight);

        mListview.setAdapter(listAdapter);
        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {

            int firstVisibleItem;
            int visibleItemCount;
            int totalItemCount;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    View v;
                    int y;
                    v = view.getChildAt(0);
                    if (v == null)
                        return;
                    y = v.getTop();

                    if (firstVisibleItem + visibleItemCount != totalItemCount) {
                        if (y < -itemHeight / 2)
                            mListview.smoothScrollBy(itemHeight + y, 400);
                        else
                            mListview.smoothScrollBy(y, 400);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.firstVisibleItem = firstVisibleItem;
                this.visibleItemCount = visibleItemCount;
                this.totalItemCount = totalItemCount;

                onListScroll(view);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/ares89/ScalingItem"));
                startActivity(intent);
            }
        });
    }

    private void onListScroll(AbsListView view) {
        View v;
        int y;

        v = view.getChildAt(0);
        if (v == null)
            return;
        ListAdapter.ViewHolder holder = (ListAdapter.ViewHolder) v.getTag();
        holder.slogan1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        holder.slogan2.setVisibility(View.GONE);
        holder.slogan2.setTranslationY(-textHeight);
        AdapterView.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = itemHeight;
        v.setLayoutParams(layoutParams);

        v = view.getChildAt(1);
        if (v == null)
            return;
        y = v.getTop();
        holder = (ListAdapter.ViewHolder) v.getTag();
        float rate = (float) (y - 1) / itemHeight;
        holder.slogan1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + 12 * rate);
        if (rate > 0.6)
            holder.slogan2.setVisibility(View.VISIBLE);
        holder.slogan2.setAlpha(rate);
        holder.slogan2.setTranslationY(-textHeight * (1 - rate));
        layoutParams = v.getLayoutParams();
        layoutParams.height = (int) (itemHeight + (itemBigHeight - itemHeight) * rate);
        v.setLayoutParams(layoutParams);

        v = view.getChildAt(2);
        if (v == null)
            return;
        y = v.getTop();
        holder = (ListAdapter.ViewHolder) v.getTag();
        rate = ((float) (y - 1 - itemHeight) / itemBigHeight);
        holder.slogan1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28 - 12 * rate);
        if (1 - rate > 0.6)
            holder.slogan2.setVisibility(View.VISIBLE);
        holder.slogan2.setAlpha(1 - rate);
        holder.slogan2.setTranslationY(-textHeight * rate);
        layoutParams = v.getLayoutParams();
        layoutParams.height = (int) (itemHeight + (itemBigHeight - itemHeight) * (1 - rate));
        v.setLayoutParams(layoutParams);

        for (int i = 3; i < view.getChildCount(); i++) {
            v = view.getChildAt(i);
            if (v == null)
                return;
            holder = (ListAdapter.ViewHolder) v.getTag();
            holder.slogan1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            holder.slogan2.setVisibility(View.GONE);
            holder.slogan2.setTranslationY(-textHeight);
            layoutParams = v.getLayoutParams();
            layoutParams.height = itemHeight;
            v.setLayoutParams(layoutParams);
        }
    }

    private void initHeight() {
        textHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

        View v = LayoutInflater.from(this).inflate(R.layout.list_item_big, null);
        v.measure(0, 0);
        itemBigHeight = v.getMeasuredHeight();

        v = LayoutInflater.from(this).inflate(R.layout.list_item, null);
        v.measure(0, 0);
        itemHeight = v.getMeasuredHeight();
    }


}
