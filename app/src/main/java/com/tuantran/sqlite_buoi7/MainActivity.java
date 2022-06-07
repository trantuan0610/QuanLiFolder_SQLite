package com.tuantran.sqlite_buoi7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_ITEM = 123;
    public static final int REQUEST_CODE_EDIT_ITEM = 1234;

    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";

    public static final String ITEM_FOLDER = "item_folder";
    public static final String LIST_FOLDER = "list_folder";

    public static final String NAME_FOLDER = "name";
    public static final String DESCRIPTION_FOLDER = "description";


    RecyclerView rcv_folder;
    public ArrayList<FolderModel> arrayList = new ArrayList<>();
    Toolbar toolbar;
    TextView tv_add;
    FolderAdapter adapter;
    DataBaseFolder dataBaseFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        dataBaseFolder = new DataBaseFolder(this);
        adapter = new FolderAdapter(this);
        rcv_folder.setAdapter(adapter);
        rcv_folder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setToolbar();
        loadData();
        adapter.setData(arrayList);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivityAddItem();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            String name = data.getExtras().getString(NAME_FOLDER);
            String description = data.getExtras().getString(DESCRIPTION_FOLDER);
            FolderModel folder = new FolderModel(name, description);
            dataBaseFolder.insertFolder(folder);
            loadData();
            Toast.makeText(this, "Thêm thư mục thành công", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            FolderModel folder = (FolderModel) bundle.getSerializable(ITEM_FOLDER);
            dataBaseFolder.updateFolder(folder);
            loadData();
            Toast.makeText(this, "Sửa thư mục thành công", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadData() {
        arrayList.clear();
        arrayList.addAll(dataBaseFolder.getAllFolder());
        adapter.notifyDataSetChanged();
    }

    private void toActivityAddItem() {
        // gửi danh sách sang để thêm item vào danh sách và trả về lại danh sách
        Intent intent = new Intent(MainActivity.this, Add_Edit_Activity.class);
        intent.putExtra(LIST_FOLDER, arrayList);
        intent.setAction(ACTION_ADD);
        startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
    }

    private void setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        tv_add = findViewById(R.id.tv_add);
        rcv_folder = findViewById(R.id.recyclefolder);
    }

    void showPopupMenu(View view, FolderModel folderModel) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_option);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        Intent intent = new Intent(MainActivity.this, Add_Edit_Activity.class);
                        intent.putExtra(ITEM_FOLDER, folderModel);
                        intent.putExtra(LIST_FOLDER, arrayList);
                        intent.setAction(ACTION_EDIT);
                        startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
                        return true;
                    case R.id.menu_delete:
                        DialogDeleteItemFolder(folderModel);

                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void DialogDeleteItemFolder(FolderModel folder) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("BẠn có muốn cóa folder này?");
        dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataBaseFolder.deleteFolder(folder);
                loadData();
            }
        });
        dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

}