package com.eli.zfb_zlb.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;
import com.eli.zfb_zlb.util.VerifyConfirmActivity;

public class VerifyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xff0b60a0);

        setContentView(R.layout.activity_verify);
    }

    public void startVerify(View v) {
        startActivity(new Intent(this, VerifyConfirmActivity.class));
    }
}
