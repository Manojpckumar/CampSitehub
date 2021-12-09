package com.example.campsitehub.Homepage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campsitehub.CampDetail.Example;
import com.example.campsitehub.R;
import com.example.campsitehub.Retrofit.APIClient;
import com.example.campsitehub.Retrofit.GetResult;
import com.example.campsitehub.Utils.CustPrograssbar;
import com.example.campsitehub.databinding.FragmentCampSearchBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CampSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CampSearchFragment extends Fragment implements GetResult.MyListener {

    FragmentCampSearchBinding binding;
    String edt_from, edt_to;
    CustPrograssbar custPrograssbar;
    List<CampsDate> campsDates;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CampSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CampSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CampSearchFragment newInstance(String param1, String param2) {
        CampSearchFragment fragment = new CampSearchFragment();
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

        binding = FragmentCampSearchBinding.inflate(inflater, container, false);

        Bundle b = getArguments();
        edt_from = b.getString("edt_from");
        edt_to = b.getString("edt_to");
        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(getContext());
        searchbyDate(edt_from, edt_to);

        initViews(binding);
        return binding.getRoot();
    }

    private void searchbyDate(String edt_from, String edt_to) {
        custPrograssbar.progressCreate(getContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start_date", edt_from);
            jsonObject.put("end_date", edt_to);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getCampDatebyid((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "CampsavailableBydate");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initViews(FragmentCampSearchBinding binding) {
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("CampsavailableBydate")) {
            custPrograssbar.close();
            Gson gson = new Gson();
            Example home = gson.fromJson(result.toString(), Example.class);

            campsDates = new ArrayList<>();
            campsDates.addAll(home.getResultData().getCampsDate());
            if (home.getResultData().getCampsDate().isEmpty()) {
                binding.noCamp.setVisibility(View.VISIBLE);
            } else {
                SearchAdapter adapter = new SearchAdapter(getContext(), campsDates,edt_from,edt_to);
                binding.rcvSearchCamps.setLayoutManager(new LinearLayoutManager(getContext()));

                binding.rcvSearchCamps.setAdapter(adapter);
            }

        }

    }
}