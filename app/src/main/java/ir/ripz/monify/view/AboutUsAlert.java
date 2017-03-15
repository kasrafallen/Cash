package ir.ripz.monify.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ir.ripz.monify.R;

public class AboutUsAlert {

    private Context context;
    private float[] dimen;

    public AboutUsAlert(Activity context, float[] dimen) {
        this.context = context;
        this.dimen = dimen;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true).setView(getView()).setTitle("توضیحات").setMessage("1-لیست هزینه های روزانه : در این بخش می توانید لیست هزینه های هر روزی را که نیاز دارید مشاهده نمایید\n" +
                "و همینطور می توانید لیست خود را ویرایش ویا هزینه ای را به آن اضافه یا حذف کنید.\n" +
                "2-گزارش هزینه های ویژه : در این بخش می توانید در بازه های زمانی دلخواه میزان هزینه های ویژه ی خود را مشاهده کنید\n" +
                "3-گزارش همه هزینه ها : در این بخش می توانید در بازه های زمانی دلخواه لیست و مجموع همه ی هزینه های ویژه و سایر هزینه ها را به صورت یکجا مشاهده کنید\n" +
                "تغییر هزینه های ویژه : در این بخش شما میتوانید به لیست هزینه های ویژه ی خود گزینه ای را اضافه ویا از لیست حذف نمایید");
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_style;
        dialog.show();
    }

    private View getView() {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        LinearLayout box = new LinearLayout(context);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setLayoutParams(new LinearLayout.LayoutParams((int) dimen[0], -2));

        layout.addView(box);
        return layout;
    }
}
