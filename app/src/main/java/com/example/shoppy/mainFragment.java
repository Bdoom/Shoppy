package com.example.shoppy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class mainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public mainFragment() {
        // Required empty public constructor
    }

    public static mainFragment newInstance() {
        mainFragment fragment = new mainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button btnSearch = rootView.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(mOnClickListener);

        Button btnSearchFromList = rootView.findViewById(R.id.btnSearchFromList);
        btnSearchFromList.setOnClickListener(mOnClickListener);

        return rootView;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            MainActivity mainActivity = (MainActivity)getActivity();
            if (v.getId() == R.id.btnSearch) {
                if (mainActivity != null)
                {
                    mainActivity.MakeRequest();
                }
            }

            if (v.getId() == R.id.btnSearchFromList)
            {
                AsyncMakeMultipleRequest asyncGetShoppingListAndStartMultipleSearch = new AsyncMakeMultipleRequest(mainActivity);
                asyncGetShoppingListAndStartMultipleSearch.execute();
            }
        }
    };

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
