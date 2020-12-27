package com.example.src.Adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.example.src.Objects.ItemModel;

import java.util.List;

public class CardStackCallbackAdapter extends DiffUtil.Callback {

    private List<ItemModel> old, baru;

    public CardStackCallbackAdapter(List<ItemModel> old, List<ItemModel> baru) {
        this.old = old;
        this.baru = baru;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).getImage().equals(baru.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old.get(oldItemPosition).equals(baru.get(newItemPosition));
    }
}
