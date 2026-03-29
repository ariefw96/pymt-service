1. Dikarenakan payment gateway bisa mengirim callback lebih dari 1 kali, maka dari service update order akan dilakukan pengecekan apakah order tersebut sudah berganti status menjadi paid. ketika status order sudah menjadi paid maka akan bypass logic (langsung return) dan service tidak akan melakukan apa-apa sehingga tidak akan terjadi double charge
2. a. Untuk error yang tidak boleh di retry adalah error yang bersifat permanent seperti bad request. biasanya error bad request adalah error yang terjadi ketika request dari client tidak memenuhi kebutuhan jadi walaupun diretry sampai bebereapa kali pun hasilnya akan tetap sama.
   untuk return http 500 sendiri sebenarnya perlu di cek, di server tersebut apakah data (misalnya transaksi) sudah terbuat atau belum, tapi seharusnya jika memang ada kesalahan di server harusnya data di rollback.
   b. perbedaan retry dan circuit breaker adalah untuk retry sendiri adalah metode mengirim request ulang ketika request gagal dikirimkan, dan untuk circuit breaker sendiri adalah seberapa ambang batas request tersebut dikirimkan ulang kembali agar tidak membebani server. misal saja untuk retry kita lakakukan dengan maks attemp sebanyak 3x.
   c. resiko melakukan retry tanpa jitter / backoff hanya akan membebani kerja server dikarena request retry dilakukan secara bersama, mungkin bisa kita sebut dengan istilah "mini" DDOS.
3. a. informasi minimum yang wajib tiap ada di log adalah
   - timestamp
   - nama service
   - aktor yang menjalankan
   - proses apa yang dijalankan
   - payload
   - dan kebutuhan tambahan adalah traceID, ini bisa kita analogikan sebagai "nomor resi" dari suatu keberlangsungan proses jika kita menggunakan arsitektur microservice agar lebih mudah dalam menganalisa log.
     
   b. seperti telah dijelaskan sebelumnya, traceID itu penting dalam penelusuran log. dengan adanya traceId tersebut kita mengamati alur suatu proses dari A -> B -> C -> dan seterusnya
   c. jika traceId tidak konsisten antar service malahan akan membuat bingung yang menelusuri log tersebut, dikarenakan kita tidak tahu seharusnya alurnya terhenti dimana
4. a. untuk consumer sendiri, biasanya saya melakukan pengecekan lebih lanjut apakah data tesebut telah diproses sebelumnya. jika data tersebut sudah diproses sebelumnya maka return saja tanpa melakukan proses lebih lanjut, saya juga menerapkan algoritma seperti ini untuk menghandle callback seperti payment gateway agar tidak terjadi double charge
   b. untuk ordering event dijamin oleh message broker itu sendiri, karena dalam message broker itu sendiri antrian yang masuk diproses terlebih dahulu, jika ada error maka diulang beberapa kali sampai batasnya lalu dibuang baru melanjutkan antrian yang baru
   c. jika ordering event tidak terjaga, maka akan menyebabkan tidak konsistennya data semisal user mengupdate data A ke B, lalu di update lagi ke C seharusnya untuk data terakhir ada di C tetapi karena ordering tidak terjaga bisa saja status data terakhir dari user tersebut adalah di B, bukan di C
5. a. untuk cache sendiri kapan harus di invalidate adalah ketika ada perubahan di database, kita harus rewrite ulang value cache tersebut karena value nya sudah tidak sesuai dengan database
   b. cache dapat dijadikan sebagai source of truth ketika data tersebut hampir bisa dibilang "tidak ada perubahan" dalam waktu lama. mungkin misalnya rate nilai tukar uang dalam hari tersebut, karena tidak ada perubahan data maka kita bisa gunakan cache sebagai source of truth.
   c. untuk mengatasi kasus cache stampede (expired ketika sedang ramai nya dipakai) kita gunakan locking ketika mengambil data dari database sehingga cukup 1 user saja yang mengupdate cache tersebut yang lain ambil value dari cache, atau kita refresh value dari cache tersebut secara berkala jangan sampai waktu expired nya habis.
6. a. penggunaak api key untuk standar microservices bisa dibilang kurang aman, karena ketika value dari api key tersebut bocor maka bisa saja dari pihak luar mengakses api tersebut sebagai "service palsu".
   b. untuk standard keamanan minimal paling gampang kita gunakan jwt dengan durasi pendek hanya untuk menangani proses yang terjadi saat itu saja.
   c. resiko jwt dengan tanpa expiry pendek untuk akses internal adalah jika token tersebut sampai bocor, bisa saja disalahgunakan untuk "menyerang" service tersebut dan karena jwt bersifat stateless maka sekali bocor dan belum expired maka akan tetap bisa dipakai kecuali kita ganti secretKey di jwt kita tadi (atau kita lakukan blacklist JWT di level database). 
