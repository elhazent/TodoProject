package education.elhazent.com.todoapp.view;

import java.util.List;

import education.elhazent.com.todoapp.base.BaseView;
import education.elhazent.com.todoapp.model.ResponseTodo;

public interface MainView extends BaseView {
    void getData(List<ResponseTodo> data);
    void error(String msg);
    void succsessAdd(String msg);


}
