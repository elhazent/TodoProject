package education.elhazent.com.todoapp.view;

import education.elhazent.com.todoapp.base.BaseView;

public interface DetailView extends BaseView {

    void error(String error);
    void succsessEdit();
    void succsessDelete();
}
