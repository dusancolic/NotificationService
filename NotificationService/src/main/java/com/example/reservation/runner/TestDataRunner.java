package com.example.reservation.runner;

import com.example.reservation.domain.Notification;
import com.example.reservation.domain.NotificationType;
import com.example.reservation.repository.NotificationRepository;
import com.example.reservation.repository.NotificationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile({"default"})
@Component
@AllArgsConstructor
public class TestDataRunner implements CommandLineRunner {

    private NotificationRepository notificationRepository;
    private NotificationTypeRepository notificationTypeRepository;

    /*
        Definisanje tipa notifikacije koja može da se pošalje. Svaki tip notifikacije se
        vezuje za tekst i skup parametara koje prima (Pozdrav %ime %prezime, da
        bi ste se verifikovali idite na sledeci %link). Kada se šalje notifikacija
        potrebno je poslati tip notifikacije i skup parametara koje prima, kao i imejl na
        koji se šalje notifikacija. Ove tipove može da definiše samo admin. Admin
        takođe je potrebno da može da lista, briše i ažurira postojeće tipove
        notifikacija. Za svaku notifikaciju koja će biti navedena u nastavku potrebno
        je definisati odgovarajući tip.

            ○ Slanje aktivacionog imejla - Zahtev za slanje aktivacionog imejla ide preko
            ovog servisa. Kako se registracija dešava na korisničkom servisu, potrebno
            je sa korisničkog servisa poslati podatke za slanje imejla i proslediti ga sa
            ovog servisa.
            ○ Slanje imejla za promenu lozinke.
            ○ Slanje notifikacije kada se trening uspešno zakaže. Ovu notifikaciju je
            potrebno poslati klijentu koji je zakazao trening, kao i menadžeru fiskulturne
            sale.
            ○ Slanje notifikacije za otkazivanje treninga. Takođe se šalje i klijentu
            (klijentima) i menadžeru.
            ○ Slanje notifikacije jedan dan (24h) pre početka termina treninga kao podsetnik.

        ○ Sve poslate notifikacije se čuvaju u bazi podataka kao arhiva. Admin može da
        izlista sve notifikacije koje su ikada poslate, dok klijent i menadžer mogu da
        vide samo one koje su poslate njima. Potrebno je obezbediti filtriranje po
        sledećim parametrima: tip notifikacije (promena lozinke, aktivacioni imejl, itd),
        imejl i po zadatom vremenskom opsegu (recimo za prošlu godinu).
        ○ Svaki zahtev za slanje notifikacije se šalje asinhrono preko mesedž brokera.
    */
    @Override
    public void run(String... args) throws Exception {
        NotificationType notificationType = new NotificationType();
        notificationType.setName("activation");
        notificationType.setText("Welcome 1 2, here is your activation link : 3");

        NotificationType notificationType2 = new NotificationType();
        notificationType2.setName("passwordChange");
        notificationType2.setText("Hello 1 2, your have changed your password.");

        NotificationType notificationType3 = new NotificationType();
        notificationType3.setName("cancelation");
        notificationType3.setText("Hello 1 2, your training on 3 , at 4 has been canceled.");


        NotificationType notificationType4 = new NotificationType();
        notificationType4.setName("booking");
        notificationType4.setText("Hello 1 2, you have successfully booked a training on 3 , at 4 ");

        NotificationType notificationType5 = new NotificationType();
        notificationType5.setName("reminder");
        notificationType5.setText("Hello 1 2, this is your reminder that you have a training tomorrow at this time.");

        notificationTypeRepository.save(notificationType);
        notificationTypeRepository.save(notificationType2);
        notificationTypeRepository.save(notificationType3);
        notificationTypeRepository.save(notificationType4);
        notificationTypeRepository.save(notificationType5);

        Notification notification = new Notification("uroscolic02@gmail.com", notificationType, List.of("Dusan","Colic"));
        notificationType.setText(notificationType.getText()
                .replace("1",notification.getParameters().get(0))
                .replace("2",notification.getParameters().get(1)));
        notificationTypeRepository.save(notificationType);
        notificationRepository.save(notification);


    }
}
