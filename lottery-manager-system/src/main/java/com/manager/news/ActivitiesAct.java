package com.manager.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.manager.adapter.CommonAdapter;
import com.manager.adapter.ViewHolder;
import com.manager.bean.NewsBean;
import com.manager.common.Constants;
import com.manager.lotterypro.R;

import java.util.ArrayList;

/**
 * 活动 界面
 * @author donghuiyang
 * @create time 2016/5/13 0013.
 */
public class ActivitiesAct extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{
    //返回按钮
    private View backBtn;

    private ListView mListView;
    private CommonAdapter<NewsBean> mListViewAdapter;
    //生成动态数组，加入数据
    ArrayList<NewsBean> mLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_layout);

        initData();

        initView();
        initList();
    }

    @Override
     protected void onDestroy() {
        mLists = null;

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (backBtn == v){
            //返回按钮
            finish();
        }
    }

    /**
     * 获取控件对象
     */
    private void initView() {
        ViewGroup topView = (ViewGroup)findViewById(R.id.activities_topview);
        TextView titleView = (TextView) topView.findViewById(R.id.title_tv_topview);
        titleView.setText(R.string.news_item_str_1);

        backBtn = (View) topView.findViewById(R.id.common_topview_back_btn);
        backBtn.setOnClickListener(this);
    }

    private void initList() {
        mListView = (ListView) findViewById(R.id.activities_listview);
        mListViewAdapter = new CommonAdapter<NewsBean>(this, mLists, R.layout.list_item_3) {
            @Override
            public void convert(ViewHolder helper, NewsBean item) {
                helper.setImageResource(R.id.list_item_3_icon_imgeview, item.getIconID());
                helper.setText(R.id.list_item_3_title_textview, item.getNewsTitle());
                helper.setText(R.id.list_item_3_time_textview, item.getNewsTime());
                helper.setText(R.id.list_item_3_time1_textview, item.getNewsTime1());
            }
        };
        mListView.setAdapter(mListViewAdapter);

        mListView.setOnItemClickListener(this);
    }

    /**
     * 初始化list数据
     */
    private void initData() {
        //添加数据
        mLists = Constants.ActivitiesLists;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NewsShowAct.class);
        intent.putExtra("data", mLists.get(position));
        startActivity(intent);
    }
}
