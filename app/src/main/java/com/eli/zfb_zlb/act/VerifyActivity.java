package com.eli.zfb_zlb.act;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class VerifyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xff0b60a0);

        setContentView(R.layout.activity_verify);

        setProgressListener(progress -> {
            if (progress == 100) {
                LinearLayout container = findViewById(R.id.header_container);
                container.setBackgroundColor(0xFF0E85E1);

                ((ImageView) container.getChildAt(0)).setImageTintList(ColorStateList.valueOf(0xffffffff));
                ((TextView) container.getChildAt(1)).setTextColor(0xffffffff);

                findViewById(R.id.verity_center).setVisibility(View.VISIBLE);
                findViewById(R.id.verify_confirm).setVisibility(View.VISIBLE);
            }
        });
    }

    public void startVerify(View v) {
        startActivityForResult(new Intent(this, VerifyConfirmActivity.class), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("elifli", "VerifyActivity requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == 100 && resultCode == 105) {
            setResult(106);
            finish();
        }
    }
}
