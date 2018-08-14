package net.edoproject.loco;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import net.edoproject.loco.databinding.DuplicationItemBinding;
import java.util.List;

public class DupplicationItemsAdapter extends RecyclerView.Adapter<DupplicationItemsAdapter.ViewHolder> {
    private List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private DuplicationItemBinding binding;

        public ViewHolder(DuplicationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }

    public DupplicationItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public DupplicationItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DuplicationItemBinding itemBinding = DuplicationItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new DupplicationItemsAdapter.ViewHolder(itemBinding);
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
