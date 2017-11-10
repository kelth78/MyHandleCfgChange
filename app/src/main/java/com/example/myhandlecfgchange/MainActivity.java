package com.example.myhandlecfgchange;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    private final String KEY_PLAYERNAME = "playerName";
    private final String KEY_PLAYERSCORE = "playerScore";

    private final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private RetainedFragment retainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        // Find retained fragment (if any)
        FragmentManager fm = getFragmentManager();
        retainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // Create fragment (if not exist)
        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            fm.beginTransaction().add(retainedFragment, TAG_RETAINED_FRAGMENT).commit();
            retainedFragment.setData("Store some data");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        // this means that this activity will not be recreated now, user is leaving it
        // or the activity is otherwise finishing
        if (isFinishing()) {
            FragmentManager fm = getFragmentManager();
            // We do not need this fragment anymore
            fm.beginTransaction().remove(retainedFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        // For us to save data before activity is destroyed
        TextView scoreView = findViewById(R.id.playerScore);
        String scoreStr = scoreView.getText().toString();
        int score = Integer.parseInt(scoreStr);
        outState.putInt(KEY_PLAYERSCORE, score);

        TextView nameView = findViewById(R.id.playerName);
        outState.putString(KEY_PLAYERNAME, nameView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        // Restore
        TextView scoreView = findViewById(R.id.playerScore);
        scoreView.setText(String.valueOf(savedInstanceState.getInt(KEY_PLAYERSCORE)));
        TextView nameView = findViewById(R.id.playerName);
        nameView.setText(savedInstanceState.getString(KEY_PLAYERNAME));
    }

    // Activity handles configuration changes by itself
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Handle screen orientation manually (Manifest.xml needs to specify)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}

