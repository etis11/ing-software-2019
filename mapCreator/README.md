Il mapCreator serve a creare file json di configurazione per le mappe in maniera interattiva.
L'utente deve attivare delle celle all'interno della finstra dell'applicazion per creare tile.
COMANDI:
left-click = creazione di un tile appartenente alla stanza corrente. E' un tile che può contenere munizioni ma non armi.

right-click = creazione di un tile appartenente alla stanza corrente. E' un tile che può contenere armi ma non munizioni. Il tile attuale è   settato come punto di rigenerazione. ATTENZIONE: il primo weaponTile sarà considerato sempre un "red" regent point, il secondo "blue", il terzo "yellow", indipendentemente dal colore della tile presente sul display.

key : 'r' = cambio della stanza. Ad esempio, si passa dalla stanza 0 alla stanza 1. Il colore codifica diverse stanze.
ORDINE DI APPARIZIONE DELLE STANZE = rosso, blu, giallo, verde, viola, grigio.
ATTENZIONE: Il colore della stanza e la codifica dei punti di rigenerazione non sono collegati.

key : 'p' = aggiunta di un player all'interno della stanza puntata dal mouse. Possono essere aggiunti fino a 5 player.
ORDINE DI APPARIZIONE DEY PLAYER: "Banshee", "Dozer", "Distruttore", "Sprog", "Violetta". I player sono tutti codificati dalle stesso colore.

key : 'd' = Una volta premuto il tasto d, non è più possibile creare tile di alcun tipo. Da questo momento, con il primo left-click si selezione un tile di partenza, con il secondo un tile di arrivo e viene creata una porta tra i due tile. I due tile devono essere di due stanze diverse. Graficamente, la porta è rappresentata da una linea grigia.
NB:E' ancora possibile aggiuntere player

MOLTO IMPORTANTE: la serializzazione da json è affidata all'applicazione che farà uso di questi file, pertanto questa applicazione non fornisce alcuna primitiva di deserializzazione.
