# networks-class-homework
Title says it all, uh?

Spec [ITA]: martedì, 5 dicembre 2017, 12:16

L'obiettivo del progetto è quello di sviluppare un server HTTP.  Il progetto prevede:

    Lo sviluppo di una serie di classi in Java che implementano le funzionalità e che estendono le interfacce messe a disposizione sul sito del corso;
    La realizzazione di una serie di test in Java che verifichino il corretto funzionamento delle funzionalità base;
    La realizzazione di una serie di script Python che mostrano il comportamento del server.

Il progetto può essere svolto in gruppo. In questo caso il gruppo non può contenere più di tre persone e tutti i componenti del gruppo devono sostenere l'esame nella stessa data. 

Per semplificare l'accesso alle classi sviluppate, il progetto dovrà fornire una implementazione per l'interfaccia HTTPFactory (vedere le specifiche sotto). I metodi eventualmente non implementati dovranno restituire null. La classe, chiamata MyHTTPFactory, dovrà far parte del pacchetto:

it.unifi.rc.httpserver.m<numeromatricola>

In caso di gruppo può essere scelta indifferentemente la matricola di uno degli appartenenti al gruppo.

Livello 0. Fornire l'implementazione per le interfacce:

    HTTPRequest
    HTTPReply

Queste interfacce identificano, rispettivamente, una richiesta ed una risposta del protocollo HTTP. 

Livello 1. Fornire una implementazione per le classi astratte:

    HTTPInputStream
    HTTPOutputStream

Queste classi consentano di leggere/scrivere una richiesta/risposta HTTP nell'appropriato stream. Le specifiche istanze di queste classi saranno accessibili mediante i metodi getHTTPInputStream e getHTTPOutputStream della classe MyHTTPFactory fornita.

Livello 2. Fornire l'implementazione per un  Handler che è in grado di costruire una risposta gestendo le risorse a partire da una particolare directory ed implementando la versione versione 1.0 del protocollo. Un istanza di questo handler è ottenibile mediante il metodo getFileSystemHandler1_0( File root ) della classe MyHTTPFactory.

Livello 3. Fornire l'implementazione per un  Handler che è in grado di costruire una risposta gestendo le risorse a partire da una particolare directory ed implementando la versione versione 1.0 del protocollo. A differenza del livello precedente l'handler gestisce un particolare host. Un istanza di questo handler è ottenibile mediante il metodo  getFileSystemHandler1_0( String host, File root ) della classe MyHTTPFactory.

Livello 4. Fornire l'implementazione per un  Handler che è in grado di costruire una risposta gestendo le risorse a partire da una particolare directory ed implementando la versione versione 1.1 del protocollo. Un istanza di questo handler è ottenibile mediante il metodo getFileSystemHandler1_1( File root ) della classe MyHTTPFactory.

Livello 5. Fornire l'implementazione per un  Handler che è in grado di costruire una risposta gestendo le risorse a partire da una particolare directory ed implementando la versione versione 1.1 del protocollo. A differenza del livello precedente l'handler gestisce un particolare host. Un istanza di questo handler è ottenibile mediante il metodo getFileSystemHandler1_1( String host, File root ) della classe MyHTTPFactory.

Livello 6. Fornire l'implementazione per l'interfaccia HTTPServer che implementa, come dice il nome stesso, un server HTTP.  Un istanza di questa classe è ottenibile mediante il metodo getHTTPServer( int port , int backlog , InetAddress address , HTTPHandler ... handlers)della classe MyHTTPFactory.

Livello 7. Estendere il livello precedente garantendo che il server possa gestire più client contemporaneamente (server multi threading).

Livello 8. Implementare un handler che, implementando l'interfaccia HTTPHandler,  metta a disposizione le funzionalità di un proxy (non è necessario il caching locale).

Il progetto viene considerato superato se vengono implementati correttamente i livelli fino al 3 incluso.

Modalità di consegna: al momento della consegna ogni gruppo (o singolo studente) deve consegnare un archivio ottenuto dalla compressione di una cartella denominata con il numero di matricola dello studente (es. "1234456") e contenente :

    una directory chiamata Java con tutti i sorgenti java;
    una directory chiamata Python con tutti i sorgenti Python;
    una breve relazione  (chiamata "readme.pdf") con una descrizione dei file consegnati e dei test realizzati (funzionalità ed istruzioni per l'esecuzione).

I progetti che non rispettano le specifiche saranno penalizzati.


Valutazione: Il progetto verrà valutato rispetto a tre aspetti:

    Corretto funzionamento del codice consegnato;
    Organizzazione e pulizia del codice (presenza di commenti, struttura dei metodi,...);
    Design della soluzione proposta. 

Consegna: Il progetto dovrà essere consegnato almeno una settimana prima dell'orale

