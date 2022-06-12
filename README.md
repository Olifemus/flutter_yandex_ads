#THIS IS A FORK OF []()

# flutter_yandex_ads

Yandex ADS for flutter applicaitions

## Roadmap

Android
- [x] Banner
- [x] Interstitial
- [ ] Native
- [ ] Rewarded

iOS
- [x] Banner
- [x] Interstitial
- [ ] Native
- [ ] Rewarded


## Getting Started

Install package

```yaml
flutter_yandex_ads: 0.0.4
```

For android add to AndroidManifest.xml

```xml

<uses-permission android:name="com.google.android.gms.permission.AD_ID" tools:node="remove"/>
```

For ios add to Info.plist

```xml
<key>SKAdNetworkItems</key>
<array>
<dict>
    <key>SKAdNetworkIdentifier</key>
    <string>zq492l623r.skadnetwork</string>
</dict>
</array>
```
And set for ios in Podfile

```
#use_frameworks!
```


Call initialization in main.dart

```dart
FlutterYandexAds ads = new FlutterYandexAds();
ads.initialize();
```

Created banner widget:

```dart
Container(
  height: 100,
  child: YandexAdsBannerWidget(
    ads: ads,
    height: 100,
    id: 'R-M-DEMO-320x50',
    onAdLoaded: () {
      print('onAdLoaded');
    },
    onAdFailedToLoad: (AdLoadError err) {
      print('onAdFailedToLoad code: ${err.code}, description: ${err.description}');
    },
    onImpression: (String? data) {
      print("on ad impression ${data ?? ''}");
    },
    onAdClicked: () {
      print('onAdClicked');
    },
  ),
)
```

## Advanced development

Generate pigeons files fo

```
flutter pub run pigeon \
  --input pigeons/yandex.dart \
  --dart_out lib/pigeons/yandex.dart \
  --java_out ./android/src/main/kotlin/ru/kovardin/flutter_yandex_ads/pigeons/Yandex.java \
  --java_package "ru.kovardin.flutter_yandex_ads.pigeons" \
  --objc_header_out ./ios/Classes/pigeons/Yandex.h \
  --objc_source_out ./ios/Classes/pigeons/Yandex.m


flutter pub run pigeon \
  --input pigeons/interstitial.dart \
  --dart_out lib/pigeons/interstitial.dart \
  --java_out ./android/src/main/kotlin/ru/kovardin/flutter_yandex_ads/pigeons/Interstitial.java \
  --java_package "ru.kovardin.flutter_yandex_ads.pigeons" \
  --objc_header_out ./ios/Classes/pigeons/Interstitial.h \
  --objc_source_out ./ios/Classes/pigeons/Interstitial.m


flutter pub run pigeon \
  --input pigeons/rewarded.dart \
  --dart_out lib/pigeons/rewarded.dart \
  --java_out ./android/src/main/kotlin/ru/kovardin/flutter_yandex_ads/pigeons/Rewarded.java \
  --java_package "ru.kovardin.flutter_yandex_ads.pigeons" \
  --objc_header_out ./ios/Classes/pigeons/Rewarded.h \
  --objc_source_out ./ios/Classes/pigeons/Rewarded.m
