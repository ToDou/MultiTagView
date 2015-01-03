package com.test.tudou.multitagview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.test.tudou.multitagview.view.MultiTagView;


public class MainActivity extends Activity {
    //https://github.com/cloay/CTagView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
