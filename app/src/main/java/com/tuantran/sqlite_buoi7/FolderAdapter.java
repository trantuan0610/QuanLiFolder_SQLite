package com.tuantran.sqlite_buoi7;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter {
    ArrayList<FolderModel> arrayList;
    private MainActivity context;

    public FolderAdapter(MainActivity context) {
        this.context = context;
    }

    public void setData(ArrayList<FolderModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);

        return new FolderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int index = position; // vi tri
        FolderModel folder = arrayList.get(position);
        ((FolderViewHolder) holder).tv_name.setText(folder.getName());
        ((FolderViewHolder) holder).tv_description.setText(folder.getDescription());

        ((FolderViewHolder) holder).layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_Edit_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MainActivity.ITEM_FOLDER, folder);
                bundle.putSerializable(MainActivity.LIST_FOLDER, arrayList);
                intent.putExtras(bundle);
                intent.setAction(MainActivity.ACTION_EDIT);
                context.startActivityForResult(intent, MainActivity.REQUEST_CODE_EDIT_ITEM);

            }
        });

        ((FolderViewHolder) holder).ic_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showPopupMenu(((FolderViewHolder) holder).ic_option, folder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_description;
        private RelativeLayout layout_item;
        private ImageView ic_option;


        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            layout_item = itemView.findViewById(R.id.layout_item);
            ic_option = itemView.findViewById(R.id.ic_option);

        }
    }

}
