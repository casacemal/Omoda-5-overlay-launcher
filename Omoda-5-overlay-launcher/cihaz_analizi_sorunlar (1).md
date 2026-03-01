# Chery Omoda 5 - Mevcut Kısıtlamalar ve Eksiklikler Analizi

Cihaz üzerinde yapılan ADB incelemeleri sonucunda tespit edilen kritik sorunlar aşağıdadır:

## 1. Bildirim Erişimi (Notification Listener) Kısıtlaması
*   **Sorun:** Sistemde "Bildirim Erişimi" (Notification Access) menüsü tamamen gizlenmiş veya devre dışı bırakılmış.
*   **Etkisi:** 3. parti Launcher uygulamaları (Vivid, Car Launcher vb.) medya oynatıcılarını kontrol edemiyor, şarkı bilgilerini çekemiyor ve bildirimleri ekranda gösteremiyor.
*   **Teknik Durum:** `enabled_notification_listeners` ayarı boş veya kısıtlı. Normalde kullanıcı arayüzünden verilmesi gereken izin, sistem tarafından engellenmiş.

## 2. Navigasyon Çubuğu (Navigation Bar) ve Tuş Eksikliği
*   **Sorun:** Cihazda standart Android navigasyon tuşları (Geri, Ana Ekran, Son Uygulamalar) yok. Bunun yerine Chery'nin kendi sol barı (`LeftNavigationBar`) kullanılıyor.
*   **Etkisi:** Kullanıcı başka bir uygulama açtığında geri dönmek veya ana ekrana geçmek için fiziksel bir tuşa veya yazılımsal bir bara sahip değil.
*   **Teknik Durum:** `qemu.hw.mainkeys` veya benzeri sistem özellikleri navigasyon barını gizlemek üzere yapılandırılmış.

## 3. Erişilebilirlik Servisleri (Accessibility Services) Blokajı
*   **Sorun:** Navigasyon barı uygulamalarının (Soft Keys vb.) çalışması için gereken "Erişilebilirlik" izinleri verilemiyor.
*   **Etkisi:** Cihazda `accessibility_enabled=0` olarak ayarlanmış ve ayarlar menüsünde bu kısım kilitli. Bu izin olmadan ekrana sanal tuş koyan uygulamalar tıklama işlemini gerçekleştiremiyor.

## 4. Sistem İzinleri ve Overlay (Üstte Gösterim) Engelleri
*   **Sorun:** "Diğer uygulamaların üzerinde göster" (Overlay) izni birçok uygulama için sistem tarafından otomatik reddediliyor.
*   **Etkisi:** Yüzen butonlar (Floating buttons) veya yan paneller çalışırken hata veriyor veya sistem tarafından kapatılıyor.
