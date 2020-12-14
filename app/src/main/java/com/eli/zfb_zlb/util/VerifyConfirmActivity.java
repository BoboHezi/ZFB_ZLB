package com.eli.zfb_zlb.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.act.BaseActivity;
import com.eli.zfb_zlb.act.CameraActivity;

public class VerifyConfirmActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm);

        TextView userText = findViewById(R.id.user_text);
        userText.postDelayed(() -> {
            startActivityForResult(new Intent(this, CameraActivity.class), 101);
        }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("elifli", "VerifyConfirmActivity requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == 101 && resultCode == 104) {
            setResult(105);
            finish();
        }
    }
}
