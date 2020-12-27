package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment;
import com.chuyende.hotelbookingappofhotel.adapters.ChonTienNghiAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;
import com.chuyende.hotelbookingappofhotel.interfaces.ChonTienNghiCallback;

import java.util.ArrayList;
import java.util.List;

public class ChonTienNghiDialog extends DialogFragment {

    public ChonTienNghiAdapter tienNghiAdapter;

    TextView tvTieuDe;
    public RecyclerView rcvChonTienNghi;
    public RecyclerView.LayoutManager layoutManager;
    public Button btnThoi, btnThem;

    public static String cacMaTienNghi = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_chon_tien_nghi, null);

        tvTieuDe = viewDialog.findViewById(R.id.tvTieuDe);
        rcvChonTienNghi = viewDialog.findViewById(R.id.rcvChonTienNghi);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnThem = viewDialog.findViewById(R.id.btnThem);

        tvTieuDe.setText(R.string.title_dialog_them_tien_nghi);

        // Read and fill all data to RecyclerView chon tien nghi
        TatCaPhongFragment.tienNghiDB.readAllDataTienNghi(new ChonTienNghiCallback() {
            @Override
            public void onDataCallbackChonTienNghi(List<TienNghi> listTienNghis) {
                tienNghiAdapter = new ChonTienNghiAdapter((ArrayList<TienNghi>) listTienNghis, getContext());
                rcvChonTienNghi.setAdapter(tienNghiAdapter);
                rcvChonTienNghi.setHasFixedSize(true);
                tienNghiAdapter.notifyDataSetChanged();

                layoutManager = new LinearLayoutManager(getActivity());
                rcvChonTienNghi.setLayoutManager(layoutManager);

                btnThoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "Button Thoi on Dialog is tapped!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cacMaTienNghi = "";
                        for (TienNghi item : listTienNghis) {
                            if (item.getCheckTN()) {
                                cacMaTienNghi += item.getMaTienNghi() + ",";
                            }
                        }
                        Log.d("CTND=>", cacMaTienNghi);
                        //Toast.makeText(getContext(), "Button Them on dialog is tapped!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
            }
        });
        builder.setView(viewDialog);

        return builder.create();
    }
}
