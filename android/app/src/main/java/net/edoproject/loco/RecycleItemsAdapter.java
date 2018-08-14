package net.edoproject.loco;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.edoproject.loco.databinding.RecycleItemBinding;
import java.util.List;

public class RecycleItemsAdapter extends RecyclerView.Adapter<RecycleItemsAdapter.ViewHolder> {
    private List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecycleItemBinding binding;

        public ViewHolder(RecycleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }

    public RecycleItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public RecycleItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleItemBinding itemBinding = RecycleItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new RecycleItemsAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position > items.size()-1){
            return;
        }
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
