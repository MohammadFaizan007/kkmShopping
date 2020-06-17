package com.digitalcashbag.shopping.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.digitalcashbag.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kkm.com.core.BuildConfig;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseActivity;
import kkm.com.core.utils.LoggerUtil;

import static kkm.com.core.BuildConfig.DYNAMIC_LINK;

public class ReferEarn extends BaseActivity {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_refer)
    ImageView imgRefer;
    @BindView(R.id.cv_share)
    CardView cvShare;
    @BindView(R.id.cv_copy)
    CardView cvCopy;
    @BindView(R.id.tv_all_earnings)
    TextView tvAllEarnings;
    @BindView(R.id.btn_copy_code)
    TextView tvCopyCode;

    private FirebaseUser user;
    private FirebaseAuth auth_user = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            user = firebaseAuth.getCurrentUser();
            LoggerUtil.logItem("AUTH**********************");
            LoggerUtil.logItem(firebaseAuth.getUid());
            if (user == null) {
                LoggerUtil.logItem("AUTH NULL");
                LoggerUtil.logItem(PreferencesManager.getInstance(context).getEMAIL());
                auth_user.signInAnonymously().addOnCompleteListener(task -> user = task.getResult().getUser());
            }
        }
    };
    private Uri mInvitationUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_earn);
        ButterKnife.bind(this);

        title.setText("Refer And Earn");

        sideMenu.setOnClickListener(v -> onBackPressed());

        String sourceString = "Copy Referral Code: " + "<b>" + PreferencesManager.getInstance(context).getInviteCode() + "</b> ";
        tvCopyCode.setText(Html.fromHtml(sourceString));

        Glide.with(context).load(BuildConfig.BASE_URL_ICONS + "refer_earn.png").
                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.pin_bag)
                        .error(R.drawable.pin_bag))
                .into(imgRefer);
    }

    @OnClick({R.id.cv_share, R.id.cv_copy, R.id.tv_all_earnings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_share:
                if (user != null) {
                    createUser();
                } else {
                    showMessage("User not found!");
                }
                break;
            case R.id.cv_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Referral Code", PreferencesManager.getInstance(context).getInviteCode());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_all_earnings:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth_user.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onDestroy() {
        auth_user.removeAuthStateListener(authStateListener);
        super.onDestroy();
    }

    private void createUser() {
        String uid = user.getUid();
        Log.e("uid==>", uid);
        String link = "https://" + DYNAMIC_LINK + ".page.link/?invitedby=" + PreferencesManager.getInstance(context).getInviteCode();
        Log.e("link==>", link);
        try {
            FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLink(Uri.parse(link))
//            https://kkmdigitalpkg.page.link
//            https://digitalcashbag.page.link
                    .setDynamicLinkDomain(DYNAMIC_LINK + ".page.link")
                    .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com." + DYNAMIC_LINK).build())
                    //for iOS
//                                    .setIosParameters(
//                                            new DynamicLink.IosParameters.Builder("com.example.ios")
//                                                    .setAppStoreId("123456789")
//                                                    .setMinimumVersion("1.0.1")
//                                                    .build())
                    .buildShortDynamicLink()
                    .addOnSuccessListener(shortDynamicLink -> {
                        hideLoading();
                        mInvitationUrl = shortDynamicLink.getShortLink();
                        Log.e("mInvitationUrl==>", mInvitationUrl.toString());
                        String referrerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        String subject = String.format("%s Welcome to Cash Bag", referrerName);
                        String invitationLink = mInvitationUrl.toString();
                        String msg = " Hi,\nInviting you to join Cash Bag\nan interesting app which provides you\n" +
                                " incredible offers on Recharge, Money Transfer, Shopping & many more.\n\n" +
                                "Use my referrer code :\n\n " + PreferencesManager.getInstance(context).getInviteCode() +
                                "\n\nDownload app from link : " + invitationLink;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, msg);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                    }).addOnFailureListener(e -> {
                e.printStackTrace();
                hideLoading();
                Log.e("onFailure==>", "=================================");
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
            hideLoading();
        }

    }

}
