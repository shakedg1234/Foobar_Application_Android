package com.example.foobarapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foobarapplication.R;
import com.example.foobarapplication.entities.Post;

import java.util.List;
import java.util.Locale;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    private OnItemClickListener listener;
    private boolean isDarkMode;
    private final LayoutInflater mInflater;
    private List<Post> posts;
    private Context context;


    public interface OnItemClickListener {

        void onShareClick(View v);

        void onLikeClick(Post id, TextView likesTextView);

        void onCommentClick(int postId);

        void onOptionClick(View v, int id);

        void onPictureClick(View v, String userId, String profileImg);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final TextView tvContent;
        private final ImageView ivPic;
        private final ImageButton shareButton;
        private final ImageButton likeButton;
        private final ImageButton commentButton;
        private final ImageView post_option;
        private final TextView likesTextView;
        private final ImageButton profilePicture;
        private final TextView date;


        private PostViewHolder(View itemView, boolean isDarkMode) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            shareButton = itemView.findViewById(R.id.shareButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            commentButton = itemView.findViewById(R.id.commentButton);
            post_option = itemView.findViewById(R.id.post_options);
            likesTextView = itemView.findViewById(R.id.likes);
            this.profilePicture = itemView.findViewById(R.id.profile_picture);
            this.date = itemView.findViewById(R.id.date);
        }
    }

    public PostsListAdapter(Context context, boolean isDarkMode) {
        mInflater = LayoutInflater.from(context);
        this.isDarkMode = isDarkMode;
        this.context = context;
    }

    public PostsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);
        return new PostViewHolder(itemView, isDarkMode);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor());
            holder.tvContent.setText(current.getContent());
            String imageBase64 = current.getsPic();
            String profileImageBase64 = current.getsProfilePicture();
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                String formattedBase64 = imageBase64.substring(23);
                // Decode the Base64 string into a byte array
                byte[] imageData = Base64.decode(formattedBase64, Base64.DEFAULT);
                // Convert the byte array into a Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                // Set the Bitmap to the ImageView
                holder.ivPic.setImageBitmap(bitmap);
                holder.ivPic.setVisibility(View.VISIBLE);  // Make the ImageView visible if there is an image
            } else {
                holder.ivPic.setVisibility(View.GONE);  // Hide the ImageView if there is no image
            }


            if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
                String formattedBase64 = profileImageBase64.substring(23);
                // Decode the Base64 string into a byte array
                byte[] profileImageData = Base64.decode(formattedBase64, Base64.DEFAULT);
                // Convert the byte array into a Bitmap
                Bitmap profileBitmap = BitmapFactory.decodeByteArray(profileImageData, 0, profileImageData.length);
                // Set the Bitmap to the ImageView
                holder.profilePicture.setImageBitmap(profileBitmap);
            }

            holder.date.setText(String.format(Locale.getDefault(), "%s", current.getCurrentDate()));
            holder.likesTextView.setText(String.format(Locale.getDefault(), "%d likes", current.getLikes()));

            // Use the isDarkMode parameter from the ViewHolder constructor
            if (isDarkMode) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.grey));
                holder.post_option.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.grey));
                holder.tvAuthor.setTextColor(Color.WHITE);
                holder.tvContent.setTextColor(Color.WHITE);
                // Set other dark mode styles
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.post_option.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
                holder.tvAuthor.setTextColor(Color.BLACK);
                holder.tvContent.setTextColor(Color.BLACK);
            }
        }

        //check if the shareButton was pressed
        holder.shareButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onShareClick(v);
            }
        });

        //check if the likeButton was pressed
        holder.likeButton.setOnClickListener(v -> {
            if (listener != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Post currentPost = posts.get(adapterPosition);
                    listener.onLikeClick(currentPost, holder.likesTextView);
                }
            }
        });

        //check if the commentButton was pressed
        holder.commentButton.setOnClickListener(v -> {
            if (listener != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Post currentPost = posts.get(adapterPosition);
                    listener.onCommentClick(currentPost.getId());
                }
            }
        });

        //check if the post_option (edit or delete) was pressed
        holder.post_option.setOnClickListener(v -> {
            if (listener != null) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Post currentPost = posts.get(adapterPosition);
                    listener.onOptionClick(v, currentPost.getId());
                }
            }
        });

        holder.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        Post currentPost = posts.get(adapterPosition);
                        listener.onPictureClick(v, currentPost.getAuthor(), currentPost.getProfileImg());
                    }
                }
            }
        });
    }

    public void setOnItemClickListener(PostsListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public void setPosts(List<Post> s) {
        posts = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
        notifyDataSetChanged();
    }
}
