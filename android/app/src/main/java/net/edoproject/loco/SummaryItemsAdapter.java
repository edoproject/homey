package net.edoproject.loco;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.edoproject.loco.databinding.SummaryItemBinding;
import java.util.List;

public class SummaryItemsAdapter extends RecyclerView.Adapter<SummaryItemsAdapter.ViewHolder> {
    private List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SummaryItemBinding binding;

        public ViewHolder(SummaryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }

    public SummaryItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public SummaryItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SummaryItemBinding itemBinding = SummaryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new SummaryItemsAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(SummaryItemsAdapter.ViewHolder holder, int position) {
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
