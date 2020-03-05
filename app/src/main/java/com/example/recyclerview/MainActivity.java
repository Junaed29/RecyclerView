package com.example.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    RecyclerView recyclerView;
    RecyclerAdaptar recyclerAdaptar;
    List<String> names;


    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

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



    }

    // Method created by RecyclerViewClickInterface
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,names.get(position),Toast.LENGTH_SHORT).show();
    }

    // Method created by RecyclerViewClickInterface
    @Override
    public void onLongItemClick(int position) {
        names.remove(position);

        // TODO Refresh RecyclerView
        recyclerAdaptar.notifyDataSetChanged();

        //              OR

        // TODO Delete from RecyclerView, it's do not refresh RecyclerView
        //recyclerAdaptar.notifyItemRemoved(position);
    }
}
