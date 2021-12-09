package edu.byu.cs.activitymenuexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem; 
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem personMenuItem = menu.findItem(R.id.personMenuItem);
        personMenuItem.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_user)
                .colorRes(R.color.colorWhite)
                .actionBarSize());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch(menu.getItemId()) {
            case R.id.fileMenuItem:
                Toast.makeText(this, getString(R.string.fileMenuSelectedMessage), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.personMenuItem:
                Toast.makeText(this, getString(R.string.personMenuSelectedMessage), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }
}
