package com.eli.zfb_zlb.act;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eli.zfb_zlb.R;
import com.eli.zfb_zlb.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

import eli.avocado.utils.SystemUtils;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    private EditText mSearchEd;

    private RecyclerView mSearchList;

    private ImageView mSearchBg;

    private String mInput;

    private String[] mSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setColor(this, 0xffb0b0b0);

        setContentView(R.layout.activity_search);

        mSearchEd = findViewById(R.id.search_et);
        mSearchEd.requestFocus();

        mSearchList = findViewById(R.id.result_list);
        mSearchList.setAdapter(new ResultAdapter());
        mSearchList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBg = findViewById(R.id.search_bg);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mSearchEd, InputMethodManager.SHOW_FORCED);
                });
            }
        }, 100);

        mSearchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: " + s.toString());

                String[] selected = null;
                if (!TextUtils.isEmpty(s.toString())) {
                    selected = new String[]{
                            "浙里办", "浙里办杭州市", "浙里办杭州校园健身"
                    };
                }
                handleInput(s.toString(), selected);
            }
        });
    }

    private void handleInput(String input, String[] selected) {
        mInput = input;
        mSelected = selected;

        if (mSelected == null || mSelected.length == 0) {
            mSearchBg.setVisibility(View.VISIBLE);
        } else {
            mSearchBg.setVisibility(View.GONE);
        }

        mSearchList.getAdapter().notifyDataSetChanged();
    }

    public void search(View v) {
        handleInput(mInput, null);
        mSearchBg.setImageResource(R.drawable.zfb_search_rst);

        SystemUtils.hideKeypad(mSearchEd);

        mSearchBg.setOnClickListener(v1 -> {
            startActivity(new Intent(this, ZlbHomeActivity.class));
        });
    }

    @Override
    public void finish() {
        super.finish();
        SystemUtils.hideKeypad(mSearchEd);
    }

    class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {

        @NonNull
        @Override
        public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ResultHolder(LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.result_holder, parent, false));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
            TextView rstText = holder.itemView.findViewById(R.id.rst_text);
            ImageView rstImage = holder.itemView.findViewById(R.id.rst_image);
            rstImage.setImageTintList(ColorStateList.valueOf(0xff777777));

            String text = mSelected[position];
            SpannableStringBuilder sb = new SpannableStringBuilder(text);
            sb.setSpan(new ForegroundColorSpan(0xff1d77f2), 0, mInput.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            rstText.setText(sb);

            holder.itemView.setOnClickListener(v -> search(v));
        }

        @Override
        public int getItemCount() {
            return mSelected != null ? mSelected.length : 0;
        }

        class ResultHolder extends RecyclerView.ViewHolder {
            public ResultHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
