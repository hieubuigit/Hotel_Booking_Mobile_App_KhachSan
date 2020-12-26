package com.chuyende.hotelbookingappofhotel.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.validate.CheckTextInput;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ManHinhDangNhap extends AppCompatActivity {

    Button btnDangNhap;
    EditText edtTaiKhoan, edtMatKhau;
    TextView tvThongBao;
    Dialog dialog;
    CheckTextInput checkTextInput = new CheckTextInput(ManHinhDangNhap.this);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String TAG = "TAG";
    public static String MATKHAU_KEY = "matKhau";
    public static String TRANGTHAITK = "trangThaiTaiKhoan";
    public static String TRANGTHAITRUE = "true";
    public static String TRANGTHAIFALSE = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dialog = new Dialog(this);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taiKhoan = edtTaiKhoan.getText().toString();
                String matKhau = edtMatKhau.getText().toString();

                if(edtTaiKhoan.getText().toString().isEmpty() || edtMatKhau.getText().toString().isEmpty()) {
                    checkTextInput.checkEmpty(edtTaiKhoan, "Vui lòng nhập tài khoản");
                    checkTextInput.checkEmpty(edtMatKhau, "Vui lòng nhập mật khẩu");
                    return;
                }

                db.collection("TaiKhoanKhachSan").document(taiKhoan).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String mkhau = document.getData().get(MATKHAU_KEY).toString();
                                String trangThai = document.getData().get(TRANGTHAITK).toString();

                                if(matKhau.trim().equals(mkhau) && trangThai.equals(TRANGTHAITRUE)){
                                    Intent intent = new Intent(ManHinhDangNhap.this, MainFragment.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("taiKhoan", taiKhoan);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    tvThongBao.setText("");
                                } else if(matKhau.trim().equals(mkhau) && trangThai.equals(TRANGTHAIFALSE)) {
                                    openDialogThongBao();
                                    tvThongBao.setText("");
                                }else{
                                    tvThongBao.setText("Nhập sai tài khoản hoặc mật khẩu!");
                                }
                            } else {
                                tvThongBao.setText("Nhập sai tài khoản hoặc mật khẩu!");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }

    private void openDialogThongBao() {
        dialog.setContentView(R.layout.custom_dialog_dang_nhap);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tieuDeDialog = dialog.findViewById(R.id.tvTieuDe);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        tieuDeDialog.setText("Thông báo");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setControl() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        tvThongBao = findViewById(R.id.tvThongBao);
    }
}