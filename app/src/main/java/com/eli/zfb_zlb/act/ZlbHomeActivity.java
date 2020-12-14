package com.eli.zfb_zlb.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
        startActivityForResult(new Intent(this, VerifyActivity.class), 99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("elifli", "ZlbHomeActivity requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == 99 && resultCode == 106) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, MarriageActivity.class));
            }, 1000);
        }
    }
}
