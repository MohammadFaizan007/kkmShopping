package kkm.com.core.retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import kkm.com.core.BuildConfig;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Vivek on 1/8/18.
 */

public class ServiceGenerator {

    private static final String CACHE_CONTROL = "Cache-Control";
    private static ServiceGenerator serviceGenerator;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // TODO BUILDER FILE FOR FILE UPLOAD
    private static Retrofit.Builder builderFile = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FILE)
            .addConverterFactory(GsonConverterFactory.create(gson));


    // TODO SERVICE FOR MLM
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_MLM)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    // TODO SERVICE FOR POLICY BAZAAR
//    public static <S> S createServiceSalesPoint(Class<S> serviceClass) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.SALES_POINT)
//                .client(provideOkHttpClient())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
////        @Headers("Authorization: #$SPO!@#!7@86#7#6@")
//        return retrofit.create(serviceClass);
//    }

    // TODO SERVICE FOR FILE UPLOAD THROUGH MULTIPART
    public static <S> S createServiceFile(Class<S> serviceClass) {
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = original.newBuilder()
                    .header("Content-Type", "multipart/form-data")
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builderFile.client(client).build();
        return retrofit.create(serviceClass);
    }


    // TODO SERVICE FOR IIA SHOPPING
    public static <S> S createServiceShopping(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    // TODO SERVICE FOR UTILITY (LIKE RECHARGE,BILL PAYMENT)
    public static <S> S createServiceUtility(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_UTILITY)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }


    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES)
                .build();
    }

    public static ServiceGenerator getInstance() {
        if (serviceGenerator == null) serviceGenerator = new ServiceGenerator();
        return serviceGenerator;
    }

    /**
     * M2P Service call with authentication
     */
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_M2P)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit.Builder builderv2 = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_M2PV2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit.Builder buildermlmv2 = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_MLM_V2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit.Builder builder_utility_v2 = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_UTILITY_V2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    // TODO CYBER PLATE API V2

    // TODO SERVICE FOR M2P SERVICE
    public static <S> S createServiceUtilityV2(
            Class<S> serviceClass) {
        if (!TextUtils.isEmpty(BuildConfig.M2P_USERNAME)
                && !TextUtils.isEmpty(BuildConfig.M2P_PASSWORD)) {
            String authToken = Credentials.basic(BuildConfig.M2P_USERNAME, BuildConfig.M2P_PASSWORD);
            return createServiceUtilityV2(serviceClass, authToken);
        }

        return createServiceUtilityV2(serviceClass, null);
    }

    private static <S> S createServiceUtilityV2(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder_utility_v2.client(httpClient.build());
                retrofit = builder_utility_v2.build();
            }
        }

        return retrofit.create(serviceClass);
    }


    // TODO SERVICE FOR M2P SERVICE
    public static <S> S createServiceM2P(Class<S> serviceClass) {
        if (!TextUtils.isEmpty(BuildConfig.M2P_USERNAME)
                && !TextUtils.isEmpty(BuildConfig.M2P_PASSWORD)) {
            String authToken = Credentials.basic(BuildConfig.M2P_USERNAME, BuildConfig.M2P_PASSWORD);
            return createServiceM2P(serviceClass, authToken);
        }
        return createServiceM2P(serviceClass, null);
    }


    private static <S> S createServiceM2P(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    ////////////////////////////M2P V2 Starts///////////////////////////

    private static Retrofit retrofit = builder.build();

    // TODO SERVICE FOR M2P SERVICE
    public static <S> S createServiceM2PV2(Class<S> serviceClass) {
        if (!TextUtils.isEmpty(BuildConfig.M2P_USERNAME)
                && !TextUtils.isEmpty(BuildConfig.M2P_PASSWORD)) {
            String authToken = Credentials.basic(BuildConfig.M2P_USERNAME, BuildConfig.M2P_PASSWORD);
            return createServiceM2PV2(serviceClass, authToken);
        }

        return createServiceM2PV2(serviceClass, null);
    }

    private static <S> S createServiceM2PV2(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builderv2.client(httpClient.build());
                retrofit = builderv2.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    ////////////////////////////M2P V2 Ends///////////////////////////


    ////////////////////////////Mlm V2 Starts///////////////////////////
    private static Retrofit retrofit_mlm_v2 = buildermlmv2.build();

    // TODO SERVICE FOR M2P SERVICE
    public static <S> S createServiceMLMV2(Class<S> serviceClass) {
        if (!TextUtils.isEmpty(BuildConfig.M2P_USERNAME)
                && !TextUtils.isEmpty(BuildConfig.M2P_PASSWORD)) {
            String authToken = Credentials.basic(BuildConfig.M2P_USERNAME, BuildConfig.M2P_PASSWORD);
            return createServiceMLMV2(serviceClass, authToken);
        }
        return createServiceMLMV2(serviceClass, null);
    }

    private static <S> S createServiceMLMV2(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                buildermlmv2.client(httpClient.build());
                retrofit_mlm_v2 = buildermlmv2.build();
            }
        }

        return retrofit_mlm_v2.create(serviceClass);
    }

    ////////////////////////////Mlm V2 Ends///////////////////////////


    static class AuthenticationInterceptor implements Interceptor {
        private String authToken;

        AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @NotNull
        @Override
        public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }


}