package com.example.baitaplythuyet3nguyenxuanhai;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private List<Contact> contacts;
    private ArrayAdapter<Contact> adapter;
    private ListView lvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUser = findViewById(R.id.lv_user);
        db = new DatabaseHandler(this);

        // --- Xóa dữ liệu cũ (nếu muốn) ---
        List<Contact> oldContacts = db.getAllContacts();
        for (Contact c : oldContacts) {
            db.deleteContact(c);
        }

        // --- Thêm dữ liệu mẫu ---
        db.addContact(new Contact("Nguyen Van A", "0360000001"));
        db.addContact(new Contact("Tran Thi B", "0360000002"));
        db.addContact(new Contact("Ly Van C", "0360000003"));
        db.addContact(new Contact("Duong Thi D", "0360000004"));

        // --- Lấy danh sách contact từ DB ---
        loadContacts();

        // --- Long click để xóa contact ---
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = contacts.get(position);
                db.deleteContact(selectedContact);
                Toast.makeText(MainActivity.this,
                        "Deleted: " + selectedContact.getName(), Toast.LENGTH_SHORT).show();

                // Cập nhật ListView sau khi xóa
                loadContacts();
                return true; // true nghĩa là sự kiện long click đã được xử lý
            }
        });
    }

    // Hàm load contacts từ DB và cập nhật ListView
    private void loadContacts() {
        contacts = db.getAllContacts();

        // Log từng contact ra Logcat
        for (Contact c : contacts) {
            Log.d("MainActivity", c.toString());
        }

        // Dùng adapter với đối tượng Contact trực tiếp, gọi toString() để hiển thị
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                contacts);

        lvUser.setAdapter(adapter);
    }
}
