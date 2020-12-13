package com.eli.zfb_zlb.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;

public class ZlbHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xffb0b0b0);

        setContentView(R.layout.activity_zlb_home);
    }

    public void marriage(View v) {
        startActivity(new Intent(this, VerifyActivity.class));
    }
}
