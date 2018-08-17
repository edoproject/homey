package net.edoproject.loco;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import net.edoproject.loco.databinding.SummaryCategoryBinding;

public class SummaryCategoriesAdapter extends RecyclerView.Adapter<SummaryCategoriesAdapter.ViewHolder> {
    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SummaryCategoryBinding binding;

        public ViewHolder(SummaryCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }

    public SummaryCategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public SummaryCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SummaryCategoryBinding binding
                = SummaryCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new SummaryCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SummaryCategoriesAdapter.ViewHolder holder, int position) {
        if ( position >= categories.size() ) {
            return;
        }

        Category category = categories.get(position);
        holder.bind(category);

        RecyclerView.LayoutManager itemsLayoutManager
                = new LinearLayoutManager(holder.binding.summaryItems.getContext());
        holder.binding.summaryItems.setLayoutManager(itemsLayoutManager);

        SummaryItemsAdapter itemsAdapter = new SummaryItemsAdapter(category.getItems());
        holder.binding.summaryItems.setAdapter(itemsAdapter);

        holder.binding.summaryCategoryImage.setImageDrawable(
                holder.binding.summaryCategoryRow.getResources().getDrawable(
                        guessImage(
                                holder.binding.summaryCategoryName.getText().toString())));

        if (position == categories.size() - 1) {
            ViewGroup.MarginLayoutParams params
                    = (ViewGroup.MarginLayoutParams) holder.binding.summaryCategoryRow.getLayoutParams();
            params.bottomMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    128, holder.binding.summaryCategoryRow.getContext().getResources().getDisplayMetrics());
            holder.binding.summaryCategoryRow.setLayoutParams(params);
        }

    }

    public int guessImage(String name) {
        switch (name) {
            case "Items to buy":
                return R.drawable.ic_icon_trolley;
            case "Dupplicates to keep":
                return R.drawable.ic_take_with_me;
            case "To donate":
                return R.drawable.ic_give_to_other;
            default:
                return R.drawable.ic_trash;
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}