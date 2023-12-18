package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;

public class baseActivity extends AppCompatActivity implements MenuProvider {
    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.bar, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
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
}

