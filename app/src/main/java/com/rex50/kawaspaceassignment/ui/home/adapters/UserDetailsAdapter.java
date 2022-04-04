package com.rex50.kawaspaceassignment.ui.home.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rex50.kawaspaceassignment.data.models.user.User;
import com.rex50.kawaspaceassignment.databinding.ItemUserDetailsBinding;
import com.rex50.kawaspaceassignment.utils.CustomSpanStrings;

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

        holder.binding.tvGender.setText(user.getFormattedGender());

        holder.binding.tvName.setText(user.getName().getFormatted());
        CustomSpanStrings.withTextView(holder.binding.tvName)
                .applyUnderline(user.getName().getFormatted())
                .commit();

        holder.binding.tvLocation.setText(user.getLocation().getFormattedLocation());
        CustomSpanStrings.withTextView(holder.binding.tvLocation)
                .applyColor(Color.parseColor("#A259FF"), user.getLocation().getStreet().getNumber().toString())
                .applyBold(user.getLocation().getCountry())
                .commit();

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
