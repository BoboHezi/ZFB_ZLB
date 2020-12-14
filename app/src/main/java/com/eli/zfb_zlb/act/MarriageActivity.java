package com.eli.zfb_zlb.act;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;

public class MarriageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xffb0b0b0);

        setContentView(R.layout.activity_marriage);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
