package com.example.tinynews.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.tinynews.TinyNewsApplication;
import com.example.tinynews.databinding.FragmentHomeBinding;
import com.example.tinynews.model.Article;
import com.example.tinynews.repository.NewRepository;
import com.example.tinynews.repository.NewViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements CardStackListener{
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager cardLayoutManager;
    private final CardSwipeAdapter cardAdapter = new CardSwipeAdapter();
    private List<Article> articles;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    //this is the hidden lifecycle of android: fragment lifecycle
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View - card stack view
        cardLayoutManager = new CardStackLayoutManager(requireContext(), this); //important about interface and implement
        cardLayoutManager.setStackFrom(StackFrom.Top);


        binding.homeCardStackView.setLayoutManager(cardLayoutManager);
        binding.homeCardStackView.setAdapter(cardAdapter);

        NewRepository newRepository = new NewRepository();

        //viewModel = new HomeViewModel(newRepository); //setup the stream
        //now, this method can retain all the home fragment's view model now
        //provider: obtain my old state --> this, the current fragment
        //Factory: using old state to create the new fragment
        viewModel = new ViewModelProvider(this, new NewViewModelFactory(newRepository)).get(HomeViewModel.class);

        //set country list and change the country designation
        viewModel.setCountryInput(TinyNewsApplication.country_select);
        binding.countryShown.setText("Country/Region: " + TinyNewsApplication.country_full_name);
        binding.countryShown.setOnClickListener(view1 -> {
            NavDirections directions = HomeFragmentDirections.actionNavigationHomeToNavigationCountry();
            Navigation.findNavController(view1).navigate(directions);
        });

        //The data is coming from stream, not one time setup.
        //the two commands below can be place whatever at first since it always need to build the stream then transport data
        //build stream
        viewModel.getTopHeadlines().observe(getViewLifecycleOwner(), newsResponse -> {
            if (newsResponse != null) {
                articles = newsResponse.articles;
                cardAdapter.setArticles(articles);
            }
        });

        //clicking like and dislike button to trigger automated swiping action
        binding.homeLikeButton.setOnClickListener(v -> swipeCard(Direction.Right));
        binding.homeUnlikeButton.setOnClickListener(v -> swipeCard(Direction.Left));
        binding.homeRewindButton.setOnClickListener(v -> swipeCard(Direction.Top));



    }

    //this is the clicking like and unlike button to trigger swiping action
    private void swipeCard(Direction direction) {
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();

        cardLayoutManager.setSwipeAnimationSetting(setting);

        if(direction == Direction.Right || direction == Direction.Left) {
            binding.homeCardStackView.swipe();
        } else if(direction == Direction.Top) {
            binding.homeCardStackView.rewind();
        }

    }



    //Important!
    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if(direction == Direction.Left) {
            Article article = articles.get(cardLayoutManager.getTopPosition()-1); //layout starts at 1
            viewModel.deleteSavedArticle(article);


        } else if(direction == Direction.Right) {
            Article article = articles.get(cardLayoutManager.getTopPosition()-1); //layout starts at 1
            viewModel.setfavoriteArticle(article);

            Toast.makeText(getActivity(), "Save News Successfully!", Toast.LENGTH_SHORT).show();
        }

        //Log.d("HomeFragment", String.valueOf(cardLayoutManager.getTopPosition()));

        //pagination
        if(cardLayoutManager.getTopPosition() == articles.size()) {

            viewModel.getTopHeadlines().observe(getViewLifecycleOwner(), newsResponse -> {
                if (newsResponse != null) {
                    articles = newsResponse.articles;
                    cardAdapter.setArticles(articles);
                }
            });

        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}