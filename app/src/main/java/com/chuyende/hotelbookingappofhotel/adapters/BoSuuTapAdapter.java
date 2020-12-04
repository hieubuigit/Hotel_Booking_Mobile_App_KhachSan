package com.chuyende.hotelbookingappofhotel.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.activities.ThemPhongActivity;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

public class BoSuuTapAdapter extends RecyclerSwipeAdapter<BoSuuTapAdapter.BoSuuTapHolder> {
    private ArrayList<Bitmap> listURIBoSuuTap = ThemPhongActivity.listBitmap;
    private Context context;

    // Constructor
    public BoSuuTapAdapter(ArrayList<Bitmap> listURIBoSuuTap, Context context) {
        this.listURIBoSuuTap = listURIBoSuuTap;
        this.context = context;
    }

    @NonNull
    @Override
    public BoSuuTapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_recyclerview_bo_suu_tap_anh, parent, false);
        return  new BoSuuTapHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoSuuTapHolder holder, int position) {
        // Get bitmap from image
        Bitmap bitmap = listURIBoSuuTap.get(position);
        holder.imvBoSuuTap.setImageBitmap(bitmap);

        // Show swipe layout
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        // Drag a item from right to left
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_pull));

        // Action when swiping
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        // Event handle when user tap on tv Xoa
        holder.btnXoaAnhBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button delete a image at " + position + " is tapped!", Toast.LENGTH_SHORT).show();
            }
        });

        // Event Handle when user tap on a item on RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "A image is tapped!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listURIBoSuuTap.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeBoSuuTap;
    }

    // ViewHolder Class
    public static class BoSuuTapHolder extends RecyclerView.ViewHolder {
        ImageView imvBoSuuTap;
        TextView btnXoaAnhBoSuuTap;
        SwipeLayout swipeLayout;

        public BoSuuTapHolder(@NonNull View itemView) {
            super(itemView);
            imvBoSuuTap = itemView.findViewById(R.id.imvBoSuuTap);
            btnXoaAnhBoSuuTap = itemView.findViewById(R.id.tvXoaAnhBoSuuTap);
            swipeLayout = itemView.findViewById(R.id.swipeBoSuuTap);
        }
    }

    // Show dialog delete a image from bo suu tap
    public void showDialogDeleteAImageFromGallery(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Get layout on dialog
        //v = LayoutInflater.from(context).inflate(R.layout.custom_dialog_thong_bao_xoa, null);
    }
}
