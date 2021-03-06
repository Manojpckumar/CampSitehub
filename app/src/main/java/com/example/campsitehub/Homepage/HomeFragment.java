package com.example.campsitehub.Homepage;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.campsitehub.CustomViews.CustomButton;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.Api;
import com.example.campsitehub.Retrofit.RetrofitHelper;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.Utils.RecyclerViewClickInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewClickInterface {

    LinearLayout rootLayout;
    RecyclerView rcv_home;
    CustPrograssbar custPrograssbar;
    RecyclerViewClickInterface recyclerViewClickInterface;
    List<HomeViewModel> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rootLayout = view.findViewById(R.id.rootLayout);
        rcv_home = view.findViewById(R.id.rcv_home);
        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(getContext());
        Retrofit retrofit = RetrofitHelper.getClient();
        Api api = retrofit.create(Api.class);


        api.GetHomeData().enqueue(new Callback<ExampleModel>() {
            @Override
            public void onResponse(Call<ExampleModel> call, Response<ExampleModel> response) {

                list = response.body().getHomeView();

                if (list.isEmpty()) {
                    custPrograssbar.close();
                    Snackbar.make(rootLayout, "No records Found", Snackbar.LENGTH_SHORT).show();

                } else {
                    custPrograssbar.close();
                    HomePageAdapter adapter = new HomePageAdapter(list, getContext() );
                    rcv_home.setLayoutManager(new LinearLayoutManager(getContext()));

                    rcv_home.setAdapter(adapter);


                }

            }

            @Override
            public void onFailure(Call<ExampleModel> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position, String chk) {



    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}