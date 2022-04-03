package com.rex50.kawaspaceassignment.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.rex50.kawaspaceassignment.R;
import com.rex50.kawaspaceassignment.databinding.ActMainBinding;
import com.rex50.kawaspaceassignment.ui.home.FragHome;

public class ActMain extends AppCompatActivity {

    private ActMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadHomeFrag();
    }

    private void loadHomeFrag() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer, FragHome.newInstance());
        transaction.commit();
    }
}