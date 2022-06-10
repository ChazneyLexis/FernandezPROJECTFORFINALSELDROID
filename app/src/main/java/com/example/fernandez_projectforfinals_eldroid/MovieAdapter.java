package com.example.fernandez_projectforfinals_eldroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends BaseAdapter {

    List<Movie> movieList;
    Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        view = LayoutInflater.from(context).inflate(R.layout.movie_list, null);

        TextView movieIDTextView, movieTitleTextView, movieYearTextView, movieRuntimeTextView, movieLanguageTextView, movieReleaseDateTextView, movieCountryTextView;
        ImageView editButton, deleteButton;

        editButton = view.findViewById(R.id.editButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        movieIDTextView = view.findViewById(R.id.movieTitleTextView);
        movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
        movieYearTextView = view.findViewById(R.id.movieYearTextView);
        movieRuntimeTextView = view.findViewById(R.id.movieRuntimeTextView);
        movieLanguageTextView = view.findViewById(R.id.movieLanguageTextView);
        movieReleaseDateTextView = view.findViewById(R.id.movieReleaseDateTextView);
        movieCountryTextView = view.findViewById(R.id.movieCountryTextView);

        movieIDTextView.setText("ID: " + movieList.get(i).getmID());
        movieTitleTextView.setText("Title: " + movieList.get(i).getmTttl());
        movieReleaseDateTextView.setText("Release Date: " + dateFormat.format(movieList.get(i).getmRlsDt().toDate()));
        movieYearTextView.setText("Year: " + movieList.get(i).getmYr());
        movieRuntimeTextView.setText("Runtime: " + movieList.get(i).getmRnTm());
        movieLanguageTextView.setText("Language: " + movieList.get(i).getmLngg());
        movieCountryTextView.setText("Country: " + movieList.get(i).getmCtry());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateMovie.class);
                intent.putExtra("Movie", (Parcelable) movieList.get(i));
                context.startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setCancelable(false);
                alert.setTitle("Delete movie record");
                alert.setMessage("Are you sure to delete this movie record?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int o) {

                        db.collection("Movie").document(movieList.get(i).getmID())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        movieList.remove(i);
                                        notifyDataSetChanged();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                alert.setNegativeButton("No", null);
                alert.show();
            }
        });



        return view;
    }
}
