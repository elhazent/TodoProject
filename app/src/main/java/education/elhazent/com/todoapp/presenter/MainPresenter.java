package education.elhazent.com.todoapp.presenter;

import android.support.v7.app.AlertDialog;

import java.util.List;

import education.elhazent.com.todoapp.base.BasePresenter;
import education.elhazent.com.todoapp.model.ResponseTodo;
import education.elhazent.com.todoapp.network.Injection;
import education.elhazent.com.todoapp.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements BasePresenter<MainView> {

    MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void getData() {
        Injection.getService().getAlldata().enqueue(new Callback<List<ResponseTodo>>() {
            @Override
            public void onResponse(Call<List<ResponseTodo>> call, Response<List<ResponseTodo>> response) {
                if (response.isSuccessful()) {
                    view.getData(response.body());
//                view.error("Response data success");
                }
            }

            @Override
            public void onFailure(Call<List<ResponseTodo>> call, Throwable t) {
                view.error("Response data failed");
            }
        });

    }

    public void addNote(String title, String body, String id, final AlertDialog dialog) {
        Injection.getService().postData(title, body, "1").enqueue(new Callback<ResponseTodo>() {
            @Override
            public void onResponse(Call<ResponseTodo> call, Response<ResponseTodo> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    view.succsessAdd("success add data");
                } else {
                    view.error("Error Add data");
                }
            }

            @Override
            public void onFailure(Call<ResponseTodo> call, Throwable t) {
                view.error("Error Add data");
            }
        });
    }

    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
