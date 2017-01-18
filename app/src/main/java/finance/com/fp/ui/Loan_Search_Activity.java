package finance.com.fp.ui;

import android.os.Bundle;
import android.view.View;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import sang.com.xdialog.MoveDialog;

public class Loan_Search_Activity extends BasisActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan__search_);
        setColor(this, getResources().getColor(R.color.white));
        initToolBar("网贷搜索");
        final View viewById = findViewById(R.id.activity_loan__search_);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void click(View view) {
        MoveDialog dialog = new MoveDialog(this);
        dialog.setContentView(R.layout.dialog_item);
        dialog.show(view);
    }

    public void click1(View view) {
        MoveDialog dialog = new MoveDialog(this);
        dialog.setContentView(R.layout.dialog_item);
        dialog.show(view);
    }

    public void click2(View view) {
        MoveDialog dialog = new MoveDialog(this);
        dialog.setContentView(R.layout.dialog_item);
        dialog.show(view);
    }

    public void click3(View view) {
        MoveDialog dialog = new MoveDialog(this);
        dialog.setContentView(R.layout.dialog_item);
        dialog.show(view);
    }

    public void click4(View view) {
        MoveDialog dialog = new MoveDialog(this);
        dialog.setContentView(R.layout.dialog_item);
        dialog.show(view);
    }
}
