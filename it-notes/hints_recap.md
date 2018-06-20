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

# Funzioni Ricorsive Primitive (**RP**)

## Mostrare che una certa funzione è **RP**:

- *Scomporla* (composizione, r.p., minimalizzazione, ecc) in funzioni che sappiamo essere RP o iniziali

## Mostrare che una funzione *definita per casi* è **RP**

- Individuare le **relazioni** associate alle casistiche
- Dimostrare che le relazioni individuate sono **RP**:
    - Dimostrare che la loro *funzione caratteristica* è *RP* (vedi sopra)

