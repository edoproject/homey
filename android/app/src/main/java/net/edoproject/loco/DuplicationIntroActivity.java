package net.edoproject.loco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class DuplicationIntroActivity extends AppCompatActivity {
    private static final String TAG = DuplicationIntroActivity.class.getSimpleName();
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duplication_intro_activity);
        getSupportActionBar().hide();

        nextButton = findViewById(R.id.duplication_intro_next);
        nextButton.setOnClickListener((view) -> {
            Log.d(TAG, "Next button");
            Intent openIntroActivity =  new Intent("android.intent.action.DUPLICATION");
            startActivity(openIntroActivity);
        });
    }
}
