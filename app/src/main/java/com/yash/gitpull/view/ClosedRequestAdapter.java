package com.yash.gitpull.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.gitpull.R;
import com.yash.gitpull.model.ClosedPullRequest;

import java.util.ArrayList;

public class ClosedRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        private ArrayList<ClosedPullRequest> adapterArrayList;
        
        public ClosedRequestAdapter(Context context, ArrayList<ClosedPullRequest> closedRequestAdapters) {
            this.mContext = context;
            this.adapterArrayList = closedRequestAdapters;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            ImageView avatar;
            TextView tvUsername;
            TextView tvCreatedDate, tvClosedDate;
            ItemViewHolder(View v) {
                super(v);
                tvTitle = v.findViewById(R.id.title);
                avatar = v.findViewById(R.id.avatar);
                tvUsername = v.findViewById(R.id.username);
                tvCreatedDate = v.findViewById(R.id.created_date);
                tvClosedDate = v.findViewById(R.id.closed_date);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            viewHolder = getViewHolder(parent, inflater);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
            final ClosedPullRequest closedPullRequest = adapterArrayList.get(position);
            ItemViewHolder holder = (ItemViewHolder) h;
            holder.tvTitle.setText(closedPullRequest.getTitle());
            holder.tvUsername.setText(closedPullRequest.getUser().getLogin());
            holder.tvCreatedDate.setText(closedPullRequest.getCreated_at());
            holder.tvClosedDate.setText(closedPullRequest.getClosed_at());
            Glide.with(mContext).load(closedPullRequest.getUser().getAvatar_url())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.avatar);
        }

        @Override
        public int getItemCount() {
            return adapterArrayList == null ? 0 : adapterArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == adapterArrayList.size() - 1) ? 0 : 1;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
            RecyclerView.ViewHolder viewHolder;
            View v1 = inflater.inflate(R.layout.list_pull_items, parent, false);
            viewHolder = new ItemViewHolder(v1);
            return viewHolder;
        }


       
    }

