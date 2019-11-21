package com.example.harbour.facemeetroom.widget.recycler;

import android.view.View;

public interface OnItemClickLitener {
        //RecyclerView的点击事件，将信息回调给view
        void onItemClick(View view, int position);
}
