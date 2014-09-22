package com.flukiness.googlypics.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.flukiness.googlypics.R;
import com.flukiness.googlypics.models.ImageSearchQuery;

/**
 * Created by Jing Jin on 9/21/14.
 */
public class SettingsFragment extends DialogFragment {

    public interface SettingsFragmentListener {
        void onSettingsFinish(ImageSearchQuery newQuery);
    }

    private static final String ARG_SEARCH_QUERY = "query";
    private static final String TITLE = "Options";

    private Switch switchSize;
    private RadioGroup rgSize;

    private Switch switchColor;
    private RadioGroup rgColor;

    private Switch switchType;
    private RadioGroup rgType;

    private Switch switchSite;
    private EditText etSiteFilter;

    ImageSearchQuery searchQuery;

    public static SettingsFragment newInstance(ImageSearchQuery searchQuery) {
        SettingsFragment frag = new SettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SEARCH_QUERY, searchQuery);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        searchQuery = new ImageSearchQuery((ImageSearchQuery)getArguments().getParcelable(ARG_SEARCH_QUERY));
        getDialog().setTitle(TITLE);
        setUpViews(view);
        return view;
    }

    public void saveAndDismiss() {
        switch(rgSize.getCheckedRadioButtonId()) {
            case R.id.rbtnSmall:
                searchQuery.size = ImageSearchQuery.SIZE.icon;
                break;
            case R.id.rbtnMedium:
                searchQuery.size = ImageSearchQuery.SIZE.medium;
                break;
            case R.id.rbtnLarge:
                searchQuery.size = ImageSearchQuery.SIZE.xxlarge;
                break;
            case R.id.rbtnHuge:
                searchQuery.size = ImageSearchQuery.SIZE.huge;
                break;
            default:
                searchQuery.size = null;
        }

        switch(rgColor.getCheckedRadioButtonId()) {
            case R.id.rbtnBlack:
                searchQuery.color = ImageSearchQuery.COLOR.black;
                break;
            case R.id.rbtnGray:
                searchQuery.color = ImageSearchQuery.COLOR.gray;
                break;
            case R.id.rbtnWhite:
                searchQuery.color = ImageSearchQuery.COLOR.white;
                break;
            case R.id.rbtnBlue:
                searchQuery.color = ImageSearchQuery.COLOR.blue;
                break;
            case R.id.rbtnTeal:
                searchQuery.color = ImageSearchQuery.COLOR.teal;
                break;
            case R.id.rbtnGreen:
                searchQuery.color = ImageSearchQuery.COLOR.green;
                break;
            case R.id.rbtnYellow:
                searchQuery.color = ImageSearchQuery.COLOR.yellow;
                break;
            case R.id.rbtnPink:
                searchQuery.color = ImageSearchQuery.COLOR.pink;
                break;
            case R.id.rbtnPurple:
                searchQuery.color = ImageSearchQuery.COLOR.purple;
                break;
            case R.id.rbtnRed:
                searchQuery.color = ImageSearchQuery.COLOR.red;
                break;
            case R.id.rbtnOrange:
                searchQuery.color = ImageSearchQuery.COLOR.orange;
                break;
            case R.id.rbtnBrown:
                searchQuery.color = ImageSearchQuery.COLOR.brown;
                break;
            default:
                searchQuery.color = null;
        }

        switch(rgType.getCheckedRadioButtonId()) {
            case R.id.rbtnFace:
                searchQuery.type = ImageSearchQuery.TYPE.face;
                break;
            case R.id.rbtnPhoto:
                searchQuery.type = ImageSearchQuery.TYPE.photo;
                break;
            case R.id.rbtnClipart:
                searchQuery.type = ImageSearchQuery.TYPE.clipart;
                break;
            case R.id.rbtnLineart:
                searchQuery.type = ImageSearchQuery.TYPE.lineart;
                break;
            default:
                searchQuery.color = null;
        }

        String siteFilter = etSiteFilter.getText().toString().trim();
        searchQuery.site = siteFilter.isEmpty() ? null : siteFilter;

        SettingsFragmentListener listener = (SettingsFragmentListener)getActivity();
        listener.onSettingsFinish(searchQuery);
        dismiss();
    }

    private void setUpViews(View container) {
        switchSize = (Switch)container.findViewById(R.id.switchSize);
        rgSize = (RadioGroup)container.findViewById(R.id.rgSize);
        switchColor = (Switch)container.findViewById(R.id.switchColor);
        rgColor = (RadioGroup)container.findViewById(R.id.rgColor);
        switchType = (Switch)container.findViewById(R.id.switchType);
        rgType = (RadioGroup)container.findViewById(R.id.rgType);
        switchSite = (Switch)container.findViewById(R.id.switchSite);
        etSiteFilter = (EditText)container.findViewById(R.id.etSiteFilter);

        Button btnSettingsOk = (Button) container.findViewById(R.id.btnSettingsOk);
        btnSettingsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndDismiss();
            }
        });

        if (searchQuery.size != null) {
            switch (searchQuery.size) {
                case icon:
                    rgSize.check(R.id.rbtnSmall);
                    break;
                case medium:
                    rgSize.check(R.id.rbtnMedium);
                    break;
                case xxlarge:
                    rgSize.check(R.id.rbtnLarge);
                    break;
                case huge:
                    rgSize.check(R.id.rbtnHuge);
                    break;
            }
        }

        if (searchQuery.color != null) {
            switch (searchQuery.color) {
                case black:
                    rgColor.check(R.id.rbtnBlack);
                    break;
                case blue:
                    rgColor.check(R.id.rbtnBlue);
                    break;
                case brown:
                    rgColor.check(R.id.rbtnBrown);
                    break;
                case gray:
                    rgColor.check(R.id.rbtnGray);
                    break;
                case green:
                    rgColor.check(R.id.rbtnGreen);
                    break;
                case orange:
                    rgColor.check(R.id.rbtnOrange);
                    break;
                case pink:
                    rgColor.check(R.id.rbtnPink);
                    break;
                case purple:
                    rgColor.check(R.id.rbtnPurple);
                    break;
                case red:
                    rgColor.check(R.id.rbtnRed);
                    break;
                case teal:
                    rgColor.check(R.id.rbtnTeal);
                    break;
                case white:
                    rgColor.check(R.id.rbtnWhite);
                    break;
                case yellow:
                    rgColor.check(R.id.rbtnYellow);
                    break;
            }
        }

        if (searchQuery.type != null) {
            switch (searchQuery.type) {
                case face:
                    rgType.check(R.id.rbtnFace);
                    break;
                case photo:
                    rgType.check(R.id.rbtnPhoto);
                    break;
                case clipart:
                    rgType.check(R.id.rbtnClipart);
                    break;
                case lineart:
                    rgType.check(R.id.rbtnLineart);
                    break;
            }
        }

        if (searchQuery.site != null && !searchQuery.site.isEmpty()) {
            etSiteFilter.setText(searchQuery.site);
        }
    }
}
