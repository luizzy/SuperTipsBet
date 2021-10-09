package com.winningbets.supertipsbet;

import static android.content.ContentValues.TAG;
import static com.mopub.common.Constants.TEN_SECONDS_MILLIS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/

//import com.google.android.gms.ads.AdView;


public class Elite extends Fragment implements View.OnClickListener {


    View view;
    private InterstitialAd mInterstitialAd;
    private MoPubInterstitial moPubInterstitial;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.elite, container, false);

        CardView elite_picks = view.findViewById(R.id.elite);
        CardView special = view.findViewById(R.id.special);
        CardView single = view.findViewById(R.id.single);
        CardView over = view.findViewById(R.id.over);
        CardView ht = view.findViewById(R.id.ht);
        CardView bigwin = view.findViewById(R.id.bigwin);
        CardView all_sports = view.findViewById(R.id.all_sports);
        CardView correct = view.findViewById(R.id.correct);
        CardView surprise = view.findViewById(R.id.surprise);
        //CardView telegram_join = binding.telegramJoin;

        special.setOnClickListener(this);
        single.setOnClickListener(this);
        elite_picks.setOnClickListener(this);
        over.setOnClickListener(this);
        ht.setOnClickListener(this);
        bigwin.setOnClickListener(this);
        all_sports.setOnClickListener(this);
        correct.setOnClickListener(this);
        surprise.setOnClickListener(this);


        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(false)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        AdLoader adLoader = new AdLoader.Builder(getContext(), getString(R.string.Admob_Native))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd NativeAd) {
                        if (isDetached()) {
                            NativeAd.destroy();
                            return;// Show the ad.
                        }
                        if (NativeAd.getMediaContent().hasVideoContent()) {

                            float mediaAspectRatio = NativeAd.getMediaContent().getAspectRatio();
                            float duration = NativeAd.getMediaContent().getDuration();

                        }
                        NativeAd.getMediaContent().getVideoController().setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                            @Override
                            public void onVideoStart() {
                                Log.d("Luizzy", "Video Started");
                            }

                            @Override
                            public void onVideoPlay() {
                                Log.d("Luizzy", "onVideoPlay: ");
                            }

                            @Override
                            public void onVideoPause() {
                                Log.d("Luizzy", "onVideoPause: ");
                            }

                            @Override
                            public void onVideoEnd() {
                                Log.d("Luizzy", "onVideoEnd: ");
                            }

                            @Override
                            public void onVideoMute(boolean b) {
                                Log.d("Luizzy", "onVideoMute: ");
                            }
                        });
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = view.findViewById(R.id.my_template);
                        template.setVisibility(View.VISIBLE);
                        template.setStyles(styles);
                        template.setNativeAd(NativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.d(TAG, "onAdFailedToLoad: " + adError);
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .withNativeAdOptions(adOptions)
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

        return view;


    }

    /*public void LoadAdmobInt() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(), getString(R.string.Admob_Interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d("TAG", "The ad was shown.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdImpression() {
                        Log.d("TAG", "The ad made an impression.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    private void showAdmobInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null) {
            mInterstitialAd.show(getActivity());
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }*/

    private void showMopubInt() {
        moPubInterstitial = new MoPubInterstitial(getActivity(), getString(R.string.Mopub_interstitial));
        moPubInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial moPubInterstitial) {
                if (moPubInterstitial != null && moPubInterstitial.isReady()) {
                    try {
                        if (moPubInterstitial.isReady()) {
                            moPubInterstitial.show();
                        } else {
                            // Caching is likely already in progress if `isReady()` is false.
                            // Avoid calling `load()` here and instead rely on the callbacks as suggested below.
                            Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                        }
                    } catch (Throwable e) {
                        // Do nothing, just skip and wait for ad loading
                    }
                }
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial moPubInterstitial, MoPubErrorCode moPubErrorCode) {
                final Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moPubInterstitial.load();
                    }
                }, TEN_SECONDS_MILLIS);

            }

            @Override
            public void onInterstitialShown(MoPubInterstitial moPubInterstitial) {

            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial moPubInterstitial) {

            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial moPubInterstitial) {

            }
        });
        moPubInterstitial.load();
        if (moPubInterstitial.isReady()) {
            moPubInterstitial.show();
        } else {
            // Caching is likely already in progress if `isReady()` is false.
            // Avoid calling `load()` here and instead rely on the callbacks as suggested below.
        }

    }


    @Override
    public void onStart() {
        super.onStart();
       // LoadAdmobInt();
        //  showMopBanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.elite:
                Intent intent6 = new Intent(view.getContext(), games.class);
                intent6.putExtra("title", "Elite Tips");
                intent6.putExtra("db", "stb");
                intent6.putExtra("selectedp", "elite");
                startActivity(intent6);
                //showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.special:
                Intent intent1 = new Intent(view.getContext(), games.class);
                intent1.putExtra("title", "Special Tips");
                intent1.putExtra("db", "stb");
                intent1.putExtra("selectedp", "special");
                startActivity(intent1);
                //showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.single:
                Intent intent2 = new Intent(view.getContext(), games.class);
                intent2.putExtra("title", "Single");
                intent2.putExtra("db", "stb");
                intent2.putExtra("selectedp", "single");
                startActivity(intent2);
               // showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.over:
                Intent intent3 = new Intent(view.getContext(), games.class);
                intent3.putExtra("title", "Over/Under Picks");
                intent3.putExtra("db", "stb");
                intent3.putExtra("selectedp", "over");
                startActivity(intent3);
               // showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.ht:
                Intent intent4 = new Intent(view.getContext(), games.class);
                intent4.putExtra("title", "HT/FT Tips");
                intent4.putExtra("db", "stb");
                intent4.putExtra("selectedp", "ht");
                startActivity(intent4);
                //showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.bigwin:
                Intent intent5 = new Intent(view.getContext(), games.class);
                intent5.putExtra("title", "Big Odds Picks");
                intent5.putExtra("db", "stb");
                intent5.putExtra("selectedp", "bigwin");
                startActivity(intent5);
                //showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.all_sports:
                Intent intent7 = new Intent(view.getContext(), games.class);
                intent7.putExtra("title", "All Sports Tips");
                intent7.putExtra("db", "stb");
                intent7.putExtra("selectedp", "basketball");
                startActivity(intent7);
                //showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.correct:
                Intent intent8 = new Intent(view.getContext(), games.class);
                intent8.putExtra("title", "Correct Score Tips");
                intent8.putExtra("db", "stb");
                intent8.putExtra("selectedp", "correct tips");
                startActivity(intent8);
                //showAdmobInterstitial();
                showMopubInt();
                break;

            case R.id.surprise:
                Intent intent10 = new Intent(view.getContext(), games.class);
                intent10.putExtra("title", "Surprise Tips");
                intent10.putExtra("db", "stb");
                intent10.putExtra("selectedp", "surprise");
                startActivity(intent10);
               // showAdmobInterstitial();
                showMopubInt();
                break;

        }

       /* if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }*/
    }
}