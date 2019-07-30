package education.elhazent.com.todoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import education.elhazent.com.todoapp.presenter.DetailPresenter;
import education.elhazent.com.todoapp.view.DetailView;

public class DetailActivity extends AppCompatActivity implements DetailView {

    public static String EXTRA_ID = "id";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_NOTE = "note";
    public static String EXTRA_FLAG = "flag";
    String title;
    String note;
    String id;

    Button btndelete;
    Button btnedit;
    TextView tvtitle;
    TextView tvnote;

    DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvtitle = findViewById(R.id.detail_title);
        tvnote = findViewById(R.id.detail_note);
        btndelete = findViewById(R.id.btn_delete);
        btnedit = findViewById(R.id.btn_edit);

        presenter = new DetailPresenter(this);
        getData();

        onClick();
    }

    private void onClick() {
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Note");
        builder.setCancelable(false);
        builder.setMessage("Do you want to delete this note?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void editData() {

        final AlertDialog dialogAdd = new AlertDialog.Builder(this).setTitle("Edit Note")
                .setPositiveButton("save", null).
                        setNegativeButton("cancel", null)
                .create();
        LayoutInflater inflater = getLayoutInflater();
        View formRegister = inflater.inflate(R.layout.edit_data_layout, null, false);
        final MainActivity.ViewHolderNote holderNote = new MainActivity.ViewHolderNote(formRegister);
        dialogAdd.setView(formRegister);
        dialogAdd.setCancelable(false);
        dialogAdd.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                holderNote.title.setText(title);
                holderNote.note.setText(note);
                Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cek validasi
                        String datatitle = holderNote.title.getText().toString().trim();
                        String datanote = holderNote.note.getText().toString().trim();
                        if (TextUtils.isEmpty(title)) {
                            holderNote.title.setError("Title cannot be empty");
                        } else if (TextUtils.isEmpty(note)) {
                            holderNote.note.setError("Note cannot be empty");
                        } else {
                            presenter.editData(datatitle, datanote, "1", dialogAdd);
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

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString(EXTRA_TITLE);
        note = bundle.getString(EXTRA_NOTE);
        id = bundle.getString(EXTRA_ID);

        tvtitle.setText(title);
        tvnote.setText(note);
    }


    @Override
    public void error(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void succsessEdit() {
        Toast.makeText(this, "succes edit data", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void succsessDelete() {
        Toast.makeText(this, "Success Delete", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
