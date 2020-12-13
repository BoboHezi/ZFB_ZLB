package com.eli.zfb_zlb.act;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;

public class MainActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xff1678ff);

        setContentView(R.layout.activity_main);
    }

    public void search(View v) {
        startActivity(new Intent(this, SearchActivity.class));
    }
}