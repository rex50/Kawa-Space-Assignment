package com.rex50.kawaspaceassignment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rex50.kawaspaceassignment.data.models.SelectedUserData;
import com.rex50.kawaspaceassignment.data.models.UsersRequest;
import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.data.source.remote.services.UsersService;
import com.rex50.kawaspaceassignment.databinding.FragHomeBinding;
import com.rex50.kawaspaceassignment.ui.home.list.UsersListAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragHome extends Fragment {

    private FragHomeViewModel viewModel;

    private FragHomeBinding binding;
    
    private UsersListAdapter usersListAdapter;

    private SelectedUserData selectedUser;

    public static FragHome newInstance() {
        return new FragHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FragHomeViewModel.class);

        initRecycler();

        initClicks();

        setupObservers();

        viewModel.getUsers(new UsersRequest(UsersService.DEFAULT_INCLUDE, 20));
    }

    private void initRecycler() {
        usersListAdapter = new UsersListAdapter(requireContext(), new ArrayList<>());
        binding.recUsers.setAdapter(usersListAdapter);
    }


    private void initClicks() {
        usersListAdapter.setOnClickListener(((position, user) -> {
            updateSelectedUser(user, position);
        }));
    }

    private void updateSelectedUser(User user, int position) {
        int lastIndex = -1;
        if(selectedUser != null) {
            // Deselect last selected user
            selectedUser.getUser().setSelected(false);
            lastIndex = selectedUser.getPosition();
        }

        // Select current user and store
        user.setSelected(true);
        selectedUser = new SelectedUserData(user, position);

        // Update UI
        usersListAdapter.notifyItemChanged(lastIndex);
        usersListAdapter.notifyItemChanged(position);
    }

    private void setupObservers() {
        viewModel.users.observe(getViewLifecycleOwner(), data -> {
            switch (data.getStatus()) {
                case LOADING:
                    showLoader(true);
                    break;
                    
                case SUCCESS:
                    showLoader(false);

                    if(selectedUser == null && data.getData() != null && data.getData().size() != 0) {
                        // No user is selected so select 1st user
                        User firstUser = data.getData().get(0);
                        firstUser.setSelected(true);
                        selectedUser = new SelectedUserData(firstUser, 0);
                    }

                    usersListAdapter.update(data.getData());

                    // TODO: Update top view pager
                    break;
                    
                case ERROR:
                    showLoader(false);
                    showToast("Problem while getting users");
                    break;
            }
        });
    }
    
    private void showLoader(boolean show) {
        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showToast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
}