package dut.t2.travelhepler.ui.main.more.profile.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import dut.t2.travelhepler.R;

import java.util.List;

public class DialogIOSSheet extends Dialog implements DialogInterface {

    public DialogIOSSheet(Context context) {
        super(context, R.style.ios_sheet_style);
    }

    public static class Builder {
        private DialogIOSSheet mIosSheetDialog;
        private Context mContext;
        private CharSequence mTitle;
        private DialogIOSSheet.SheetItem[] mItems;
        private OnClickListener mOnClickListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public DialogIOSSheet.Builder setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public DialogIOSSheet.Builder setData(List<SheetItem> items, OnClickListener listener) {
            this.mItems = new DialogIOSSheet.SheetItem[items.size()];
            for (int i = 0, len = items.size(); i < len; i++) {
                DialogIOSSheet.SheetItem item = items.get(i);
                mItems[i] = new DialogIOSSheet.SheetItem(item.name);
            }
            this.mOnClickListener = listener;
            return this;
        }

        public DialogIOSSheet create() {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View sheetView = inflater.inflate(R.layout.dialog_ios_sheet, null);
            mIosSheetDialog = new DialogIOSSheet(mContext);

            TextView tvTitle = sheetView.findViewById(R.id.title);
            LinearLayout message_layout = sheetView.findViewById(R.id.message_layout);
            Button btn_cancel = sheetView.findViewById(R.id.btn_flex_cancel);

            // if title is null, set tvTitle visibility GONE,and set first item background as top
            if (TextUtils.isEmpty(mTitle)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setText(mTitle);
            }

            for (int i = 0, len = mItems.length; i < len; i++) {
                View itemView = inflater.inflate(R.layout.dialog_ios_sheet_item, message_layout, false);
                Button btnItem = itemView.findViewById(R.id.btn_item);
                btnItem.setText(mItems[i].name);

                //if title is null, set tvTitle visibility GONE,and set first item background as top
                if (i == 0 && TextUtils.isEmpty(mTitle)) {
                    View line = itemView.findViewById(R.id.line);
                    line.setVisibility(View.GONE);
                    btnItem.setBackgroundResource(com.ligl.android.widget.iosdialog.R.drawable.iossheet_top_btn_selector);
                }
                if (i == mItems.length - 1) {
                    btnItem.setBackgroundResource(com.ligl.android.widget.iosdialog.R.drawable.iossheet_bottom_btn_selector);
                }

                final int itemIndex = i;
                btnItem.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick(mIosSheetDialog, itemIndex);
                        }
                        mIosSheetDialog.dismiss();
                    }
                });
                message_layout.addView(itemView);
            }

            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mIosSheetDialog.dismiss();
                }
            });

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);

            Window window = mIosSheetDialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams wml = window.getAttributes();
            wml.width = metrics.widthPixels;
            wml.gravity = Gravity.BOTTOM;
            wml.y = 0;
            window.setAttributes(wml);
            sheetView.setMinimumWidth(metrics.widthPixels);


            ViewGroup.LayoutParams vgl = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int maxHeight = (int) (metrics.heightPixels * 0.7);

            sheetView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int dialogMeasureHeight = sheetView.getMeasuredHeight();
            if (dialogMeasureHeight > maxHeight) {
                vgl.height = maxHeight;
            }
            mIosSheetDialog.setContentView(sheetView, vgl);
            return mIosSheetDialog;
        }

        public DialogIOSSheet show() {
            mIosSheetDialog = create();
            mIosSheetDialog.show();
            return mIosSheetDialog;
        }
    }

    public static final class SheetItem {

        public String name;

        public SheetItem() {
        }

        public SheetItem(String name) {
            this.name = name;
        }

    }
}