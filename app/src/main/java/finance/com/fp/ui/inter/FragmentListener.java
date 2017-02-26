package finance.com.fp.ui.inter;


public interface FragmentListener {

    void onBackClikc();

    void onNextClick();

    String getPhone();
    boolean isRegister();

    void setPhone(String phone);

}
