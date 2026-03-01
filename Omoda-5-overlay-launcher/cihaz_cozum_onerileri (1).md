# Chery Omoda 5 - Çözüm Önerileri ve İyileştirme Planı

Cihazdaki kısıtlamaları aşmak için Root yetkisiyle uygulanabilecek yöntemler:

## 1. Bildirim Erişimini Zorla Aktif Etme (Root)
Kullanıcı arayüzünde menü olmasa bile, ADB üzerinden istediğimiz Launcher'a bildirim izni verebiliriz.
*   **Komut:** `adb shell settings put secure enabled_notification_listeners <uygulama_adı>/<servis_adı>`
*   **Sonuç:** Launcher, medya kontrollerine ve bildirim verilerine anında erişebilir hale gelir.

## 2. Yazılımsal Navigasyon Barını (Native Android) Geri Getirme
Sistemin gizlediği standart Android navigasyon barını `build.prop` veya `settings` üzerinden zorla açabiliriz.
*   **Yöntem A:** `qemu.hw.mainkeys=0` ayarını `build.prop` dosyasına eklemek. (Dikkatli yapılmalı).
*   **Yöntem B:** `settings put global policy_control immersive.full=*` komutuyla ekranın alt kısmında navigasyon alanını tetiklemek.

## 3. Erişilebilirlik İzinlerini Manuel Tanımlama
Hangi navigasyon barı uygulamasını yüklediyseniz, onun iznini SQL veritabanına doğrudan yazarak aktif edebiliriz.
*   **Komut:** `adb shell settings put secure enabled_accessibility_services <paket_adi>`
*   **Sonuç:** Uygulama, ayarlara girmeye gerek kalmadan çalışmaya başlar.

## 4. Chery Sol Barını (LeftNavigationBar) Özelleştirme
Madem sistemde bir `LeftNavigationBar` var, bu pencerenin önceliğini (Z-Order) değiştirerek veya 3. parti bir Launcher'ın butonlarını bu alanın üzerine (Overlay) bindirerek hibrit bir çözüm üretebiliriz.

## 5. Emülatör Üzerinde Prototip Hazırlama
Aldığımız `lib64` ve `vendor` yedekleriyle, bu kısıtlamaların olmadığı "Özgür" bir Chery emülatörü oluşturup:
1.  Hangi uygulamanın en stabil çalıştığını test edeceğiz.
2.  Çalışan konfigürasyonu doğrudan gerçek cihaza "push" yöntemiyle aktaracağız.

## 6. Launcher ve Klima (HVAC) Sorunları Çözümü
F-Launcher kullanımı sonrası kaybolan Klima ve Bildirim Paneli için:

*   **Sorun:** Orijinal launcher devre dışı kaldığında sistem servisleri (HVAC, Status Bar) tetiklenmiyor.
*   **Çözüm (Geri Yükleme):**
    1.  Orijinal Launcher'ı Etkinleştir: `adb shell pm enable com.yfve.launcher`
    2.  Varsayılanı Geri Al: `adb shell cmd package set-home-activity com.yfve.launcher/.LauncherActivity`
    3.  Klima Manuel Başlatma: `adb shell am start -n com.yfve.hvac/com.yfve.hvac.MainActivity`
    4.  Cihazı Yeniden Başlat: `adb reboot`

# 1. Orijinal Launcher'ı tekrar etkinleştirin (Klima ve Panel buna bağlıdır)
adb shell pm enable com.yfve.launcher

# 2. Orijinal Launcher'ı varsayılan olarak geri tanımlayın
adb shell cmd package set-home-activity com.yfve.launcher/.LauncherActivity

# 3. Eğer Klima hala açılmıyorsa manuel tetikleyin
adb shell am start -n com.yfve.hvac/com.yfve.hvac.MainActivity

# 4. Değişikliklerin tamamen aktif olması için sistemi yeniden başlatın
adb reboot