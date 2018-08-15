package net.edoproject.loco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

public class DupplicationActivity extends StatefullActivity {
    private static final String TAG = DupplicationActivity.class.getSimpleName();
    private RecyclerView categoriesView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.duplication_activity);

        categoriesView = findViewById(R.id.duplication_categories_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(categoriesLayoutManager);

        categoriesAdapter = new DupplicationCategoriesAdapter(getState().getDupplicates());
        categoriesView.setAdapter(categoriesAdapter);

        nextButton = findViewById(R.id.duplication_next);
        nextButton.setOnClickListener((view) -> {
            Log.d(TAG, "Next button");
            Intent openIntroActivity =  new Intent("android.intent.action.SUMMARY");
            startActivity(openIntroActivity);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoriesAdapter = new DupplicationCategoriesAdapter(getState().getDupplicates());
        categoriesView.setAdapter(categoriesAdapter);
    }
}
