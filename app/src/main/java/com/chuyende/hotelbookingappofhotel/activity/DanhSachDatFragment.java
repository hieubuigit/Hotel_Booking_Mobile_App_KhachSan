package com.chuyende.hotelbookingappofhotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofhotel.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachDatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachDatFragment extends Fragment {
    TextView tieuDe;
    ListView lvDanhSachDat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachDatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachDatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachDatFragment newInstance(String param1, String param2) {
        DanhSachDatFragment fragment = new DanhSachDatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Thay doi tieu de
        tieuDe = getView().findViewById(R.id.tvTieuDe);
        tieuDe.setText("Danh sách các khách hàng đã đặt phòng");
        lvDanhSachDat = getView().findViewById(R.id.lvDanhSachDat);

        ListView listView = getView().findViewById(R.id.lvDanhSachDat);
        ArrayList<String> list = new ArrayList<>();
        list.add("Khach hang 1");
        list.add("Khach hang 2");
        list.add("Khach hang 3");
        list.add("Khach hang 4");
        list.add("Khach hang 5");
        list.add("Khach hang 6");
        list.add("Khach hang 7");
        list.add("Khach hang 8");
        list.add("Khach hang 9");
        list.add("Khach hang 10");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        lvDanhSachDat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ManHinhChiTietDat.class);
                startActivity(intent);
            }
        });
    }
}