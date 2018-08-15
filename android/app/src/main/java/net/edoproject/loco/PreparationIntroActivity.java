package net.edoproject.loco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class PreparationIntroActivity extends AppCompatActivity {
    private static final String TAG = PreparationIntroActivity.class.getSimpleName();
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparation_intro_activity);
        getSupportActionBar().hide();

        nextButton = findViewById(R.id.preparation_intro_next);
        nextButton.setOnClickListener((view) -> {
            Log.d(TAG, "Next button");
            Intent openIntroActivity =  new Intent("android.intent.action.PREPARATION");
            startActivity(openIntroActivity);
        });
    }
}
