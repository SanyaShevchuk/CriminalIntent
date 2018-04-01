package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by User on 27.03.2018.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
