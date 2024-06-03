package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class baseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if (id==R.id.goHome) {
            this.startActivity(new Intent(this,
                    HomeActivity.class));
            return true;
        }
        else if(id==R.id.openSettings) {
            this.startActivity(new Intent(this,
                    SettingsActivity.class));
            return true;
        }
        else
            // The user's action isn't recognized.
            return false;
    }
    protected void giveInfo(){
        DialogFragment info = new infoFragment();
        info.show(getSupportFragmentManager(), infoFragment.TAG);
    }
}

