package education.elhazent.com.todoapp.base;

import android.view.View;

public interface BasePresenter<T extends BaseView> {

    void onAttach(T view);
    void onDetach();

}
