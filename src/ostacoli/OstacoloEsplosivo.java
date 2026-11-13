// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 4: gli ostacoli. Scadenza: domenica 15 novembre 2026.
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/**
 * L'ostacolo esplosivo: si rompe al primo colpo e, quando si rompe, esplode: colpisce una volta
 * ogni ostacolo non rotto che tocca la sua area allargata di PORTATA pixel su ogni lato.
 * È fermo.
 *
 * L'esplosione la fa il contenitore: il metodo colpisciAttorno(zona) della classe Ostacoli, che
 * scrivete voi; qui lo si chiama sul contenitore ricevuto: campo.colpisciAttorno(...).
 * Il rettangolo allargato si ottiene con area.allargato(PORTATA).
 *
 * IL PEZZO MANCANTE: i corpi dei metodi segnati «scrivete qui» e i campi che vi servono.
 */
public class OstacoloEsplosivo implements Ostacolo {
    /** Fin dove arriva l'esplosione: PORTATA pixel oltre ogni lato dell'ostacolo. */
    public static final double PORTATA = 30;
    public static final Color COLORE = new Color(235, 90, 40);

    private final Rettangolo area;
    private boolean hasExploded = false;

    /** Pretende: area non è null. Promette: un ostacolo esplosivo non rotto. */
    public OstacoloEsplosivo(Rettangolo area) {
        if (area == null) {
            throw new IllegalArgumentException("area must not be null");
        }

        this.area = area;
    }

    @Override
    public Rettangolo getArea() { return area; }

    /**
     * Oltre al contratto di Ostacolo: il primo colpo lo rompe, e quando si rompe l'ostacolo
     * esplode (campo.colpisciAttorno con la sua area allargata di PORTATA).
     */
    @Override
    public void colpisci(Ostacoli campo) {
        if (this.isRotto()) {
            throw new IllegalStateException("cannot hit a broken obstacle");
        }

        this.hasExploded = true;
        campo.colpisciAttorno(this.area.allargato(PORTATA));
    }

    @Override
    public boolean isRotto() {
        return this.hasExploded;
    }

    /** Promette: COLORE, anche da rotto. */
    @Override
    public Color getColore() {
        return COLORE;
    }

    @Override
    public void avanza() {
        // nessuna azione
    }
}
