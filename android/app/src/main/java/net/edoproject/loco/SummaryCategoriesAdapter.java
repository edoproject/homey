package net.edoproject.loco;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SummaryCategoriesAdapter extends RecyclerView.Adapter<SummaryCategoriesAdapter.ViewHolder> {
    private List<Category> categories;
    private RecyclerView.LayoutManager itemsLayoutManager;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public TextView countView;
        public RecyclerView itemsView;
        private ImageView imageView;
        public View view;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.summaryCategoryName);
            itemsView = v.findViewById(R.id.summaryItems);
            countView = v.findViewById(R.id.summaryCount);
            imageView = v.findViewById(R.id.summaryCategoryImage);
            view = v;
        }

        public TextView getTextView() { return textView; }
        public TextView getCountView() { return countView; }
        public ImageView getImageView() { return imageView; }
        public View getCategoryView() { return view; }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SummaryCategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    public void set(List<Category> categories) {
        this.categories = categories;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SummaryCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_category, parent, false);
        return new ViewHolder(v);
    }

    private void bindViewHolderToCategory(ViewHolder holder, Category category){
        itemsLayoutManager = new LinearLayoutManager(holder.itemsView.getContext());
        holder.itemsView.setLayoutManager(itemsLayoutManager);

        SummaryItemsAdapter itemsAdapter = new SummaryItemsAdapter(category.getItems());
        holder.itemsView.setAdapter(itemsAdapter);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if ( position < categories.size() ) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Category category = categories.get(position);
            holder.getTextView().setText(category.getName());
            holder.getCountView().setText(Integer.toString(category.getItems().size()));

            Resources resources = holder.view.getResources();

            switch (category.getName()) {
                case "Items to buy":
                    holder.getImageView().setImageDrawable(resources.getDrawable(R.drawable.ic_icon_trolley));
                    break;
                case "Dupplicates":
                    holder.getImageView().setImageDrawable(resources.getDrawable(R.drawable.ic_take_with_me));
                    break;
                case "To donate":
                    holder.getImageView().setImageDrawable(resources.getDrawable(R.drawable.ic_give_to_other));
                    break;
                case "To dump":
                    holder.getImageView().setImageDrawable(resources.getDrawable(R.drawable.ic_trash));
                    break;
            }


            holder.getCategoryView().setOnClickListener((view) -> {
                if (category.isExpanded()) {
                    Toast toast = Toast.makeText(view.getContext(), "collapsing", Toast.LENGTH_SHORT);
                    toast.show();
                    category.setExpanded(false);
                    holder.itemsView.setVisibility(View.GONE);
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "expanding", Toast.LENGTH_SHORT);
                    toast.show();
                    category.setExpanded(true);
                    holder.itemsView.setVisibility(View.VISIBLE);
                }
            });

            bindViewHolderToCategory(holder, category);
        }

        if (position == categories.size() - 1) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemsView.getLayoutParams();
            params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, holder.itemsView.getContext().getResources().getDisplayMetrics());
            holder.itemsView.setLayoutParams(params);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size();
    }
}