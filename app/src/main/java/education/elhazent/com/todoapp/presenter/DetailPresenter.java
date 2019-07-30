package education.elhazent.com.todoapp.presenter;

import android.support.v7.app.AlertDialog;

import education.elhazent.com.todoapp.base.BasePresenter;
import education.elhazent.com.todoapp.model.ResponseTodo;
import education.elhazent.com.todoapp.network.Injection;
import education.elhazent.com.todoapp.view.DetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements BasePresenter<DetailView> {


    DetailView detailView;

    public DetailPresenter(DetailView detailView) {
        this.detailView = detailView;
    }

    public void editData(String title, String body, String id, final AlertDialog dialog) {
        Injection.getService().editData(title,body,"1").enqueue(new Callback<ResponseTodo>() {
            @Override
            public void onResponse(Call<ResponseTodo> call, Response<ResponseTodo> response) {
                if (response.isSuccessful()) {
                    detailView.succsessEdit();
                    dialog.dismiss();
                } else {
                    detailView.error("Error Edit Data");
                }
            }

            @Override
            public void onFailure(Call<ResponseTodo> call, Throwable t) {
                detailView.error("Error Edit Data");

            }
        });

    }

    public void deleteData() {

        Injection.getService().deleteData().enqueue(new Callback<ResponseTodo>() {
            @Override
            public void onResponse(Call<ResponseTodo> call, Response<ResponseTodo> response) {
                if (response.isSuccessful()) {
                    detailView.succsessDelete();

                } else {
                    detailView.succsessDelete();
                    //  view.onErrorMsg("Error Edit Data");
                }
            }

            @Override
            public void onFailure(Call<ResponseTodo> call, Throwable t) {
                detailView.error("Error Edit Data");

            }
        });

    }

    @Override
    public void onAttach(DetailView view) {
        detailView = view;
    }

    @Override
    public void onDetach() {
        detailView = null;
    }
}
