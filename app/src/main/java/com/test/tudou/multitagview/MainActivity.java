package com.test.tudou.multitagview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.tudou.library.ui.view.MultiTagView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


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

    @OnClick({
            R.id.btn_bottom_result
    })
    @SuppressWarnings("unused")
    public void OnClick(View view) {
        ArrayList<String> results = mTagViewAdd.getTags();
        mTagViewAddByList.removeAllTagView();
        mTagViewAddByList.addTags(results);
    }

    private void updateView() {
        mTagViewAddByList.setTagClickable(false);
        mTagViewAddByList.setShowAddButton(false);
    }
}
