package com.bignerdranch.android.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;

public class CrimeFragment extends Fragment {

    private Crime mCrime;


    EditText mTitleField;
    Button mDateButton;
    CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);


        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @OnCheckedChanged(R.id.crime_solved)
    public void onCheckChanged(CompoundButton buttonView, boolean isChecked){
        mCrime.setSolved(isChecked);
    }

    @OnTextChanged(value = R.id.crime_title, callback =
            OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCrime.setTitle(s.toString());
    }

     
}