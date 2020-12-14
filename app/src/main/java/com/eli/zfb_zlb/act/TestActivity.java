package com.eli.zfb_zlb.act;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.eli.zfb_zlb.R;

import net.frakbot.jumpingbeans.JumpingBeans;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        // 增加跳动的点
        final TextView textView1 = findViewById(R.id.textView1);
        JumpingBeans jumpingBeans1 = JumpingBeans.with(textView1)
                .appendJumpingDots()
                .build();

        // 从第一个字符串到最后一个字符串波浪式循环跳动
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        JumpingBeans jumpingBeans2 = JumpingBeans.with(textView2)
                .makeTextJump(0, textView2.getText().length())
                .setIsWave(true)
                .setLoopDuration(3000)
                .build();

    }
}
