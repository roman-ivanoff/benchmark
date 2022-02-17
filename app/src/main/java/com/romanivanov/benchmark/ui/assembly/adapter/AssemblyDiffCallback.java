package com.romanivanov.benchmark.ui.assembly.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.romanivanov.benchmark.models.assembly.AssemblyData;

public class AssemblyDiffCallback extends DiffUtil.ItemCallback<AssemblyData> {
    @Override
    public boolean areItemsTheSame(@NonNull AssemblyData oldItem, @NonNull AssemblyData newItem) {
        return oldItem.isSameItemType(newItem);
    }

    @Override
    public boolean areContentsTheSame(@NonNull AssemblyData oldItem, @NonNull AssemblyData newItem) {
        return oldItem.equals(newItem);
    }
}
