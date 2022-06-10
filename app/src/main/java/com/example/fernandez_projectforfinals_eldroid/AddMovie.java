package com.example.fernandez_projectforfinals_eldroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMovie extends AppCompatActivity {

    TextInputEditText movie_id, movietitle, movie_year, movie_runtime, movie_lang, movie_rdate, movie_country;
    Button saveButton;
    ImageView addPhotoImageView;
    Uri imageUri;
    final Calendar myCalendar = Calendar.getInstance();
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);

        movie_id = findViewById(R.id.movie_id);
        movietitle = findViewById(R.id.movietitle);
        movie_year = findViewById(R.id.movie_year);
        movie_runtime = findViewById(R.id.movie_runtime);
        movie_lang = findViewById(R.id.movie_lang);
        movie_rdate = findViewById(R.id.movie_rdate);
        movie_country = findViewById(R.id.movie_country);
        saveButton = findViewById(R.id.saveButton);
        addPhotoImageView = findViewById(R.id.addPhotoImageView);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        movie_rdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMovie.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String saveID = movie_id.getText().toString();
                String saveTitle = movietitle.getText().toString();
                String saveYear = movie_year.getText().toString();
                String saveMRuntime = movie_runtime.getText().toString();
                String saveLanguage = movie_lang.getText().toString();
                Timestamp saveRDate = new Timestamp(myCalendar.getTime());
                String saveCountry = movie_country.getText().toString();

                addData(saveID, saveTitle, saveYear, saveMRuntime, saveLanguage, saveRDate, saveCountry);
            }
        });


    }

    private void addData(String saveID, String saveTitle, String saveYear, String saveMRuntime, String saveLanguage, Timestamp saveRDate, String saveCountry) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyhhmm", Locale.US);
        String id = mUser.getUid() + dateFormat.format(new Date());
        Movie game = new Movie(saveID, saveTitle, saveYear, saveMRuntime, saveLanguage, saveRDate, saveCountry);
        database.collection("Movie").document(id).set(game).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddMovie.this, "Movie successfully added!", Toast.LENGTH_SHORT).show();
                }
                else {
                    android.app.AlertDialog.Builder alert = new AlertDialog.Builder(AddMovie.this);
                    alert.setCancelable(false);
                    alert.setTitle("Error!");
                    alert.setMessage(task.getException().getLocalizedMessage());
                    alert.setPositiveButton("Okay", null);
                    alert.show();
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        movie_rdate.setText(dateFormat.format(myCalendar.getTime()));
    }
}