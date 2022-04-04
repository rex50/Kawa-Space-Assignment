package com.rex50.kawaspaceassignment.ui.home;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.rex50.kawaspaceassignment.R;
import com.rex50.kawaspaceassignment.data.models.SelectedUserData;
import com.rex50.kawaspaceassignment.data.models.UsersRequest;
import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.data.source.remote.services.UsersService;
import com.rex50.kawaspaceassignment.databinding.FragHomeBinding;
import com.rex50.kawaspaceassignment.databinding.LayoutPopupMenuBinding;
import com.rex50.kawaspaceassignment.ui.home.adapters.UserDetailsAdapter;
import com.rex50.kawaspaceassignment.ui.home.adapters.UsersListAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragHome extends Fragment {

    private FragHomeViewModel viewModel;

    private FragHomeBinding binding;

    private ArrayList<User> users;
    
    private UsersListAdapter usersListAdapter;

    private UserDetailsAdapter userDetailsAdapter;

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

        initViewpager();

        initClicks();

        setupObservers();

        viewModel.getUsers(new UsersRequest(UsersService.DEFAULT_INCLUDE, 20));
    }

    private void initRecycler() {
        usersListAdapter = new UsersListAdapter(requireContext(), new ArrayList<>());
        binding.recUsers.setAdapter(usersListAdapter);
    }

    private void initViewpager() {
        userDetailsAdapter = new UserDetailsAdapter(new ArrayList<>());
        binding.viewPager2.setAdapter(userDetailsAdapter);
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateSelectedUser(users.get(position), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void initClicks() {
        usersListAdapter.setOnClickListener(((position, user) -> {
            updateSelectedUser(user, position);
            binding.viewPager2.setCurrentItem(position, true);
        }));

        binding.btnMenu.setOnClickListener(this::showPopup);

        binding.btnBack.setOnClickListener(v -> {
            int index = binding.viewPager2.getCurrentItem() - 1;
            if(index != -1) {
                binding.viewPager2.setCurrentItem(index, true);
                binding.recUsers.scrollToPosition(index);
                updateSelectedUser(users.get(index), index);
            }
        });

        binding.btnNext.setOnClickListener(v -> {
            int index = binding.viewPager2.getCurrentItem() + 1;
            if(index != userDetailsAdapter.getItemCount()) {
                binding.viewPager2.setCurrentItem(index, true);
                binding.recUsers.scrollToPosition(index);
                updateSelectedUser(users.get(index), index);
            }
        });
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
                    binding.btnNext.setVisibility(View.GONE);
                    binding.btnBack.setVisibility(View.GONE);
                    break;
                    
                case SUCCESS:
                    showLoader(false);
                    binding.btnNext.setVisibility(View.VISIBLE);
                    binding.btnBack.setVisibility(View.VISIBLE);
                    users = data.getData();
                    if(users == null)
                        users = new ArrayList<>();

                    if(selectedUser == null && users.size() != 0) {
                        // No user is selected so select 1st user
                        User firstUser = data.getData().get(0);
                        firstUser.setSelected(true);
                        selectedUser = new SelectedUserData(firstUser, 0);
                    }

                    usersListAdapter.update(users);
                    userDetailsAdapter.update(users);
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

    public void showPopup(View anchor) {
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LayoutPopupMenuBinding popupBinding = LayoutPopupMenuBinding.inflate(layoutInflater);

        PopupWindow popupWindow = new PopupWindow(
                popupBinding.getRoot(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        View.OnClickListener clickListener = view -> {
            showToast("Menu clicked: " + view.getTag());
            popupWindow.dismiss();
        };
        popupBinding.menuDownload.setOnClickListener(clickListener);
        popupBinding.menuPricing.setOnClickListener(clickListener);
        popupBinding.menuProduct.setOnClickListener(clickListener);
        popupBinding.btnDismiss.setOnClickListener(v -> popupWindow.dismiss());

        popupWindow.setOutsideTouchable(true);
        popupWindow.setElevation(10F);
        popupWindow.showAsDropDown(anchor, -180, -60);
    }

}