package com.chuyende.hotelbookingappofhotel.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.chuyende.hotelbookingappofhotel.R;

import java.util.ArrayList;


public class DanhSachThanhToanFragment extends Fragment {
    private static final String TAG ="DanhSachThanhToanFragment";
    TextView tieuDe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_thanh_toan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Thay doi tieu de
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        tieuDe.setText("Danh sách các khách hàng đã thanh toán phòng");

        SwipeMenuListView listView = (SwipeMenuListView) getView().findViewById(R.id.listviewThanhToan);

        ArrayList<String> list = new ArrayList<>();
        list.add("Swipe1");
        list.add("Swipe2");
        list.add("Swipe3");
        list.add("Swipe4");
        list.add("Swipe5");
        list.add("Swipe6");
        list.add("Swipe7");
        list.add("Swipe8");
        list.add("Swipe9");
        list.add("Swipe10");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getContext().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));
                openItem.setBackground(new ColorDrawable(getContext().getColor(R.color.custom)));
                // set item width
                openItem.setWidth(500);
                // set item title
                openItem.setTitle("Hoàn tất thanh toán");
                // set item title fontsize
                openItem.setTitleSize(16);
                // set item title font color
                openItem.setTitleColor(Color.BLACK);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d(TAG, "onMenuItemClick: clickItem "+index);
                        Toast.makeText(getContext(), "Khach hang " + list.get(position), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), ManHinhChiTietThanhToan.class);
                        startActivity(intent);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}