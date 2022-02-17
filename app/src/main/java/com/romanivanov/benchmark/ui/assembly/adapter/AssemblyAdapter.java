package com.romanivanov.benchmark.ui.assembly.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.romanivanov.benchmark.R;
import com.romanivanov.benchmark.databinding.CollectionItemViewBinding;
import com.romanivanov.benchmark.models.assembly.AssemblyData;

import java.util.ArrayList;
import java.util.List;

public class AssemblyAdapter extends ListAdapter<AssemblyData, AssemblyAdapter.AssemblyViewHolder> {

    public AssemblyAdapter() {
        super(new AssemblyDiffCallback());
    }

    @NonNull
    @Override
    public AssemblyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item_view, parent, false);
        return new AssemblyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssemblyViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public void submitList(@Nullable List<AssemblyData> list) {
        super.submitList(list == null ? new ArrayList<>(0) : list);
    }


    static class AssemblyViewHolder extends RecyclerView.ViewHolder {
        private final CollectionItemViewBinding collectionItemViewBinding;

        public AssemblyViewHolder(View view) {
            super(view);
            this.collectionItemViewBinding = CollectionItemViewBinding.bind(view);
        }

        public void bind(AssemblyData collectionModel) {
            collectionItemViewBinding.tvListName.setText(collectionModel.getLabelId());
            collectionItemViewBinding.tvOperationName.setText(collectionModel.getOperationNameId());
            if (collectionModel.isInProgress()) {
                collectionItemViewBinding.tvTimeInMs.setText("");
                viewGoneAnimator(collectionItemViewBinding.tvTimeInMs);
                viewVisibleAnimator(collectionItemViewBinding.pbIsLoading);
            } else {
                viewGoneAnimator(collectionItemViewBinding.pbIsLoading);
                viewVisibleAnimator(collectionItemViewBinding.tvTimeInMs);
                collectionItemViewBinding.tvTimeInMs.setText(collectionModel.getTimeInMs());
            }
        }

        private void viewVisibleAnimator(View view) {
            view.setVisibility(View.VISIBLE);
            view.setTranslationY(view.getHeight());
            view.animate()
                    .translationY(0)
                    .alpha(1.f)
                    .setListener(null);
        }

        private void viewGoneAnimator(View view) {
            view.animate()
                    .translationY(view.getHeight())
                    .alpha(0.f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.GONE);
                        }
                    });
        }

    }
}
