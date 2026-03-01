# Chery Omoda 5 – Tuş Takımı (HardKey) Loglarından Çıkan Komutlar ve Bilgiler

Bu doküman, iki log dosyasındaki komutları ve loglardan çıkarılan “ne işe yarar / ne kadar riskli” bilgilerini, uygulamada güvenle kullanabilmen için **Basit / Orta / Gelişmiş** olarak sınıflandırır.

> Not: Loglarda “adb logcat …” komutunun **cihaz içinde çalışmadığı** görülüyor (adb binary yok). Cihaz kabuğunda log almak için `logcat` kullanılmalı.

---

## 1) Basit (Düşük risk – düşük zorluk)

### 1.1 `logcat -c`
- **Amaç:** Log buffer’ı temizler (yeni testte sadece yeni kayıtlar gelsin).
- **Risk:** Düşük. Sadece logları temizler; sistemi değiştirmez.
- **Dikkat:** Testten önce temizleyip sonra filtreleyerek okumak daha net sonuç verir.

### 1.2 `logcat | grep -i hardkey`
- **Amaç:** Hardkey ile ilgili satırları yakalamak için logcat çıktısını filtreler.
- **Risk:** Düşük.
- **Dikkat:** `grep` bazı cihazlarda yoksa `toybox grep`/`busybox grep` gerekebilir.

### 1.3 (Logdan öğrenilen kritik fark) `adb logcat …` **cihaz içinde çalışmaz**
- **Amaç:** Loglarda, cihaz shell içinde `adb logcat` denenmiş ve **“adb: inaccessible or not found”** hatası alınmış.
- **Sonuç:**  
  - **PC tarafında:** `adb logcat …` (host aracı)  
  - **Cihaz içinde:** `logcat …` (device aracı)

---

## 2) Orta (Orta risk – orta zorluk)

### 2.1 `dumpsys car_service | grep -Ei "speed|gear|ign|light|brake"`
- **Amaç:** `car_service` üzerinden **hız / vites / kontak (ign) / ışık / fren** benzeri anahtar kelimeleri hızlı taramak.
- **Risk:** Orta (okuma ağırlıklı ama büyük çıktı üretebilir).
- **Zorluk:** Orta.
- **Dikkat:**
  - Çıktı çok büyük olabilir → filtreyi daralt.
  - Bazı property’ler permission/SELinux yüzünden kısıtlı olabilir.

### 2.2 `dumpsys car_service | grep -Ei "lamp|indicator|hazard"`
- **Amaç:** **Lamba / sinyal / dörtlü** ile ilişkili kısımları anahtar kelimeyle yakalamaya çalışmak.
- **Risk / Zorluk:** Orta (2.1 ile aynı).

---

## 3) Gelişmiş (Düşük/Orta risk ama “entegrasyon” zorluğu yüksek)

> Bu bölüm “logdan çıkan mekanizma”yı uygulama mimarisinde kullanabilmen için özetler. Komut değil; **uygulama tarafında yakalanabilir sinyaller** ve davranışlar.

### 3.1 Hardkey olay zinciri (loglardan görülen)
Loglarda hardkey basımı sırasında şu bileşenler sık geçiyor:
- `WindowManager: interceptKeyBeforeDispatching: report hardkey event …`
- `CarHardkeyManager: hardkey keycode:… press_down / shortPress_up`
- `SYSUI.HardKeyHandler: receive key code … / ignore … / openHomePage`
- Yayın (broadcast) benzeri intent:
  - `act=com.saic.keyevent.hardkey.report`
  - `CarPlayReceiver: onReceive: com.saic.keyevent.hardkey.report`
  - `MEDIA.HardKeyBusiness: onHardKey: Intent { act=com.saic.keyevent.hardkey.report … }`

**Uygulama açısından anlamı:**
- Sistem, hardkey’i önce WindowManager seviyesinde yakalıyor, sonra araç/medya katmanlarına dağıtıyor.
- Bazı keycode’lar SYSUI tarafından “ignore” ediliyor; bazıları (HOME gibi) doğrudan işlem görüyor.

**Risk:** Düşük/Orta (okuma/izleme), ancak **entegrasyon zorluğu yüksek** (permission, broadcast export durumu vs.).
**Dikkat:** `com.saic.keyevent.hardkey.report` broadcast’ı dinlemek için:
- Exported mı?
- Signature permission var mı?
- Sticky mi?
gibi detayları `pm dump`, `dumpsys package` ile doğrulamak gerekebilir.

---

## 4) Loglardan çıkarılan keycode gözlemleri (Uygulamada eşleme için)

Aşağıdaki keycode’lar loglarda net şekilde geçti:

### 4.1 Android standart keycode’lar
- **KEYCODE_HOME = 3**  
  - SYSUI: `KEYCODE_HOME :openHomePage`
  - CarHardkeyManager tarafında “take video from device” gibi vendor davranışı da loglanmış.

- **KEYCODE_CALL = 5**  
  - Basılınca `CarPlayCommClient: sendHardKey:keyType6` tetikleniyor (CarPlay hattına event gidiyor).

- **KEYCODE_VOLUME_UP = 25**  
  - SYSUI tarafı çoğu satırda `ignore key code : 25` diyor.  
  - Yani ses tuşu event’i sistemde farklı katmanda handled olabilir.

- **KEYCODE_MEDIA_NEXT = 87 (standart karşılık olması muhtemel)**  
  - `CarHardkeyManager: mediaValue:3` görülüyor (muhtemel “next track / source next” gibi bir media aksiyonu).

### 4.2 Vendor/özel keycode’lar (289–294, 291)
Bunlar Android standart listede tipik olarak yoktur; vendor tanımlı olabilir.

- **289**  
  - Medya tarafında “source change” akışı görülüyor: `onHardKeySrc … changeSource:Radio`  
  - Pratikte: “Kaynak değiştir / Radio” benzeri bir tuş olabilir.

- **290**  
  - `keycodeHandle: CP is not connected` logu var → CarPlay/CP ile ilişkili bir tuş olabilir.

- **293**  
  - `keycodeHandle: getSiriStatus ===> false` ve `telKey:8` / `sendHardKey:keyType8` görülüyor  
  - Pratikte: Siri/Voice/Phone fonksiyonu ile ilişkili olabilir.

- **294**  
  - Görünüyor ama belirgin aksiyon satırı az; sadece SYSUI “receive key code : 294” gibi.

- **291**  
  - SYSUI: `receiver key code : 291(not processed)` → sistem UI bunu işlemiyor (ama alt katmanlar işleyebilir).

---

## 5) Uygulamada “sorun çıkmaması” için pratik kullanım planı

### Basit test akışı (önerilen)
1) `logcat -c`
2) `logcat | grep -i hardkey`
3) Her tuşa bas → keycode’ları not et → hangi komponentler devreye giriyor takip et.

### Orta seviye veri tarama
- `dumpsys car_service | grep -Ei …` filtrelerini **tek tek** çalıştır.
- Çıktıyı dosyaya almak istersen: `… > /sdcard/car_service_dump.txt` (yazma izinlerine dikkat).

### Gelişmiş entegrasyon (dinleme)
- Broadcast action `com.saic.keyevent.hardkey.report` uygulama tarafından dinlenebiliyor mu?  
  - Dinlenebiliyorsa: en temiz yol bu olur (keycode + extras üzerinden).
  - Dinlenemiyorsa: Accessibility / Input / vendor API gibi alternatifler gerekir (bu log dosyaları onları göstermiyor).

---

## 6) Bu dosyada geçen komutların “tehlike ve zorluk” özeti

| Seviye | Komut / Bilgi | Tehlike | Zorluk | Not |
|---|---|---:|---:|---|
| Basit | `logcat -c` | Düşük | Düşük | Log temizleme |
| Basit | `logcat | grep -i hardkey` | Düşük | Düşük | Filtreli izleme |
| Basit | “cihaz içinde `adb` yok” | Düşük | Düşük | PC’de adb, cihazda logcat |
| Orta | `dumpsys car_service | grep -Ei …` | Orta | Orta | Büyük çıktı, permission etkisi |
| Gelişmiş | `com.saic.keyevent.hardkey.report` mekanizması | Düşük/Orta | Yüksek | Export/permission doğrulaması gerekir |

