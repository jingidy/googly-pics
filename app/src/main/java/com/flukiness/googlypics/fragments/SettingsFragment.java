package com.flukiness.googlypics.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flukiness.googlypics.R;

/**
 * Created by Jing Jin on 9/21/14.
 */
public class SettingsFragment extends DialogFragment {
    private static final String title = "Options";

    public static SettingsFragment newInstance() {
        SettingsFragment frag = new SettingsFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        getDialog().setTitle(title);

        final Button btnSettingsOk = (Button) view.findViewById(R.id.btnSettingsOk);
        btnSettingsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndDismiss();
            }
        });
        return view;
    }

    public void saveAndDismiss() {
        dismiss();
    }
}
