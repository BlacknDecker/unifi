# Decidibilità di linguaggi

Tecniche più comuni:
- manipolare e considerare le MdT che li accettano (decidono), o che li semidecidono
- manipolare e considerare gli algoritmi di *decisione*, *semidecisione* ed *enumerazione*

## Dimostrare la *decidibilità* di linguaggi

- **Unione** e **concatenazione** di linguaggi decidibili:
    - combinare le MdT che li decidono in modo da accettare le stringhe del nuovo linguaggio

- **Chiusura** di Kleene di linguaggi decidibili:
    - osservare che l'operazione di chiusura si può scomporre in operazioni di *unione* e *concatenazione* di linguaggi decidibili, il cui risultato è decidibile

## Dimostrare la *semidecidibilità* di linguaggi

- Descrivere direttamente un **algoritmo di semidecisione**


# **In**decidibilità di linguaggi

## Procedere *per assurdo*
- Supponi di avere una MdT che risolva il problema dato nell'esercizio
- Usala per generare un assurdo
    - risolvere il problema dell'arresto (o del nastro vuoto, o un altro problema impossibile)
    - contraddire una delle ipotesi (es decidere un linguaggio indecidibile)


# Funzioni Ricorsive Primitive (**RP**)

## Mostrare che una certa funzione è **RP**:

- *Scomporla* (composizione, r.p., minimalizzazione, ecc) in funzioni che sappiamo essere RP o iniziali

## Mostrare che una funzione *definita per casi* è **RP**

- Individuare le **relazioni** associate alle casistiche
- Dimostrare che le relazioni individuate sono **RP**:
    - Dimostrare che la loro *funzione caratteristica* è *RP* (vedi sopra)


# Realizzare MdT che fanno cose

Vale sia per le MdT che *calcolano funzioni* che per MdT che *accettano linguaggi*.

Tecniche più comuni:
- se non è specificato diversamente, si può *usare più di un nastro* se torna meglio
- si può *lanciare* (simulare il comportamento di) *altre MdT* durante l'esecuzione

**Attenzione**: se dice *descrivere* va bene a parole, se dice *costruire* ci vuole il grafico o la lista delle transizioni. Se c'è verso di fare il grafico anche se dice *descrivere*, va bene uguale, ma è meglio comunque descriverla lo stesso anche a parole.

## *Descrivere* MdT

- Descrivere bene la configurazione iniziale
    - come è fatta la MdT (quanti nastri, deterministica, ecc)
    - come è fatto e dove si trova l'input

- Descrivere il comportamento: *due strade*
    - dire cosa fa al generico passo *i-esimo*
    - spiegare per filo e per segno come si comporta

## *Costruire* MdT

- Definire il tipo di MdT (quanti nastri, deterministica)
- Definire le convenzioni utilizzate (come sono fatte le transazioni, ecc)
- Mostrare **il grafico** (o la lista delle transazioni, ma è da folli)


# Complessità in tempo delle MdT

*Prerequisiti*: avere chiaro cosa fa la MdT e quale linguaggio accetta. Avere il **grafico** aiuta tantissimo.

- Calcolare la complessità per un dato input:
    - segui il percorso che farebbe quell'input *sul grafico* e conta le transazioni
- Calcolare il caso peggiore:
    - *guarda il grafico* e pensa a quale stringa ci gira il più a lungo possibile

**Occhio**: si può esprimere la complessità *non solo* in funzione della lunghezza della/e stringa/stringhe di input (vedi es. 2 di luglio 2015). *Se fai giochini del genere, specificaglielo sempre*, e soprattutto **fallo solo se sei stra-sicuro di quello che scrivi**.

# Dimostrazioni su classi P, NP, NP-C, co-NP

Procedimento generale:
- individuare quali sono gli *elementi dell'ipotesi*
- scrivere subito le *implicazioni immediate* che seguono da quegli elementi
- scrivere ogni implicazione immediata che viene da *combinazioni di elementi dimostrati*
- ragionare sulla tesi e *chiarire dove si vuole arrivare*
- *cercare connessioni* fra quanto è già dimostrato e quello che serve dimostrare per avvalorare la tesi

**Occhio**: se ti dice una cosa per ipotesi, significa che probabilmente *ti serve a qualcosa*.

Cose generali che è comodo sapere:
- P e NP sono chiuse per unione, intersezione, concatenazione e chiusura di Kleene.
- Riduci linguaggi di problemi noti **al** tuo problema. *Non azzardarti a fare il contrario*.

## Dimostrare che un Linguaggio è in NP

-Semplicemente, descrivere una MdT non deterministica che lo accetta in tempo polinomiale

## Dimostrare che un Linguaggio è NP-Hard

- Dimostrare che la **funzione di riduzione** dal *generico linguaggio NP* è computabile in tempo polinomiale
    - dimostrare anche che è corretta, es. specificandola per casi e descrivendone il funzionamento

## Mostrare che classe di complessità (es NPC) è *sottoinsieme proprio* di un altra (es. NP)

- dimostrare che c'è un linguaggio (es. linguaggio vuoto) che appartiene alla superclasse (es. NP) ma non alla sottoclasse (es. NPC)
- se non è immediato, far vedere che la tutta la sottoclasse sta dentro la superclasse

## Dimostrare cose relative alla classe co-NP

Ragiona sull'aspetto *booleano* della cosa (ma non scrivergli niente a riguardo, non si sa mai). 

- L in NP <-> L^c (L complementare) è in co-NP
- Si può usare l'*equivalente* delle *leggi di De Morgan* --- (cfr. es. 2, settembre 2015)
    - **L1 intersezione L2 <-> (L1^c unione L^1c)^c** 
- Si può fare ragionamenti tipo **L - L1 = L intersezione L1^c**
