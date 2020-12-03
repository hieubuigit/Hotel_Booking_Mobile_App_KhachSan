package com.chuyende.hotelbookingappofhotel.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofhotel.R;
import com.chuyende.hotelbookingappofhotel.data_models.Phong;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

public class BoSuuTapAdapter extends RecyclerView.Adapter<BoSuuTapAdapter.BoSuuTapHolder> {
    ArrayList<String> listURIBoSuuTap = new ArrayList<String>();
    Context context;

    // Constructor
    public BoSuuTapAdapter(ArrayList<String> listURIBoSuuTap, Context context) {
        this.listURIBoSuuTap = listURIBoSuuTap;
        this.context = context;
    }

    @NonNull
    @Override
    public BoSuuTapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoSuuTapHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_recyclerview_bo_suu_tap_anh, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoSuuTapHolder holder, int position) {
        String uriAImage = listURIBoSuuTap.get(position);
        holder.imvBoSuuTap.setImageResource(R.drawable.modeling);

        // Show swipe layout
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag a item from right to left
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.dragLeft));

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

        // Event handle when user tao on tv Xoa
        holder.tvXoaAnhBoSuuTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button delete a image is tapped!", Toast.LENGTH_SHORT).show();
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

    // ViewHolder Class
    public static class BoSuuTapHolder extends RecyclerView.ViewHolder {
        ImageView imvBoSuuTap;
        TextView tvXoaAnhBoSuuTap;
        SwipeLayout swipeLayout;

        public SwipeLayout getSwipeLayout() {
            return swipeLayout;
        }

        public void setSwipeLayout(SwipeLayout swipeLayout) {
            this.swipeLayout = swipeLayout;
        }

        public ImageView getImvBoSuuTap() {
            return imvBoSuuTap;
        }

        public void setImvBoSuuTap(ImageView imvBoSuuTap) {
            this.imvBoSuuTap = imvBoSuuTap;
        }

        public TextView getTvXoaAnhBoSuuTap() {
            return tvXoaAnhBoSuuTap;
        }

        public void setTvXoaAnhBoSuuTap(TextView tvXoaAnhBoSuuTap) {
            this.tvXoaAnhBoSuuTap = tvXoaAnhBoSuuTap;
        }

        public BoSuuTapHolder(@NonNull View itemView) {
            super(itemView);
            imvBoSuuTap = itemView.findViewById(R.id.imvBoSuuTap);
            tvXoaAnhBoSuuTap = itemView.findViewById(R.id.tvXoaAnhBoSuuTap);
            swipeLayout = itemView.findViewById(R.id.swipeBoSuuTap);
        }
    }

    // Show dialog delete a image from bo suu tap
    public void showDialogDeleteAImageFromGallery(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Get layout on dialog
        v = LayoutInflater.from(context).inflate(R.layout.custom_dialog_thong_bao_xoa, null);

    }
}
