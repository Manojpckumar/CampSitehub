package com.example.campsitehub.Homepage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campsitehub.AddCampSite.AllCampsActivity;
import com.example.campsitehub.AddCampSite.Allamenities;
import com.example.campsitehub.Bookings.MyBookings;
import com.example.campsitehub.R;
import com.example.campsitehub.TermsPolicy.ActivityTerms;
import com.example.campsitehub.databinding.FragmentAdminHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomeFragment extends Fragment implements View.OnClickListener {

    FragmentAdminHomeBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminHomeFragment newInstance(String param1, String param2) {
        AdminHomeFragment fragment = new AdminHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        initViews(binding);

        return binding.getRoot();
    }

    private void initViews(FragmentAdminHomeBinding binding) {
        //  binding.addCamps.setOnClickListener(this);
        binding.manageBooking.setOnClickListener(this);
        //   binding.addAmenities.setOnClickListener(this);
        binding.terms.setOnClickListener(this);
        binding.allAmenBt.setOnClickListener(this);
        binding.allCamps.setOnClickListener(this);
        binding.privacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.add_camps:
//
//                startActivity(new Intent(getContext(), AddCampSite.class));
//                getActivity().finish();
//
//
//                break;

            case R.id.manage_booking:

                startActivity(new Intent(getActivity(), MyBookings.class));

                break;

//            case R.id.add_amenities:
//
//                startActivity(new Intent(getActivity(), AddAmenities.class).putExtra("key","0"));
//
//                break;


            case R.id.all_amen_bt:
                startActivity(new Intent(getActivity(), Allamenities.class));


                break;

            case R.id.all_camps:

                startActivity(new Intent(getActivity(), AllCampsActivity.class));

                break;

            case R.id.terms:

                startActivity(new Intent(getActivity(), ActivityTerms.class).putExtra("Activity","terms").putExtra("user_type","admin"));

                break;
            case R.id.privacy:

                startActivity(new Intent(getActivity(), ActivityTerms.class).putExtra("Activity","privacy").putExtra("user_type","admin"));

                break;
        }
    }
}