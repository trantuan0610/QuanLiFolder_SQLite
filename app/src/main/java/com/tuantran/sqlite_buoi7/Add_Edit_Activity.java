package com.tuantran.sqlite_buoi7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Add_Edit_Activity extends AppCompatActivity {

    TextView tv_cancel, tv_save, tv_title;
    EditText edt_name, edt_description;
    ArrayList<FolderModel> arrayList;
    String name, description;
    FolderModel folder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        initUI();

        Bundle bundle = getIntent().getExtras();
        arrayList = (ArrayList<FolderModel>) getIntent().getSerializableExtra(MainActivity.LIST_FOLDER);
        folder = (FolderModel) getIntent().getSerializableExtra(MainActivity.ITEM_FOLDER);
        if (getIntent().getAction() == MainActivity.ACTION_ADD) {
            Add();

        } else if (getIntent().getAction() == MainActivity.ACTION_EDIT) {
            Edit();

        }


    }

    private void Edit() {
        tv_title.setText("Chỉnh Sửa Thư Mục");
        edt_name.setText(folder.getName());
        edt_description.setText(folder.getDescription());
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString().trim();
                String description = edt_description.getText().toString().trim();
                folder.setName(name);
                folder.setDescription(description);
                Intent intent = new Intent(Add_Edit_Activity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MainActivity.ITEM_FOLDER, folder);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Add() {
        tv_title.setText("Thêm Thư Mục");
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edt_name.getText().toString().trim();
                description = edt_description.getText().toString().trim();
                Intent intent = getIntent();
                intent.putExtra(MainActivity.NAME_FOLDER, name);
                intent.putExtra(MainActivity.DESCRIPTION_FOLDER, description);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initUI() {
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_save = findViewById(R.id.tv_save);
        tv_title = findViewById(R.id.tv_title);
        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);

    }
}