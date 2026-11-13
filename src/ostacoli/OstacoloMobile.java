// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 4: gli ostacoli. Scadenza: domenica 15 novembre 2026.
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/**
 * L'ostacolo mobile: va su e giù fra un limite alto e un limite basso, sempre alla stessa velocità,
 * e ai limiti torna indietro. Non si rompe mai: colpito, non cambia niente.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi segnati «scrivete qui» e i campi che vi servono.
 * I contratti dei metodi sono quelli dell'interfaccia Ostacolo (aprite Ostacolo.java), più quello
 * che dice questo commento e quelli sopra il costruttore e sopra avanza.
 */
public class OstacoloMobile implements Ostacolo {
    public static final Color COLORE = new Color(80, 200, 120);

    private Rettangolo area;   // non è final: l'ostacolo si muove
    private final double speed;
    private final double upperBound;
    private final double lowerBound;
    private boolean isMovingDown = true;

    private boolean isInsideVerticalBoundaries(double y, double height, boolean inclusive) {
        double yWithHeight = y + height;

        if (
            !inclusive && (y > this.upperBound && yWithHeight < this.lowerBound) ||
            inclusive && (y >= this.upperBound && yWithHeight <= this.lowerBound)
        ) {
            return true;
        }

        return false;
    }

    /**
     * Pretende: area non è null; velocita è maggiore di 0; l'area sta fra i due limiti
     * (limiteAlto <= area.y() e area.y() + area.altezza() <= limiteBasso).
     * Promette: un ostacolo che sta in area e che, al primo avanza, comincia a scendere.
     */
    public OstacoloMobile(Rettangolo area, double velocita, double limiteAlto, double limiteBasso) {
        if (area == null) {
            throw new IllegalArgumentException("area must not be null");
        }
        if (velocita <= 0) {
            throw new IllegalArgumentException("velocita must be greater than zero");
        }
        if (limiteAlto >= limiteBasso) {
            throw new IllegalArgumentException("the provided lower bound must be greater than the provided upper bound");
        }

        this.upperBound = limiteAlto;
        this.lowerBound = limiteBasso;

        if (!isInsideVerticalBoundaries(area.y(), area.altezza(), true)) {
            throw new IllegalArgumentException("area must be inside the provided bounds");
        }

        this.area = area;
        this.speed = velocita;
    }

    @Override
    public Rettangolo getArea() { return area; }

    /** Non si rompe mai: il colpo non cambia niente (né dove sta, né come si muove). */
    @Override
    public void colpisci(Ostacoli campo) {
        // nessuna azione
    }

    @Override
    public boolean isRotto() {
        return false;
    }

    /** Promette: COLORE. */
    @Override
    public Color getColore() {
        return COLORE;
    }

    /**
     * Promette: l'ostacolo si sposta in verticale di velocita pixel nel verso in cui sta andando
     * (all'inizio verso il basso). Se così arriverebbe su un limite o lo supererebbe, si ferma
     * esattamente sul limite e cambia verso: al passo dopo va dall'altra parte.
     * Il bordo alto non va mai sopra limiteAlto, il bordo basso mai sotto limiteBasso
     * (area.y() + area.altezza() <= limiteBasso); x, larghezza e altezza non cambiano.
     */
    @Override
    public void avanza() {
        double newY;
        if (this.isMovingDown) {
            newY = this.area.y() + this.speed;
        } else {
            newY = this.area.y() - this.speed;
        }

        if (!isInsideVerticalBoundaries(newY, this.area.altezza(), false)) { // tests expect this to be non-inclusive?
            newY = this.isMovingDown ? this.lowerBound - this.area.altezza() : this.upperBound;
            this.isMovingDown = !this.isMovingDown;
        }

        this.area = new Rettangolo(
            this.area.x(),
            newY,
            this.area.larghezza(),
            this.area.altezza()
        );
    }
}
