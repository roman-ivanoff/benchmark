package com.romanivanov.benchmark.ui.assembly;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romanivanov.benchmark.R;
import com.romanivanov.benchmark.databinding.FragmentAssemblyBinding;
import com.romanivanov.benchmark.ui.assembly.adapter.AssemblyAdapter;

public class AssemblyFragment extends Fragment implements View.OnClickListener {
    private final static String fragmentPosition = "fragmentPosition";

    private final AssemblyAdapter adapter = new AssemblyAdapter();
    private FragmentAssemblyBinding binding;
    private AssemblyViewModel viewModel;

    public static AssemblyFragment newInstance(int position) {
        final AssemblyFragment collectionFragment = new AssemblyFragment();

        final Bundle args = new Bundle();
        args.putInt(fragmentPosition, position);
        collectionFragment.setArguments(args);
        return collectionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int position = getArguments().getInt(fragmentPosition);
        viewModel = new ViewModelProvider(this, new AssemblyViewModelFactory(position))
                .get(AssemblyViewModel.class);
        viewModel.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_assembly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentAssemblyBinding.bind(view);
        binding.btnCalculateCollectionFragment.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);

        binding.rv.setLayoutManager(new GridLayoutManager(getContext(), viewModel.getSpanCount()));
        binding.rv.setAdapter(adapter);

        viewModel.itemsLiveData.observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case SUCCESS:
                    setRecyclerVisible(true);
                    adapter.submitList(listResource.data);
                    break;
                case ERROR:
                    setRecyclerVisible(false);
                    binding.etSizeCollectionFragment.setText(getResources().getText(listResource.messageId));
                    break;
                case LOADING:
                    setRecyclerVisible(false);
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setRecyclerVisible(boolean isVisible) {
        if (isVisible) {
            binding.rv.setVisibility(View.VISIBLE);
            binding.rv.animate()
                    .translationY(0)
                    .alpha(1.f)
                    .setListener(null);
        } else {
            binding.rv.animate()
                    .translationY(binding.rv.getHeight())
                    .alpha(0.f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            binding.rv.setVisibility(View.GONE);
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCalculateCollectionFragment:
                viewModel.onCalculateClicked(binding.etSizeCollectionFragment.getText().toString());
                break;
            case R.id.btnClear:
                setRecyclerVisible(false);
                viewModel.clear();
                break;
        }
    }
}