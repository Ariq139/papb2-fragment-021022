package com.example.fragmentexample1updated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {

    private Button mButton;
    private boolean isFragmentDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";
    private int mCurrentChoice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    openFragment();
                } else {
                    closeFragment();
                }
            }
        });

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
        }
    }

    public void openFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SimpleFragment simpleFragment = SimpleFragment.newInstance(mCurrentChoice);

        fragmentTransaction.add(R.id.fragment, simpleFragment).addToBackStack(null).commit();

        mButton.setText(R.string.close);

        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment);
        fragmentTransaction.remove(simpleFragment).commit();

        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
    }

    @Override
    public void onRadioButtonChoiceChecked(int choice) {
        mCurrentChoice = choice;
    }
}