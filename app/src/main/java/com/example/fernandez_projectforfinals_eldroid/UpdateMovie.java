package com.example.fernandez_projectforfinals_eldroid;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateMovie extends AppCompatActivity {

    TextInputEditText movie_id, movietitle, movie_year, movie_runtime, movie_lang, movie_rdate, movie_country;
    Button updateButton;
    ImageView updatePhotoImageView;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update);

        movie_id = findViewById(R.id.movie_id);
        movietitle = findViewById(R.id.movietitle);
        movie_year = findViewById(R.id.movie_year);
        movie_runtime = findViewById(R.id.movie_runtime);
        movie_lang = findViewById(R.id.movie_lang);
        movie_rdate = findViewById(R.id.movie_rdate);
        movie_country = findViewById(R.id.movie_country);
        updateButton = findViewById(R.id.updateButton);
        updatePhotoImageView = findViewById(R.id.updatePhotoImageView);

        if (getIntent().getExtras() != null) {
            Movie movie = (Movie) getIntent().getExtras().getParcelable("Movie");
            String myFormat = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

            movie_id.setText(movie.getmID());
            movie_rdate.setText(dateFormat.format(movie.getmRlsDt().toDate()));
            movietitle.setText(movie.getmTttl());
            movie_year.setText(movie.getmYr());
            movie_runtime.setText(movie.getmRnTm());
            movie_lang.setText(movie.getmLngg());
            movie_country.setText(movie.getmCtry());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movie.setmID(movie_id.getText().toString());
                    movie.setmTttl(movietitle.getText().toString());
                    movie.setmYr(movie_year.getText().toString());
                    movie.setmRnTm(movie_runtime.getText().toString());
                    movie.setmLngg(movie_lang.getText().toString());
                    movie.setmCtry(movie_country.getText().toString());
                    movie_rdate.setText(dateFormat.format(myCalendar.getTime()));

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Movie").document(movie.getmID()).set(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateMovie.this, "Movie successfully updated!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(UpdateMovie.this);
                                alert.setCancelable(false);
                                alert.setTitle("Error!");
                                alert.setMessage(task.getException().getLocalizedMessage());
                                alert.setPositiveButton("Okay", null);
                                alert.show();
                            }
                        }
                    });

                }
            });
        }
    }
}