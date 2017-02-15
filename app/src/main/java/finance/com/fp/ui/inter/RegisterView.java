package finance.com.fp.ui.inter;


import android.widget.EditText;

public interface RegisterView {



    void onNextClick();

    void showEtError(EditText et, int input_dynamic);
}
