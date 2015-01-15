package com.test.tudou.multitagview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.test.tudou.multitagview.view.MultiTagView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    //https://github.com/cloay/CTagView

    MultiTagView multiTagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        multiTagView = (MultiTagView) findViewById(R.id.tag_view_add_by_list);

        updateView();
    }

    private void updateView() {
        multiTagView.setTagClickable(false);
        multiTagView.setShowAddButton(false);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("苹果");
        arrayList.add("葡萄");
        arrayList.add("西红柿");
        arrayList.add("哈哈哈哈哈");
        multiTagView.addTags(arrayList);
    }
}
