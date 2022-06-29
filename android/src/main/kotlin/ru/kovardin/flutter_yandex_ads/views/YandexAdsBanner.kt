package ru.kovardin.flutter_yandex_ads.views

import android.content.Context
import android.util.Log
import android.view.View
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import ru.kovardin.flutter_yandex_ads.EventKey
import ru.kovardin.flutter_yandex_ads.EventType
import ru.kovardin.flutter_yandex_ads.YandexApi
import ru.kovardin.flutter_yandex_ads.pigeons.Yandex

class YandexAdsBanner(private val api: YandexApi) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val params = args as Map<String?, Any?>?
        val id: String = params?.get("id") as String
	val height: Int? = (params?.get("height") as String).toIntOrNull()
        return Banner(context, id, height ?: 50,  params, object : BannerAdEventListener {
            override fun onAdLoaded() {
                val builder = Yandex.EventResponse.Builder()

                val response = builder.build()
                api.callbacks.remove(EventKey(id = id, name = "onAdLoaded", type = EventType.BANNER.type))?.success(response)
            }

            override fun onAdFailedToLoad(error: AdRequestError) {
                val builder = Yandex.EventResponse.Builder()
                builder.setCode(error.code.toLong())
                builder.setDescription(error.description)

                val response = builder.build()
                api.callbacks.remove(EventKey(id = id, name = "onAdFailedToLoad", type = EventType.BANNER.type))?.success(response)
            }

            override fun onLeftApplication() {
                val builder = Yandex.EventResponse.Builder()

                val response = builder.build()
                api.callbacks.remove(EventKey(id = id, name = "onLeftApplication", type = EventType.BANNER.type))?.success(response)
            }

            override fun onReturnedToApplication() {
                val builder = Yandex.EventResponse.Builder()

                val response = builder.build()
                api.callbacks.remove(EventKey(id = id, name = "onReturnedToApplication", type = EventType.BANNER.type))?.success(response)
            }

            override fun onImpression(data: ImpressionData?) {
                val builder = Yandex.EventResponse.Builder()
                builder.setData(data?.rawData ?: "")

                val response = builder.build()
                api.callbacks.remove(EventKey(id = id, name = "onImpression", type = EventType.BANNER.type))?.success(response)
            }

            override fun onAdClicked() {
                val builder = Yandex.EventResponse.Builder()

                val response = builder.build()
                api.callbacks.remove(EventKey(id = id, name = "onAdClicked", type = EventType.BANNER.type))?.success(response)
            }
        });
    }
}

class Banner(context: Context, id: String,height: Int, params: Map<String?, Any?>?, listener: BannerAdEventListener) : PlatformView {
    private val banner: BannerAdView

    init {
	val bSize = if (height == 50) AdSize.BANNER_320x50 else AdSize.BANNER_320x100
        banner = BannerAdView(context);
        banner.setAdSize(bSize)
        banner.setAdUnitId(id)
        banner.setBannerAdEventListener(listener)

        val request: AdRequest = AdRequest.Builder().build()
        banner.loadAd(request)
    }

    override fun getView(): View {
        return banner;
    }

    override fun dispose() {}
}
