import Flutter
import UIKit
import YandexMobileAds

class YandexAdsBanner: NSObject, FlutterPlatformViewFactory {
    private var api: YandexApi

    init(api: YandexApi) {
        self.api = api
        super.init()
    }

    func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        return Banner(
                frame: frame,
                viewIdentifier: viewId,
                arguments: args,
                api: api)
    }

    public func createArgsCodec() -> FlutterMessageCodec & NSObjectProtocol {
        return FlutterStandardMessageCodec.sharedInstance()
    }
}

class Banner: NSObject, FlutterPlatformView {
    private var _view: UIView
    private var banner: YMAAdView!
    private var api: YandexApi!
    private var id: String = ""

    init(frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?, api: YandexApi?) {
        self._view = UIView()
        super.init()

        let params = args as! [String: String]
        let id = params["id"]
        let height: Int? = Int(params["height"] ?? "0")
        self.api = api
        self.id = id ?? ""
        

        banner = YMAAdView(adUnitID: id ?? "", adSize: YMAAdSize.fixedSize(with: .init(width: 320, height: height ?? 50)))
        banner.delegate = self
        banner.removeFromSuperview()
        banner.displayAtTop(in: _view)
        banner.loadAd()
    }

    func view() -> UIView {
        return _view
    }
}

extension Banner: YMAAdViewDelegate {
    func adViewDidLoad(_ adView: YMAAdView) {
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onAdLoaded", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }

    func adViewDidFailLoading(_ adView: YMAAdView, error: Error) {
        print(error)
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onAdFailedToLoad", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }

    func adViewDidClick(_ adView: YMAAdView) {
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onAdClicked", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }

    func adViewWillLeaveApplication(_ adView: YMAAdView) {
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onLeftApplication", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }

    func adView(_ adView: YMAAdView, willPresentScreen viewController: UIViewController?) {
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onAdShown", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }

    func adView(_ adView: YMAAdView, didDismissScreen viewController: UIViewController?) {
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onAdDismissed", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }

    func adView(_ adView: YMAAdView, didTrackImpressionWith impressionData: YMAImpressionData?) {
        let response = EventResponse()

        if let callback = api.callbacks[EventKey(id: id, name: "onImpression", type: EventType.BANNER.rawValue)] {
            callback(response, nil)
        }
    }
}

