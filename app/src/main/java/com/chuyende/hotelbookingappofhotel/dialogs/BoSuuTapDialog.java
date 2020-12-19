package com.chuyende.hotelbookingappofhotel.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.chuyende.hotelbookingappofhotel.activities.ThemPhongActivity;
import com.chuyende.hotelbookingappofhotel.adapters.BoSuuTapAdapter;

import java.util.ArrayList;
import java.util.BitSet;

import static com.chuyende.hotelbookingappofhotel.activities.TatCaPhongFragment.listBitmap;

public class BoSuuTapDialog extends DialogFragment {
    private ArrayList<Bitmap> listURIImage = listBitmap;
    public BoSuuTapAdapter boSuuTapAdapter;

    // View from layout
    public RecyclerView rcvBoSuuTap;
    public RecyclerView.LayoutManager layoutManager;
    public Button btnThoi;
    public Button btnThem;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get layout inflater and layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_bo_suu_tap, null);

        // Change title in dialog
        Toolbar toolbar = viewDialog.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tv_bo_suu_tap);

        // Get all view from layout
        rcvBoSuuTap = viewDialog.findViewById(R.id.rcvBoSuuTap);
        btnThoi = viewDialog.findViewById(R.id.btnThoi);
        btnThem = viewDialog.findViewById(R.id.btnThem);

        // Fill data to RecyclerView Bo Suu Tap
        boSuuTapAdapter = new BoSuuTapAdapter(listURIImage, getContext());
        rcvBoSuuTap.setAdapter(boSuuTapAdapter);
        boSuuTapAdapter.notifyDataSetChanged();

        rcvBoSuuTap.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rcvBoSuuTap.setLayoutManager(layoutManager);

        // Event handling when user tapped button Thoi and button Them
        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Button thoi bo suu tap on Dialog is tapped!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Button them bo suu tap on dialog is tapped!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        // Set custom dialog
        builder.setView(viewDialog);

        return builder.create();
    }
    public void refreshImage(){
        boSuuTapAdapter.notifyDataSetChanged();
    }
}
