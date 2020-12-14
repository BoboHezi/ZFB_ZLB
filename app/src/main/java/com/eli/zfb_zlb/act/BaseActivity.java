package com.eli.zfb_zlb.act;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.eli.zfb_zlb.R;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {

    protected int progressTime;
    private ProgressBar progressBar;
    private Timer timer;
    private boolean haveProgressbar = true;
    private boolean progressFinished;
    private boolean activityFinished;
    private ProgressListener progressListener;

    @Override
    protected void onResume() {
        super.onResume();
        if (haveProgressbar && progressBar == null) {
            progressBar = findViewById(R.id.progress_bar);
        }

        haveProgressbar = progressBar != null;

        if (haveProgressbar && !progressFinished) {
            if (progressTime <= 0) {
                progressTime = (int) (100 + Math.random() * 500);
            }
            progressBar.setProgress(0);
            onProgress(0);
            progressFinished = true;
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        progressBar.setProgress(progressBar.getProgress() + 5);
                        if (progressBar.getProgress() >= 100) {
                            timer.cancel();
                            timer.purge();
                            progressBar.setVisibility(View.GONE);
                            onProgress(100);
                        } else {
                            onProgress(progressBar.getProgress());
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, progressTime / 5);
        }
    }

    private void onProgress(int progress) {
        if (progressListener != null) {
            progressListener.onProgress(progress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            onProgress(100);
        }
    }

    protected void setProgressListener(ProgressListener listener) {
        progressListener = listener;
    }

    @Override
    public void startActivity(Intent intent) {
        if (!activityFinished) {
            super.startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (!activityFinished) {
            super.startActivityForResult(intent, requestCode);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    }

    @Override
    public void finish() {
        activityFinished = true;
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    protected void finishWithoutAnim() {
        super.finish();
    }

    public void back(View v) {
        finish();
    }

    public interface ProgressListener {
        void onProgress(int progress);
    }
}
