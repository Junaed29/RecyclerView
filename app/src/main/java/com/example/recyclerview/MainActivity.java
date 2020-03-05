package com.example.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    RecyclerView recyclerView;
    RecyclerAdaptar recyclerAdaptar;
    List<String> names;
    Button archivedButton;


    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        archivedButton = findViewById(R.id.archivedShowButton);

        names = new ArrayList<>();

        names.add("Iron Man");
        names.add("The Incredible Hulk");
        names.add("Iron Man 2");
        names.add("Thor");
        names.add("Captain America: The First Avenger");
        names.add("Marvel's The Avengers");
        names.add("Iron Man 3");
        names.add("Thor: The Dark World");
        names.add("Captain America: The Winter Soldier");
        names.add("Guardians of the Galaxy");
        names.add("Avengers: Age of Ultron");
        names.add("Ant-Man");
        names.add("Captain America: Civil War");
        names.add("Doctor Strange");
        names.add("Guardians of the Galaxy Vol. 2");
        names.add("Spider-Man: Homecoming");
        names.add("Thor: Ragnarok");
        names.add("Black Panther");
        names.add("Avengers: Infinity War");
        names.add("Ant-Man and the Wasp");
        names.add("Captain Marvel");
        names.add("Avengers: Endgame");
        names.add("Spider-Man: Far From Home");
        names.add("Black Widow");
        names.add("The Eternals");
        names.add("Shang-Chi and the Legend of the Ten Rings");
        names.add("junaed");

        recyclerAdaptar = new RecyclerAdaptar(names,this);
        recyclerView.setAdapter(recyclerAdaptar);

        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                names.add("Junaed_1");
                names.add("Junaed_2");
                names.add("Junaed_3");
                names.add("Junaed_4");

                // Adapter notify about the data changed and add to recycler view
                // It also refresh recyclerAdapter
                recyclerAdaptar.notifyDataSetChanged();

                // it'll stop the refreshing
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        archivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (archivedMovieList.isEmpty()){
                    Toast.makeText(MainActivity.this,"No Item Found",Toast.LENGTH_SHORT).show();
                }else {
                    StringBuilder allArchivedMovies = new StringBuilder();
                    for (int i = 0; i < archivedMovieList.size(); i++){
                        allArchivedMovies.append(archivedMovieList.get(i)).append("\n");
                    }
                    Toast.makeText(MainActivity.this,allArchivedMovies.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    String deletedMovie = null;
    String archivedMovie = null;
    List<String> archivedMovieList = new ArrayList<>();

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT :
                    deletedMovie = names.get(position);
                    names.remove(position);
                    recyclerAdaptar.notifyItemRemoved(position);
                    Snackbar.make(recyclerView,deletedMovie,Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    names.add(position,deletedMovie);
                                    recyclerAdaptar.notifyItemInserted(position);
                                }
                            }).show();
                    break;

                case ItemTouchHelper.RIGHT:
                    archivedMovie = names.get(position);
                    archivedMovieList.add(archivedMovie);

                    names.remove(position);
                    recyclerAdaptar.notifyItemRemoved(position);
                    Snackbar.make(recyclerView,archivedMovie+", Archived.",Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    archivedMovieList.remove(archivedMovieList.lastIndexOf(archivedMovie));
                                    names.add(position,archivedMovie);
                                    recyclerAdaptar.notifyItemInserted(position);
                                }
                            }).show();
                    break;

            }
        }

        // Copy from 'https://github.com/xabaras/RecyclerViewSwipeDecorator'
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorGreen))
                    .addSwipeRightActionIcon(R.drawable.ic_archive_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



    // Method created by RecyclerViewClickInterface
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,names.get(position),Toast.LENGTH_SHORT).show();
    }

    // Method created by RecyclerViewClickInterface
    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(this,"Long Pressed on \n"+names.get(position),Toast.LENGTH_SHORT).show();

        //names.remove(position);

        // TODO Refresh RecyclerView
        //recyclerAdaptar.notifyDataSetChanged();

        //              OR

        // TODO Delete from RecyclerView, it's do not refresh RecyclerView
        //recyclerAdaptar.notifyItemRemoved(position);
    }
}
