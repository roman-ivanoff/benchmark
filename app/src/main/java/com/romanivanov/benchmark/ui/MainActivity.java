package com.romanivanov.benchmark.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.romanivanov.benchmark.R;
import com.romanivanov.benchmark.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}