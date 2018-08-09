package net.edoproject.loco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class StatefullActivity extends AppCompatActivity {
    private static final String TAG = StatefullActivity.class.getSimpleName();

    private State state;

    public State getState() {
        return state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Loading state");
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        state = new State(this);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: Saving state");
        super.onPause();
        state.save();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: Loading state");
        super.onResume();
        state.reload();
    }
}
