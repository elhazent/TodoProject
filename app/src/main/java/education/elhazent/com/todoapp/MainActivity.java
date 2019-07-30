package education.elhazent.com.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import education.elhazent.com.todoapp.adapter.DataAdapter;
import education.elhazent.com.todoapp.model.ResponseTodo;
import education.elhazent.com.todoapp.presenter.MainPresenter;
import education.elhazent.com.todoapp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {


    DataAdapter adapter;
    List<ResponseTodo> dataNote;
    RecyclerView recyclerView;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rv_data);
        presenter = new MainPresenter(this);
        presenter.getData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataNote();
            }
        });

    }

    private void addDataNote() {
        final AlertDialog dialogAdd = new AlertDialog.Builder(this).
                setTitle("Add Note").
                //layanan untuk menampilkan view menjadi popup
                        setPositiveButton("save", null).
                        setNegativeButton("cancel", null)
                .create();
        LayoutInflater inflater = getLayoutInflater();
        View formRegister = inflater.inflate(R.layout.add_data_layout, null, false);
        final ViewHolderNote holderNote = new ViewHolderNote(formRegister);
        dialogAdd.setView(formRegister);
        dialogAdd.setCancelable(false);
        dialogAdd.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cek validasi
                        String title = holderNote.title.getText().toString().trim();
                        String note = holderNote.note.getText().toString().trim();
                        if (TextUtils.isEmpty(title)) {
                            holderNote.title.setError("Title cannot be empty");
                        } else if (TextUtils.isEmpty(note)) {
                            holderNote.note.setError("Note cannot be empty");
                        } else {
                            presenter.addNote(title, note, "1", dialogAdd);
                        }
                    }
                });
                Button buttonNegative = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAdd.dismiss();
                    }
                });
            }
        });
        dialogAdd.show();
    }

    static
    class ViewHolderNote {

        EditText title;
        EditText note;

        ViewHolderNote(View view) {
            title = view.findViewById(R.id.edt_title);
            note = view.findViewById(R.id.edt_note);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getData(List<ResponseTodo> data) {
        adapter = new DataAdapter(getApplicationContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void error(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void succsessAdd(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }



    @Override
    public void onAttachView() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onAttachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }
}
