package com.example.paginationrecycleview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private List<User> mListUser;

    private boolean isLoading;
    private boolean isLastPage;
    private  int totalPage = 5;
    private int currentPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListUser = new ArrayList<>();
        rcvUser = findViewById(R.id.rcv_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter();
        rcvUser.setAdapter(userAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

        setFirstData();

        rcvUser.addOnScrollListener(new PaginationScrollListener( linearLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading = true;
                currentPage+=1;
                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }
    // load data page1
    private void setFirstData() {
        mListUser = getListUser();
        userAdapter.setData(mListUser);
        if (currentPage<totalPage){
            userAdapter.addFooterLoading();
        } else {
            isLastPage = true;
        }
    }
    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> list = getListUser();
                userAdapter.removeFooterLoading();
                mListUser.addAll(list);
                userAdapter.notifyDataSetChanged();

                isLoading = false;
                if (currentPage<totalPage){
                    userAdapter.addFooterLoading();
                } else {
                    isLastPage = true;
                }
            }
        },2000);

    }
    private List<User> getListUser() {
        Toast.makeText(this, "load data page"+ currentPage, Toast.LENGTH_SHORT).show();
        List<User> list = new ArrayList<>();
        for (int i = 1 ; i<=10;i++) {
            list.add(new User("User name "));
        }
        return list;
    }
}