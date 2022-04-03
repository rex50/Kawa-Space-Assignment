package com.rex50.kawaspaceassignment.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rex50.kawaspaceassignment.R;
import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.databinding.ItemUserListBinding;

import java.util.ArrayList;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {

    private ArrayList<User> users;

    private OnUserClickListener listener;

    private final Drawable selectedDrawable, unSelectedDrawable;

    public UsersListAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        selectedDrawable = ContextCompat.getDrawable(context, R.drawable.bg_selected);
        unSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.bg_un_selected);
    }

    public void update(ArrayList<User> users) {
        this.users = users;
        // TODO: use DiffUtil for better performance
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(ItemUserListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        // Update UI elements
        holder.binding.tvName.setText(user.getName().getTitle());
        holder.binding.cardPage.setBackground(user.isSelected() ? selectedDrawable : unSelectedDrawable);
        if (listener != null) {
            holder.binding.cardPage.setOnClickListener(v -> listener.onClicked(position, user));
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    protected static class UserViewHolder extends RecyclerView.ViewHolder {

        public ItemUserListBinding binding;

        public UserViewHolder(@NonNull ItemUserListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnUserClickListener {
        void onClicked(int position, User user);
    }
}
