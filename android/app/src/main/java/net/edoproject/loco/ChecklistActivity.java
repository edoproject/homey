package net.edoproject.loco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class ChecklistActivity extends StatefullActivity {
    private static final String TAG = ChecklistActivity.class.getSimpleName();
    private RecyclerView categoriesView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    private ImageView nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_checklist);

        categoriesView = findViewById(R.id.item_checklist_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(categoriesLayoutManager);

        categoriesAdapter = new CategoriesAdapter(getState().getCategories());
        categoriesView.setAdapter(categoriesAdapter);

        nextButton = findViewById(R.id.item_checklist_next);
        nextButton.setOnClickListener((view) -> {
            Intent openIntroActivity =  new Intent("android.intent.action.RECYCLE");
            startActivity(openIntroActivity);
        });
    }
}
