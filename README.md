# Warn-Me
the aggregator of meteo warnings from Twitter and others sources

## Business requirements

Aplikacja WarnMe ma agregować i publikować na dedykowanej stronie www informacje o ostrzeżeniach pogodowych oraz dodatkowe informacje o burzach.
Głównym źródłem ostrzeżeń pogodowych powinien być:
* Twitter - kanał [IMGW-PIB METEO POLSKA](https://twitter.com/IMGWmeteo) oraz kanał [Rządowego Centrum Bezpieczeństwa](https://twitter.com/RCB_RP)
* Strona [burze.dzis.net](https://burze.dzis.net/)

Ostrzeżenia pogodowe z Twittera powinny być analizowane pod kątem słów kluczowych i hashtagów takich jak #ostrzeżenie #burza #wichura #trąbapowietrzna itp.
Jeżeli jest taka możliwość to powinny być wyciągane i publikowane na stronie zdjęcia z tweetów zawierające mapy Polski z ostrzeżeniami pogodowymi

Informacje o burzach pozyskiwane ze strony [burze.dzis.net](https://burze.dzis.net/) powinny mieć minimalny zakres:
* liczba wyładowań atmosferycznych dla podanego punktu geograficznego (obszar o zadanym promieniu)
* data pomiaru

Wszystkie informacje powinny być wyświetlane w formie postów ułożonych od najnowszego do najstarszego (timeline), z implementacją paginacji lub dynamicznego doładowania kolejnych postów.

Aplikacja powinna umożliwiać logowanie dla użytkowników z możliwością ustawiania podstawowej konfiguracji aplikacji dla danego użytkownika (np. ) (podstawową konfigurację  pozyskanych z zewnętrznych źródeł informacji w bazie danych do wykorzystania  

Dodatkowe wymagania (cloud):
- wysyłanie ostrzeżeń mailowo przez SMTP (aka newsletter)
- wystawienie ostrzeżeń przez endpoint (api) zgodnie z architekturą REST 

## Technical requirements
- apikacja musi być napisana z wykorzystaniem Spring/Spring Boot
- musi mieć bazę danych MySQL
- w bazie powinny znajdować się co najmniej 3 tabele i 2 relacje między tabelami
- w aplikacji powinno znajdować się minimum 5 widoków
- aplikacja powinna posiadać przynajmniej jeden formularz, obsługiwany metodą POST.
