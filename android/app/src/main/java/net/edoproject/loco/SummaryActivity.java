package net.edoproject.loco;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SummaryActivity extends StatefullActivity {
    private static final String TAG = SummaryActivity.class.getSimpleName();
    private RecyclerView categoriesView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.summary_activity);

        categoriesView = findViewById(R.id.item_summary_list_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(categoriesLayoutManager);

        categoriesAdapter = new SummaryCategoriesAdapter(getState().getSummary());
        categoriesView.setAdapter(categoriesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoriesAdapter = new SummaryCategoriesAdapter(getState().getSummary());
        categoriesView.setAdapter(categoriesAdapter);
    }
}
