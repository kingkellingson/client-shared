package edu.byu.cs.recyclerviewexamle_mutipleviewtypes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.byu.cs.recyclerviewexamle_mutipleviewtypes.model.DataGenerator;
import edu.byu.cs.recyclerviewexamle_mutipleviewtypes.model.HikingTrail;
import edu.byu.cs.recyclerviewexamle_mutipleviewtypes.model.SkiResort;

public class MainActivity extends AppCompatActivity {

    private static final int SKI_RESORT_ITEM_VIEW_TYPE = 0;
    private static final int HIKING_TRAIL_ITEM_VIEW_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        DataGenerator generator = new DataGenerator();
        List<SkiResort> skiResorts = generator.getSkiResorts();
        List<HikingTrail> hikingTrails = generator.getHikingTrails();

        UtahOutdoorsAdapter adapter = new UtahOutdoorsAdapter(skiResorts, hikingTrails);
        recyclerView.setAdapter(adapter);
    }

    private class UtahOutdoorsAdapter extends RecyclerView.Adapter<UtahOutdoorsViewHolder> {
        private final List<SkiResort> skiResorts;
        private final List<HikingTrail> hikingTrails;

        UtahOutdoorsAdapter(List<SkiResort> skiResorts, List<HikingTrail> hikingTrails) {
            this.skiResorts = skiResorts;
            this.hikingTrails = hikingTrails;
        }

        @Override
        public int getItemViewType(int position) {
            return position < skiResorts.size() ? SKI_RESORT_ITEM_VIEW_TYPE : HIKING_TRAIL_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public UtahOutdoorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if(viewType == SKI_RESORT_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.ski_resort_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.hiking_trail_item, parent, false);
            }

            return new UtahOutdoorsViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull UtahOutdoorsViewHolder holder, int position) {
            if(position < skiResorts.size()) {
                holder.bind(skiResorts.get(position));
            } else {
                holder.bind(hikingTrails.get(position - skiResorts.size()));
            }
        }

        @Override
        public int getItemCount() {
            return skiResorts.size() + hikingTrails.size();
        }
    }

    private class UtahOutdoorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView location;
        private final TextView difficulty;

        private final int viewType;
        private SkiResort skiResort;
        private HikingTrail hikingTrail;

        UtahOutdoorsViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if(viewType == SKI_RESORT_ITEM_VIEW_TYPE) {
                name = itemView.findViewById(R.id.skiResortTitle);
                location = itemView.findViewById(R.id.skiResortLocation);
                difficulty = null;
            } else {
                name = itemView.findViewById(R.id.hikingTrailTitle);
                location = itemView.findViewById(R.id.hikingTrailLocation);
                difficulty = itemView.findViewById(R.id.hikingTrailDifficulty);
            }
        }

        private void bind(SkiResort skiResort) {
            this.skiResort = skiResort;
            name.setText(skiResort.getName());
            location.setText(skiResort.getLocation());
        }

        private void bind(HikingTrail hikingTrail) {
            this.hikingTrail = hikingTrail;
            name.setText(hikingTrail.getName());
            location.setText(hikingTrail.getLocation());
            difficulty.setText(hikingTrail.getDifficulty());
        }

        @Override
        public void onClick(View view) {
            if(viewType == SKI_RESORT_ITEM_VIEW_TYPE) {
                // This is were we could pass the skiResort to a ski resort detail activity

                Toast.makeText(MainActivity.this, String.format("Enjoy skiing %s!",
                        skiResort.getName()), Toast.LENGTH_SHORT).show();
            } else {
                // This is were we could pass the hikingTrail to a hiking trail detail activity

                Toast.makeText(MainActivity.this, String.format("Enjoy hiking %s. It's %s.",
                        hikingTrail.getName(), hikingTrail.getDifficulty().toLowerCase()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
