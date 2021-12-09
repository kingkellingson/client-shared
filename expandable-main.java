package edu.byu.cs.expandablelistviewexample.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.byu.cs.expandablelistviewexample.R;
import edu.byu.cs.expandablelistviewexample.model.DataGenerator;
import edu.byu.cs.expandablelistviewexample.model.HikingTrail;
import edu.byu.cs.expandablelistviewexample.model.SkiResort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        DataGenerator dataGenerator = new DataGenerator();
        List<SkiResort> skiResorts = dataGenerator.getSkiResorts();
        List<HikingTrail> hikingTrails = dataGenerator.getHikingTrails();

        expandableListView.setAdapter(new ExpandableListAdapter(skiResorts, hikingTrails));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int SKI_RESORT_GROUP_POSITION = 0;
        private static final int HIKING_TRAIL_GROUP_POSITION = 1;

        private final List<SkiResort> skiResorts;
        private final List<HikingTrail> hikingTrails;

        ExpandableListAdapter(List<SkiResort> skiResorts, List<HikingTrail> hikingTrails) {
            this.skiResorts = skiResorts;
            this.hikingTrails = hikingTrails;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case SKI_RESORT_GROUP_POSITION:
                    return skiResorts.size();
                case HIKING_TRAIL_GROUP_POSITION:
                    return hikingTrails.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case SKI_RESORT_GROUP_POSITION:
                    return getString(R.string.skiResortsTitle);
                case HIKING_TRAIL_GROUP_POSITION:
                    return getString(R.string.hikingTrailsTitle);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case SKI_RESORT_GROUP_POSITION:
                    return skiResorts.get(childPosition);
                case HIKING_TRAIL_GROUP_POSITION:
                    return hikingTrails.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case SKI_RESORT_GROUP_POSITION:
                    titleView.setText(R.string.skiResortsTitle);
                    break;
                case HIKING_TRAIL_GROUP_POSITION:
                    titleView.setText(R.string.hikingTrailsTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case SKI_RESORT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.ski_resort_item, parent, false);
                    initializeSkiResortView(itemView, childPosition);
                    break;
                case HIKING_TRAIL_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.hiking_trail_item, parent, false);
                    initializeHikingTrailView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializeSkiResortView(View skiResortItemView, final int childPosition) {
            TextView resortNameView = skiResortItemView.findViewById(R.id.skiResortTitle);
            resortNameView.setText(skiResorts.get(childPosition).getName());

            TextView resortLocationView = skiResortItemView.findViewById(R.id.skiResortLocation);
            resortLocationView.setText(skiResorts.get(childPosition).getLocation());

            skiResortItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, getString(R.string.skiResortToastText, skiResorts.get(childPosition).getName()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void initializeHikingTrailView(View hikingTrailItemView, final int childPosition) {
            TextView trailNameView = hikingTrailItemView.findViewById(R.id.hikingTrailTitle);
            trailNameView.setText(hikingTrails.get(childPosition).getName());

            TextView trailLocationView = hikingTrailItemView.findViewById(R.id.hikingTrailLocation);
            trailLocationView.setText(hikingTrails.get(childPosition).getLocation());

            TextView trailDifficulty = hikingTrailItemView.findViewById(R.id.hikingTrailDifficulty);
            trailDifficulty.setText(hikingTrails.get(childPosition).getDifficulty());

            hikingTrailItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, getString(R.string.hikingTrailToastText, hikingTrails.get(childPosition).getName()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
