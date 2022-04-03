package com.rex50.kawaspaceassignment.ui.home.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
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

    @ColorInt
    private final int colorWhite, colorRed, colorBlack;

    public UsersListAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        selectedDrawable = ContextCompat.getDrawable(context, R.drawable.bg_selected);
        unSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.bg_un_selected);
        colorWhite = ContextCompat.getColor(context, R.color.white);
        colorBlack = ContextCompat.getColor(context, R.color.black);
        colorRed = ContextCompat.getColor(context, R.color.redColor);
    }

    public void update(ArrayList<User> users) {
        if(users == null)
            users = new ArrayList<>();

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

        // Bind UI elements
        String formattedName = String.format("%s. %s %s", user.getName().getTitle(), user.getName().getFirst(), user.getName().getLast());
        holder.binding.tvName.setText(formattedName);
        holder.binding.cardPage.setBackground(user.isSelected() ? selectedDrawable : unSelectedDrawable);
        holder.binding.tvNationality.setText(user.getNat());
        holder.binding.tvGender.setText(user.getGender());
        holder.binding.tvEmail.setText(user.getEmail());

        // Change colors of UI based on select state
        if(user.isSelected()) {
            holder.binding.cardPage.setBackground(selectedDrawable);
            holder.binding.tvName.setTextColor(colorWhite);
            holder.binding.tvEmail.setTextColor(colorWhite);
            holder.binding.tvDot.setTextColor(colorWhite);
            holder.binding.tvGender.setTextColor(colorWhite);
            holder.binding.tvNationality.setTextColor(colorWhite);
        } else {
            holder.binding.cardPage.setBackground(unSelectedDrawable);
            holder.binding.tvName.setTextColor(colorBlack);
            holder.binding.tvEmail.setTextColor(colorRed);
            holder.binding.tvDot.setTextColor(colorBlack);
            holder.binding.tvGender.setTextColor(colorBlack);
            holder.binding.tvNationality.setTextColor(colorBlack);
        }

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
