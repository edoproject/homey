package net.edoproject.loco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

public class PreparationActivity extends StatefullActivity {
    private static final String TAG = PreparationActivity.class.getSimpleName();
    private RecyclerView categoriesView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preparation_activity);

        categoriesView = findViewById(R.id.preparation_categories_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(categoriesLayoutManager);

        categoriesAdapter = new PreparationCategoriesAdapter(getState().getCategories());
        categoriesView.setAdapter(categoriesAdapter);

        nextButton = findViewById(R.id.item_checklist_next);
        nextButton.setOnClickListener((view) -> {
            Log.d(TAG, "Next button");
            getState().save();
            Intent openIntroActivity =  new Intent("android.intent.action.DUPLICATION_INTRO");
            startActivity(openIntroActivity);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoriesAdapter = new PreparationCategoriesAdapter(getState().getCategories());
        categoriesView.setAdapter(categoriesAdapter);
    }
}
