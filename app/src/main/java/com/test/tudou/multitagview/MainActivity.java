package com.test.tudou.multitagview;

import android.app.Activity;
import android.os.Bundle;

import com.test.tudou.multitagview.view.MultiTagView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {
    @InjectView(R.id.tag_view_add)
    MultiTagView mTagViewAdd;
    @InjectView(R.id.tag_view_add_by_list)
    MultiTagView mTagViewAddByList;
    //https://github.com/cloay/CTagView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        updateView();
    }

    private void updateView() {
        mTagViewAddByList.setTagClickable(false);
        mTagViewAddByList.setShowAddButton(false);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("苹果");
        arrayList.add("葡萄");
        arrayList.add("西红柿");
        arrayList.add("哈哈哈哈哈");
        mTagViewAddByList.addTags(arrayList);
    }
}
