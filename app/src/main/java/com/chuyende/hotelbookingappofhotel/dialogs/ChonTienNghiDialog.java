package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.adapters.ChonTienNghiAdapter;
import com.chuyende.hotelbookingappofhotel.data_models.TienNghi;

import java.util.ArrayList;

public class ChonTienNghiDialog extends DialogFragment {

    // ChonTienNghiApdater
    public ArrayList<TienNghi> dsTienNghi = new ArrayList<TienNghi>();
    public ChonTienNghiAdapter tienNghiAdapter;

    // Views from layout
    public RecyclerView rcvChonTienNghi;
    public RecyclerView.LayoutManager layoutManager;
    public Button btnThoi, btnThem;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get layout inflater and layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_chon_tien_nghi, null);

        Toolbar toolbar = viewDialog.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.hint_text_btn_cac_tien_nghi);

        // Get all view form custom dialog
        rcvChonTienNghi = viewDialog.findViewById(R.id.rcvChonTienNghi);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnThem = viewDialog.findViewById(R.id.btnThem);

        // Fill the fake data to RecyclerView
        TienNghi tienNghi1 = new TienNghi("tien nghi 1", "icon tien nghi 1", "tien nghi 1");
        TienNghi tienNghi2 = new TienNghi("tien nghi 2", "icon tien nghi 2", "tien nghi 2");
        TienNghi tienNghi3 = new TienNghi("tien nghi 3", "icon tien nghi 3", "tien nghi 3");
        TienNghi tienNghi4 = new TienNghi("tien nghi 4", "icon tien nghi 4", "tien nghi 4");
        TienNghi tienNghi5 = new TienNghi("tien nghi 5", "icon tien nghi 5", "tien nghi 5");
        dsTienNghi.add(tienNghi1);
        dsTienNghi.add(tienNghi2);
        dsTienNghi.add(tienNghi3);
        dsTienNghi.add(tienNghi4);
        dsTienNghi.add(tienNghi5);
        dsTienNghi.add(tienNghi5);
        dsTienNghi.add(tienNghi5);
        dsTienNghi.add(tienNghi5);
        dsTienNghi.add(tienNghi5);
        dsTienNghi.add(tienNghi5);

        // Push data to ArrayList
        tienNghiAdapter = new ChonTienNghiAdapter(dsTienNghi, getContext());
        rcvChonTienNghi.setAdapter(tienNghiAdapter);

        rcvChonTienNghi.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rcvChonTienNghi.setLayoutManager(layoutManager);

        // Event handling when user tapped button Thoi and button Them
        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Them on Dialog is tapped!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Them on dialog is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set custom dialog
        builder.setView(viewDialog);

        return builder.create();
    }

}
