package net.edoproject.loco;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.edoproject.loco.databinding.RecycleCategoryBinding;
import java.util.List;

public class RecycleCategoriesAdapter extends RecyclerView.Adapter<RecycleCategoriesAdapter.ViewHolder> {
    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleCategoryBinding binding;

        public ViewHolder(RecycleCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }

    public RecycleCategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public RecycleCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleCategoryBinding binding
                = RecycleCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new RecycleCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecycleCategoriesAdapter.ViewHolder holder, int position) {
        if (position >= categories.size()) {
            return;
        }

        Category category = categories.get(position);
        holder.bind(category);

        RecyclerView.LayoutManager itemsLayoutManager
                = new LinearLayoutManager(holder.binding.recycleItems.getContext());
        holder.binding.recycleItems.setLayoutManager(itemsLayoutManager);

        RecycleItemsAdapter itemsAdapter = new RecycleItemsAdapter(category.getItems());
        holder.binding.recycleItems.setAdapter(itemsAdapter);

        if (position == categories.size() - 1) {
            ViewGroup.MarginLayoutParams params
                    = (ViewGroup.MarginLayoutParams) holder.binding.recycleCategoryRow.getLayoutParams();
            params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    128, holder.binding.recycleCategoryRow.getContext().getResources().getDisplayMetrics());
            holder.binding.recycleCategoryRow.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}