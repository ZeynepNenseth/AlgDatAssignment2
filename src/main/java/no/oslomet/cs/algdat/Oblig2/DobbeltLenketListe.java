package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        // Konstruktør som setter verdiene lik null/0
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        // Konstruktør som lager dobbelt lenket liste av verdiene i tabellen a

        // Hvis tabell som kommer inn er null kastes en NullPointerException
        Objects.requireNonNull(a, "Tabellen er tom!");

        // Setter antall og endringer lik 0
        antall = 0;
        endringer = 0;

        // Løper gjennom arrayet a
        for (T verdi : a) {
            // Hvis verdien er null blir den ikke tatt med
            if (verdi != null) {
                // Sjekker om dette er første verdi
                if (hode == null) {
                    hode = new Node<>(verdi);
                    hale = hode;
                } else {
                    // Listen har allerede andre elementer
                    Node<T> node = hale;
                    hale = new Node<>(verdi, node, null);
                    node.neste = hale;
                }
                // Øker antall med 1 for hvert element som blir lagt til
                antall++;
            }
        }
    }

    // private hjelpemetode fra oppgave 3b)
    // Denne koden er tatt fra kode 1.2.3a) fra Kompendiet men modifisert i samspill med oppgaven
    private static void fratilKontroll(int antall, int fra, int til) { // [fra:til> halvåpent intervall
        if (fra < 0) { // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > antall) { // til er utenfor tabellen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall av noder(" + antall + ")");
        }

        if (fra > til) { // fra er større enn til
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til); // sjekker at indeksene "fra" og "til" er lovlige

        // lager en ny instans av klassen DobbeltLenketListe av typen Liste
        // kjører indeksen fra "fra" til "til" som halvåpent intervall
        // legger inn verdiene ved å hente dem fra finnNode-metoden sin verdi
        Liste<T> underliste = new DobbeltLenketListe<>();
        for (int i = fra; i < til; i++) {
            underliste.leggInn(finnNode(i).verdi);
        }
        return underliste;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        // Returnerer true dersom listen er tom
        return !(hode != null && hale != null);
    }

    @Override
    public boolean leggInn(T verdi) {
        // Kaster exception hvis verdien er null
        Objects.requireNonNull(verdi, "Nullverdier er ikke tillat");

        // Lager ny node p
        Node<T> p = new Node<>(verdi);

        // hvis listen er tom på forhånd
        if (hode == null && hale == null) {
            hode = p;
            hale = hode;
            antall++;
        } else {
            // hvis listen allerede inneholder elementer
            hale.neste = p;
            p.forrige = hale;
            hale = p;
            antall++;
        }
        endringer++; // ny node lagt til, oppdater endringer.
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        //throw new UnsupportedOperationException();

        Objects.requireNonNull(verdi,"Verdien kan ikke være null!");

        // Sjekke at ikke indeks er negativ eller større enn antall
        if(indeks < 0){
            throw new IndexOutOfBoundsException("negative indekser er ulovlige");
        }
        else if(indeks > antall){
            throw new IndexOutOfBoundsException("indekser større enn antall er ulovlige");
        }

        // Hvis listen er tom - legges verdien som første og pekerne til null
        if(antall ==0){
            Node <T> q = new Node<>(verdi);
            hode = q;
            hale = q;
        }
        // Hvis indeks er 0 - legges verdien som hode
        else if(indeks == 0){
            Node <T> q = hode;
            Node <T> p = new Node<>(verdi);

            hode = p;
            q.forrige = p;
            p.neste = q;
        }
        // Hvis indeksen er siste plassen i listen, legges verdien sist
        else if (indeks == antall){
            Node <T> p = hale;
            Node <T> q = new Node<>(verdi);

            hale = q;
            q.forrige = p;
            p.neste = q;
        }
        // Om noden skal legges i midten, må man finne verdiene den skal i mellom,
        // og sette riktig pekere frem og tilbake
        else {
            Node <T> r = finnNode(indeks);
            Node <T> q = new Node<>(verdi);
            Node <T> p = r.forrige;

            p.neste = q;
            q.neste = r;
            r.forrige = q;
            q.forrige = p;
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        // bruker metoden indeksTil hvor i alle tilfeller utenom verdien finnes i listen, returneres -1
        return indeksTil(verdi) != -1;    // true hvis vi finner en indeks med den gitte verdien (som parameter)
    }

    // privat hjelpemetode fra oppgave 3a)
    private Node<T> finnNode(int indeks) {
    // hvis noden vi leter etter ligger lenge bak i listen, deler vi listen i to for å lete enten forfra eller bakfra
        if (indeks < (antall / 2)) {           // indeksen ligger i den første halvdelen av listen
            Node<T> p = hode;                  // setter en referansevariabel p som hode i listen
            for (int i = 0; i < indeks; i++) { // kjører gjennom listen forfra
                p = p.neste;                   // p flyttes til høyre
            }
            return p;                          // returnerer noden
        } else { // indeksen ligger i den andre halvdelen av listen
            Node<T> p = hale;                              // setter referansevariabelen p som hale i listen
            for (int i = antall - 1; i > indeks; i-- ) {   // kjører gjennom listen bakfra
                p= p.forrige;                              // p flyttes til venstre
            }
            return p;                                      // returnerer noden
        }
    }

    @Override
    public T hent(int indeks) {
        // indeks sjekkes ved å bruke indeksKontroll-metoden fra interface Liste
        // altså at den ikke er mindre enn 0 og at den er mindre enn antall (noder)
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi; // returnerer verdien av typen T til noden med gitt indeks
    }

    @Override
    public int indeksTil(T verdi) {
        // metoden skal returnere indeksen til noden med den gitt verdien som parameter
        if (verdi == null) {  // hvis verdien er null, returneres -1. Ingen unntak kastes.
            return -1;
        }

        Node<T> p = hode;  // setter en referansevariabel "p" som hode
        // kjører gjennom listen forfra siden i oppgaveteksten står at indeksen til første noden
        // fra venstre skal returneres hvis den gitte verdien finnes flere ganger
        for (int i = 0; i < antall; i++) {
            if(p.verdi.equals(verdi)) { // leter etter noden som har samme verdi som den gitt i parameteren
                return i;               // returnerer indeksen
            }
            p = p.neste;                // p flyttes til venstre
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        // indeks sjekkes ved å bruke indeksKontroll-metoden fra interface Liste,
        // altså at den ikke er mindre enn 0 og at den er mindre enn antall (noder)
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi, "Nullverdier er ikke tillatt!");

        // finner noden med den gitte indeksen i listen vha finnNode-metoden og setter det som referansevariabelen "p"
        Node<T> p = finnNode(indeks);
        T tempVerdi = p.verdi;  // tar vare på verdien til p ved å sette den til "tempVerdi"
        p.verdi = nyverdi; // noden p sin verdi oppdateres med den nye verdien
        endringer++;   // endringer økes med 1

        return tempVerdi; // returnerer den gamle verdien
    }

    @Override
    public boolean fjern(T verdi) { // skal fjerne verdi fra listen og så returnere true.
        //throw new UnsupportedOperationException();

        // Her skal det ikke kastes unntak hvis verdi er null.
        // Metoden skal isteden returnere false.
        if(verdi == null){
            return false;
        }

        // Hvis det er flere forekomster av verdier det den første av dem(fra venstre)som skal fjernes.
        // Starter derfor med å sette Node q som hode.
        Node <T> q = hode;

        // Går igjennom listen
        while (q != null){
            if(q.verdi.equals(verdi)){                  // Sjekker om q = verdi
                break;                                  // Stopper og går ut av løkken om den finner lik verdi
            }
            q = q.neste;
        }
        // Har gått igjennom listen uten å finne verdi
        if(q == null){
            return false;
        }

        // Om det kun er en node
        else  if (antall == 1){
            hale = null;
            hode = null;
            antall--;
            endringer++;
            return true;
        }

        // Om verdi er lik hode
        else if(q == hode){
            hode = hode.neste;
            hode.forrige = null;
            antall--;
            endringer++;
            return true;
        }

        // Om verdi er lik hale
        else if(q == hale){
            hale = hale.forrige;
            hale.neste = null;
            antall--;
            endringer++;
            return true;
        }

        // Om verdi ligger i midten
        else {                                          // fjernes q ved å sette
            q.forrige.neste = q.neste;                  // forrige nodes neste-peker til q sin neste
            q.neste.forrige = q.forrige;                // og neste sin forrige-peker til q sin forrige
            antall--;
            endringer++;
            return true;
        }
    }

    @Override
    public T fjern(int indeks) { // skal fjerne(og returnere)verdien på posisjon indeks (som først må sjekkes).
        //throw new UnsupportedOperationException();

        // Koden er kopiert fra Online-kompendiet Programkode 3.3.3 c)
        indeksKontroll(indeks, false);

        T temp;                                      // hjelpevariabel

        if (indeks == 0){                            // skal første verdi fjernes?
            temp = hode.verdi;                       // tar vare på verdien som skal fjernes
            hode = hode.neste;                       // hode flyttes til neste node
            if (antall == 1) {
                hale = null;                         // det var kun en verdi i listen
            }
        }
        else {
            Node<T> p = finnNode(indeks - 1);  // p er noden foran den som skal fjernes
            Node<T> q = p.neste;                     // q skal fjernes
            temp = q.verdi;                          // tar vare på verdien som skal fjernes

            if (q == hale) {
                hale = p;                            // q er siste node
            }
            p.neste = q.neste;                       // "hopper over" q
        }
        antall--;                                    // reduserer antallet
        endringer++;                                 // ny endring.
        return temp;
    }

    @Override
    public void nullstill() {
        // 1. metode : tar 0 sek å kjøre - se kommentarene nedenfor
        // i for-løkken starter vi med å sette en referansevariabel "p" som hode, og kjører gjennom listen så lenge p
        // ikke er null, dvs at vi befinner oss i listen , og vi flytter p'en til venstre
        // dette tilsvarer for (int i = 0; i < antall; i++)
        for (Node<T> p = hode; p != null; p = p.neste) {
            p.verdi = null;    // setter verdien til noden til null
            p.neste = null;    // setter pekeren til forrige node til null
            p.forrige = null;  // setter pekeren til neste node til null
        }
        hode = hale = null;
        antall = 0;
        endringer++;

        /*2. metode: tar ca. 70 sek å kjøre se kommentarene nedenfor
        Node<T> p = hode;  // setter referanse
        while (antall != 0) { // så lenge listen listen ikke er tom
            fjern(0);  // bruker fjern(indeks)-metoden for å fjerne den første noden
            p.forrige = p.neste; // setter den neste pekeren til forrige
        }
            p = hale;
            hale = null;
            antall = 0;
            endringer++;*/

        /* Etter at jeg sjekket begge metodene fungerer, brukte en hjelpemetode som setter inn tall fra 0 til n-1 i en Integer
        tabell.
        public static Integer[] lagTabell(int n) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
        }

        Deretter kjørte jeg koden i main() under public class DobbeltlenketListe sammen med koden til tidsmåling som finnes i
        Kompendiet kap. 1.1.10.
        Integer[] a = lagTabell(5_000_000);
        DobbeltLenketListe<Integer> testListe = new DobbeltLenketListe<>(a);
        long tid = System.currentTimeMillis();
        testListe.nullstill();
        tid = System.currentTimeMillis() - tid;
        System.out.println(tid);
         */
    }

    @Override
    public String toString() {
        // Setter node p lik hode
        Node<T> p = hode;

        // Lager ny String
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        // Går gjennom listen fra hode og fremover. Dersom verdien ikke er null legges den til i Stringen
        for (int i = 0; i < antall; i++) {
            sb.append(p.verdi);
            if (p.neste != null) {
                sb.append(", ");
                p = p.neste;
            }
        }
        // Avslutter Stringen
        sb.append("]");

        // Returnerer Stringen
        return sb.toString();
    }

    public String omvendtString() {
        // Setter node p lik hale
        Node<T> p = hale;

        // Lager ny String
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        // Går gjennom listen fra hale og bakover. Legger til verdien i Stringen dersom den ikke er null
        for (int i = antall; i > 0; i--) {
            sb.append(p.verdi);
            if (p.forrige != null) {
                sb.append(", ");
                p = p.forrige;
            }
        }
        // Avslutter Stringen
        sb.append("]");

        // Returnerer Stringen
        return sb.toString();
    }

    // Oppg 8b. Returnerer en instans av iteratorklassen.
    @Override
    public Iterator<T> iterator() { return new DobbeltLenketListeIterator(); }

    // Oppg 8d. Returnerer en instans av iteratorklassen der 'denne'
    // starter på noden med indeks 'indeks'.
    public Iterator<T> iterator(int indeks) {
        // Indekskontroll.
        indeksKontroll(indeks, false);

        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        // Skal ikke endres.
        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        // Oppg 8c. Skal begynne fra 'denne' på noden som har indeks 'indeks'.
        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks); // Begynner på noden med indeks 'indeks'.
            fjernOK = false; // Får ikke lov å fjerne en node før fjernOK er true.
            iteratorendringer = endringer;
        }

        // Skal ikke endres.
        @Override
        public boolean hasNext() {
            return denne != null;
        }

        // Oppg 8a. Finner neste verdi i listen.
        // Første gang next() kalles, returneres første verdi i listen.
        @Override
        public T next() {
            // Sjekk om iteratorendringer er lik endringer.
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("iteratorendringer er ikke lik endringer! Listen ble modifisert!");

            // Ikke flere elementer igjen i listen.
            if (!hasNext())
                throw new NoSuchElementException("Ikke flere elementer i listen!");

            // Listen er ikke modifisert og har flere elementer.
            fjernOK = true; // Lov å fjerne.
            Node<T> node = denne; // Verdien som skal returneres.
            denne = denne.neste; // Går til neste node i listen.
            return node.verdi;
        }

        // Oppg 9
        @Override
        public void remove() {
            // Sjekk om iteratorendringer er lik endringer.
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("iteratorendinger er ikke lik endringer!");

            // Ikke tillatt å kalle denne metoden
            if (!fjernOK)
                throw new IllegalStateException("Listen har ingen elementer å fjerne, eller må kalle next() først!");

            // Hindringer passert.
            fjernOK = false; // Må kalle next() før vi kan bruke remove() igjen.

            // Noden til VENSTRE for 'denne' skal fjernes.
            // Listen har bare en verdi.
            if (antall == 1) {
                hode = null;
                hale = null;
            }

            // Siste element skal fjernes.
            else if (denne == null) {
                hale = hale.forrige; // hale peker nå på noden til venstre for siste node.
                hale.neste = null; // ny hale sin neste-peker peker ikke på den vi fjernet lenger.
            }

            // Første element skal fjernes.
            else if (denne.forrige == hode) {
                hode = denne; // Setter 'denne' som nytt hode.
                denne.forrige = null; // ny hode sin forrige-peker peker ikke på den vi fjernet lenger.
            }

            // Node midt i listen skal fjernes.
            else {
                // denne.forrige.forrige <-> denne.forrige <-> denne
                // denne.forrige skal fjernes.
                denne.forrige.forrige.neste = denne; // Noden på venstre side sin neste-peker peker på den til høyre.
                denne.forrige = denne.forrige.forrige; // Noden på høyre side sin forrige-peker peker på den til venstre.
            }

            // Element fjernet.
            antall--;
            iteratorendringer++;
            endringer++;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        bubblesort(liste, c);
    }

    // Sortering ved bruk av bubblesort. Kvadratisk orden.
    public static <T> void bubblesort(Liste<T> liste, Comparator<? super T> c) {
        for (int j = 0; j < liste.antall()-1; j++) {
            T verdi1 = liste.hent(0);
            for (int i = 0; i < liste.antall()-1; i++) {
                T verdi2 = liste.hent(i + 1);
                // Hvis verdi1 > verdi2. Bytt!
                if (c.compare(verdi1, verdi2) > 0) {
                    liste.oppdater(i, verdi2);
                    liste.oppdater(i + 1, verdi1);
                } else {
                    verdi1 = verdi2;
                }
            }
        }
    }

    // Prøvde å lage en kvikksorter metode. Kubisk orden.
    public static <T> void kvikksorter(Liste<T> liste, Comparator<? super T> c, int left, int right) {
        if (left >= right) return; // Basistilfelle. Ikke mer intervall igjen.

        // Sett midterste verdi til slutt.
        int m = (left+right)/2;
        T pivot = liste.hent(m);
        liste.oppdater(m, liste.hent(right));
        liste.oppdater(right, pivot);

        // Flytt alt større enn pivot til høyre.
        // Flytt alt mindre enn pivot til venstre.
        int l = left; int r = right-1;
        while (l < r) {
            while ( l <= r && c.compare(liste.hent(l), pivot) < 0) {
                l++;
            }
            while (l <= r && c.compare(liste.hent(r), pivot) >= 0) {
                r--;
            }
            if (l < r) {
                T temp = liste.hent(r);
                liste.oppdater(r, liste.hent(l));
                liste.oppdater(l, temp);
                l++;
                r--;

                // Dersom [6 7 9 8]. 8 er pivot. 6 og 9 ble byttet over. Da er l = r = indeks til 7.
                // l-1 blir da ikke siste verdi som er mindre enn pivot. Da blir pivot satt på feil plass når vi bytter tilbake.
                if (l == r) {
                    if (c.compare(liste.hent(l), pivot) < 0) {
                        l++;
                    }
                }
            }
        }

        // Siste tall mindre enn pivot er l-1.
        // Flytt pivot tilbake.
        T temp = liste.hent(l);
        liste.oppdater(l, liste.hent(right));
        liste.oppdater(right, temp);

        // Gjenta for elementene på venstre og høyre side av pivot.
        kvikksorter(liste, c, left, l-1);
        kvikksorter(liste, c, l+1, right);
    }
    /*
    // Tester hastighet på de ulike sorteringsmetodene laget.
    public static void main(String[] args) {
        // Tester permutasjoner.
        Random r = new Random();
        int sumTid = 0;
        int ant = 200;
        int arrayLengde = 200;
        for (int k = 0; k < ant; k++) {
            Integer[] tall = new Integer[arrayLengde];
            for (int j = 0; j < arrayLengde; j++) {
                tall[j] = j+1;
            }
            // Randomiserer tallene.
            for (int j = arrayLengde-1; j > 0; j--) {
                int rIndeks = r.nextInt(j+1);
                int temp = tall[rIndeks];
                tall[rIndeks] = tall[j];
                tall[j] = temp;
            }

            // Lager DobbeltLenketListe med tallene randomisert.
            Liste<Integer> testliste = new DobbeltLenketListe<>(tall);

            // Ser hvor lang tid sortering (bubblesort) tar for denne listen.
            long tid = System.currentTimeMillis();
            sorter(testliste, Comparator.naturalOrder());
            sumTid += System.currentTimeMillis() - tid;
        }

        System.out.println("Gjennomsnittelig tid (bubblesort) = " + sumTid/ant);
        // Arraylengde = 100: Gjennomsnittelig tid = 1 (Ca - varierer litt fra run til run)
        // Arraylengde = 200: Gjennomsnittelig tid = 4 (Ca - varierer litt fra run til run)
        // Kan virke som om den er av kvadratisk orden.

        // kvikksorter
        // Arraylengde = 400: Gjennomsnittelig tid = 1 (Varierer)
        // Arraylengde = 800: Gjennomsnittelig tid = 8 (Varierer)
        // Kubisk orden?
    }
     */

} // class DobbeltLenketListe
