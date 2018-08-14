package net.edoproject.loco;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.edoproject.loco.databinding.DuplicationCategoryBinding;
import java.util.List;

public class DupplicationCategoriesAdapter extends RecyclerView.Adapter<DupplicationCategoriesAdapter.ViewHolder> {
    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private DuplicationCategoryBinding binding;

        public ViewHolder(DuplicationCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }

    public DupplicationCategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public DupplicationCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DuplicationCategoryBinding binding
                = DuplicationCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new DupplicationCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DupplicationCategoriesAdapter.ViewHolder holder, int position) {
        if (position >= categories.size()) {
            return;
        }

        Category category = categories.get(position);
        holder.bind(category);

        RecyclerView.LayoutManager itemsLayoutManager
                = new LinearLayoutManager(holder.binding.duplicationItems.getContext());
        holder.binding.duplicationItems.setLayoutManager(itemsLayoutManager);

        DupplicationItemsAdapter itemsAdapter = new DupplicationItemsAdapter(category.getItems());
        holder.binding.duplicationItems.setAdapter(itemsAdapter);

        if (position == categories.size() - 1) {
            ViewGroup.MarginLayoutParams params
                    = (ViewGroup.MarginLayoutParams) holder.binding.duplicationCategoryRow.getLayoutParams();
            params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    128, holder.binding.duplicationCategoryRow.getContext().getResources().getDisplayMetrics());
            holder.binding.duplicationCategoryRow.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}