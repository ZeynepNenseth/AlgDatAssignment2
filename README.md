[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=463165&assignment_repo_type=GroupAssignmentRepo)
# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Marianne Heggelund, S189986, S189986@oslomet.no
* Mari Ingolfsrud Innvær, s351915, s351915@oslomet.no
* Vilde Ytterstad Holmøy, s187161, s187161@oslomet.no
* Hatice Zeynep Nenseth, s348818, s348818@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Vilde har hatt hovedansvar for oppgave 1 og 2. 
* Zeynep har hatt hovedansvar for oppgave 3, 4, og 7. 
* Marianne har hatt hovedansvar for oppgave 5 og 6. 
* Mari har hatt hovedansvar for oppgave 8, 9, og 10.
*  

# Oppgavebeskrivelse

Oppgave 1

DobbeltLenketListe(T[] a): I denne konstruktøren har vi først lagt til en Objects.requireNonNull
som kaster en NullPointerException dersom tabellen er tom. Vi setter så antall og endringer lik 0.
Deretter har vi brukt en for-each-løkke som løper gjennom a. Dersom verdien i elementet vi ser på ikke
er null sjekker vi om hode er lik null. Dersom det er det er listen tom og vi setter inn verdier fra
hode også videre. Dersom hode ikke er lik null har listen allerede elementer fra før. Vi setter da Node<T> node
som ny hale, og lager en peker fra forrige hale til den nye halen. Vi øker også antallet med 1 for hver 
gang for-løkken gjennomløpes. 

DobbeltLenketListe(): I denne konstruktøren har vi satt hode og hale lik null,
og antall og endringer lik 0

antall(): Her returnerer vi antall

tom(): Her returneres true dersom det ikke er slik at både hodet og halen ikke er null.

Oppgave 2

toString(): Her setter vi først Node<T> p som hode. Deretter lager vi en ny Stringbuilder sb, 
og legger til en klammeparentes. Vi går så inn i en for-løkke fra 0 til antall og øker med 1 
for hver gjennomgang. Vi legger til verdi i Stringen sb for hver gjennomgang.
Dersom neste node ikke er null legger vi til mellomrom og setter p lik neste node. 
Vi legger så til slutten på klammeparentesen og returnerer Stringen.

omvendtString: Her gjør vi omtrent det samme som i toString-metoden, men her går 
for-løkken motsatt vei. Det vil si at vi først setter Node<T> p som hale. 
For-løkken begynner på antall og fortsetter til og med 1. Vi minker her med 1 for hver gjennomgang.
Vi må da sette p lik forrige node i stedet for neste som vi gjør i toString.
Vi avslutter med klammeparentes og returnerer Stringen.

leggInn(T verdi): Her lager vi først en Objects.requireNonNull som kaster en
NullpointerException fordi nullverdier ikke er tillat.
Deretter lager vi en ny node p. Hvis hode og hale er lik null
vil det si at listen er tom på forhånd.
Vi setter da p lik hode og hale lik hode og øker antall med 1.
Dersom listen inneholder elementer fra før lager vi en peker til p fra den tidligere halen.
Deretter lager vi en peker fra p til den tidligere halen.
Deretter setter vi ny hale lik p og øker antall med 1. Endringer økes også med 1.
Vi returnerer til slutt true.


I oppgave 3a) returnerer hjelpemetoden finnNode "med gitt indeks som parameter" noden med den indeksen. 
I tilfelle om at listen er lang, leter man i halve listen ved å dele antall noder i to og se om indeksen er i første
eller andre halvdel. Hvis indeksen er i første halvdel av listen, starter vi å lete fra første node (hode) fram til 
midten av listen. Hvis indeksen er i andre halvdel av listen starter vi å lete bakfra (hale) til midten av listen. I 
hent-metoden returneres det verdien til noden med gitt parameter hva finnNode-metoden. I oppgave 3b) returnerer man 
verdien av nodene i et halvåpent intervall av listen bestemt av to indekser. fratilKontrol metoden fra Kompendiet 
sjekker at de gitte to indeksen er lovlige.

I oppgave 4 indeksTil-metoden returneres indeksen til den gitte verdien (som parameter) ved å løpe gjennom listen fra 
første node (hode) til siste. Grunnen at vi starter fra første node er at vi vil finne den første noden (sett fra 
starten av listen) med den verdien hvis det finnes flere noder med samme verdi. Hvis verdien ikke finnes eller er null,
returneres -1 (ingen unntak-kasting). I inneholder-metoden returneres true hvis verdien som er gitt i parameteren finnes
i listen. Her bruker vi indeksTil som hjelpemetode hvor tilfellen som ikke returnerer -1 er true.

I oppgave 5 er det laget en metode som legger inn en verdi på angitt indeks. 
Først sjekkes det at ikke verdien er NULL. Og så sjekkes det at det er lovlig indeks.
Så går metoden igjennom de fire tilfellene: 

1: Hvis listen er tom - her legges verdien som hode og hale, og begge pekerne settes til null.

2: Hvis verdien skal legges først - Her legges verdien som hode, og forrige-peker til null. 
Neste-peker blir til den noden som tidligere var hode.

3: Verdien skal legges bakerst - Her settes verdien til hale, 
forrige-peker blir til den som tidligere var hale, og neste-pekeren blir null.

4: Verdien skal legges i mellom to andre verdier - Her brukes finnNode(indeks) metoden for å finne indeksen 
der den nye verdien skal inn. Den nye verdien sine pekere settes til de to nodene den skal i mellom, og de to 
"gamle" nodene bytter pekere til den nye noden.

Til slutt oppdateres antall og endringer.

I oppgave 6 er det laget to metoder for å fjerne. I den første metoden fjernes en gitt indeks, for så å returnere 
verdien som var der. Her er brukt en kode fra kompendiet (Programkode 3.3.3 c). Her brukes først indeksKontroll() for 
å sjekke at indeksen er lovlig. Hjelpevariablen temp settes for å ta vare på verdien som fjernes. Det sjekkes for 
om indeks er først, midt i, eller siste, og fjernes ved at pekerne endres. Til slutt reduseres antall, og den fjernede 
verdien returneres.

I den andre metoden fjernes en gitt verdi, og returnerer true om den blir fjernet, og false om den ikke finnes. 
Her startes det med å sjekke om verdien er NULL, så skal det returnere false. Så er det laget en while-løkke som går
igjennom listen fra hode til hale, og stopper om den finner rett verdi. Om den ikke finner verdien returneres false.
Om den finner verdien sjekkes det om verdien er eneste i listen, om den er først i listen, om den er bakerst i listen,
og om den ligger midt i listen. Pekerne endres, og den returnerer true.



I oppgave 7 brukte man to metoder for å så måle tidsforbruke til dem som er en mal for effektivitet av metoden.
I første metoden starter man med første node (hode) og kjører gjennom listen ved å sette verdien til noden til null og
dens høyre og venstre pekerne til null. I den andre metoden brukes fjern-metoden i en while-løkke. I løkken starter man
ved å fjerne den første noden (indeks = 0). I tillegg må antall av noder oppdateres ved å sette den på 0 (listen er tom),
og antall av endringer oppdateres med å øke den.

I oppgave 8 følgte jeg oppgaven trinnvis. Først måtte en exception kastes
dersom iteratorendringer og endringer er forskjellige. Deretter måtte en exception kastes
dersom listen ikke har flere elementer. Hvis ingen exception ble kastet, så returnerer man verdien til noden vi
er på og går videre til neste. 
DobbeltLenketListeIterator(int indeks) gjør det samme som DobbeltLenketListeIterator(), bortsett fra at vi starter på node med indeks 'indeks'.
Brukte finnNode(indeks) til å finne noden jeg trengte.
iterator() og iterator(indeks) sender bare en instans av DobbeltLenketListeIterator.

I oppgave 9 begynte jeg med å kaste en exception dersom listen har blitt modifisert eller fjernOK er false.
Hvis vi ikke kaster exception, sett fjernOK til false. Da må next() metoden kalles og ikke kaste exception for at vi kan kalle remove() igjen.
Etter det kan vi fjerne noden. Fjerner noden på litt forskjellige måter.
Første måte er om det kun er 1 node i listen, andre måte er om vi fjerner siste node, tredje måte er om vi fjerner første node,
ellers er noden vi skal fjerne i midten av listen et sted.
Husk til slutt å oppdatere antall og endringer.

I oppgave 10 begynte jeg å vurdere ulike sorteringsalgoritmer.
Valgte å starte med bubblesort. Første versjon hadde en kubisk orden. Forbedret denne - da fikk jeg kvadratisk orden.
Det er denne versjonen som finnes i DobbeltLenketListe.java. Valgte å la en del av testkoden stå i DobbeltLenketListe.java.
Der gjorde jeg tidstester. Bubblesort-metoden ser ut til å være kvadratisk.
Fant først en arraylengde slik at metoden bruker ca 1 ms. Dobbelt så lang arraylengde ga ca 4 ms.
Dvs. kvadratisk orden.
Deretter laget jeg en kvikksort-metode. Denne viste seg å være kubisk. Dobbelt så lang arraylengde ga ca 8 ms.

Warning-kommentarer:
Linje 578: Advarsel viser at kvikksort aldri blir brukt. Vi ønsker likevel å ha den med
fordi vi har brukt den til å sjekke om denne metoden er mer effektiv enn bubblesort-metoden. 
Det var den ikke. 

Svake warnings-kommentarer:
Linje 125: Foreslår å endre if-setningen, men vi forstår det bedre på måten vi har gjort det. 
Linje 203: Foreslår å endre if-setningen, men vi forstår det bedre på måten vi har gjort det.
Linje 361: Warning sier at p.neste alltid er null. Vi har gjort det slik det bes om i oppgaveteksten og testen kjører
så vi lar den stå.
Linje 623: Kode er kommentert ut, men vi har valgt å la dette stå fordi vi mener det viser effektivitet slik det spørres 
om i oppgaven.