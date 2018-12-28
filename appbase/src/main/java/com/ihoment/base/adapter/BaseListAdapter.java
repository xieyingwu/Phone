package com.ihoment.base.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by pengxianglin on 2017/3/21.
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected String TAG;
    private List<T> items;
    protected OnClickItem<T> callback;

    public BaseListAdapter() {
        TAG = this.getClass().getName();
        this.items = new ArrayList<>();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        if (items == null) return;
        this.items = items;
        notifyDataSetChanged();
    }

    public void setItemsIgnoreDataSource(List<T> items) {
        if (items == null) return;
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        if (items == null) return;
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void insertItems(List<T> items) {
        if (items == null) return;
        this.items.addAll(0, items);
        notifyItemRangeInserted(0, items.size());
    }

    public void setItemsWithoutNotify(List<T> items) {
        if (items == null) return;
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void setCallback(OnClickItem<T> callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListItemViewHolder) {
            ((ListItemViewHolder) holder).bindView(getItem(position), position);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else {
            ((ListItemViewHolder) holder).bindView(getItem(position), position, payloads);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayout(viewType), parent, false);
        return createViewHolder(v, viewType);
    }

    protected abstract int getLayout(int viewType);

    protected abstract RecyclerView.ViewHolder createViewHolder(View v, int viewType);

    public interface OnClickItem<T> {
        void onClickItem(int pos, T item, View v);

        void onDoubleClickItem(int pos, T item, View v);

        void onLongClickItem(int pos, T item, View view);
    }

    protected void onClickItem(int pos, T item, View v) {
        if (callback == null) return;
        callback.onClickItem(pos, item, v);
    }

    protected void onDoubleClickItem(int pos, T item, View v) {
        if (callback == null) return;
        callback.onDoubleClickItem(pos, item, v);
    }

    protected void onLongClickItem(int pos, T item, View v) {
        if (callback == null) return;
        callback.onLongClickItem(pos, item, v);
    }

    public abstract class ListItemViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
        public long mLastTime;
        public long mCurTime;

        private int position;

        public ListItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(@NonNull View v) {

            mLastTime = mCurTime;
            mCurTime = System.currentTimeMillis();

            if (position >= getItemCount()) {
                return;
            }
            if (mCurTime - mLastTime < 500) {
                onDoubleClickItem(position, getItem(position), v);
            } else {
                onClickItem(position, getItem(position), v);
            }

        }

        private void bindView(T item, int position) {
            this.position = position;
            bind(item, position);
        }

        public abstract void bind(T item, int position);

        private void bindView(T item, int position, List<Object> payloads) {
            this.position = position;
            bind(item, position, payloads);
        }


        public void bind(T item, int position, List<Object> payloads) {

        }

    }
}
