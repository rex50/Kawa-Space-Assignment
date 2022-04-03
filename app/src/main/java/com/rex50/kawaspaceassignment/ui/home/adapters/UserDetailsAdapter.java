package com.rex50.kawaspaceassignment.ui.home.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.databinding.ItemUserDetailsBinding;

import java.util.ArrayList;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserDetailViewHolder> {

    private ArrayList<User> users;

    public UserDetailsAdapter(ArrayList<User> users) {
        this.users = users;
    }

    public void update(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserDetailViewHolder(ItemUserDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetailViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.tvName.setText(user.getName().getFormatted());
        holder.binding.tvGender.setText(user.getFormattedGender());
        // TODO: use stringSpanBuilder for styling (color, weight) string
        holder.binding.tvLocation.setText(user.getLocation().getFormattedLocation());
        holder.binding.tvTimeZone.setText(user.getLocation().getFormattedTimezone());

        Glide.with(holder.binding.ivProfile)
                .load(user.getPicture().getMedium())
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(holder.binding.ivProfile);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    protected static class UserDetailViewHolder extends RecyclerView.ViewHolder {

        public ItemUserDetailsBinding binding;

        public UserDetailViewHolder(@NonNull ItemUserDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
