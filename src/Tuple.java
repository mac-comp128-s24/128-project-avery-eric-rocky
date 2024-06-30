import java.util.Comparator;
import java.util.Objects;

/**
 * Should be java standard functionality
 */
public class Tuple<A, B> {
    public A a;
    public B b;

    /**
     * @param a
     * @param b
     */
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    static <A extends Comparable<? super A>, B> Comparator<Tuple<A, B>> sortByA() {
        return (Comparator<Tuple<A, B>>) (t1, t2) -> t1.a.compareTo(t2.a);
    };

    static <A, B extends Comparable<? super B>> Comparator<Tuple<A, B>> sortByB() {
        return (Comparator<Tuple<A, B>>) (t1, t2) -> t1.b.compareTo(t2.b);
    };

    static <A, B> Comparator<Tuple<A, B>> sortByA(Comparator<? super A> cmp) {
        Objects.requireNonNull(cmp);
        return (Comparator<Tuple<A, B>>) (t1, t2) -> cmp.compare(t1.a, t2.a);
    };

    static <A, B> Comparator<Tuple<A, B>> sortByB(Comparator<? super B> cmp) {
        Objects.requireNonNull(cmp);
        return (Comparator<Tuple<A, B>>) (t1, t2) -> cmp.compare(t1.b, t2.b);
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple<?, ?> that = (Tuple<?, ?>) obj;

        return Objects.equals(this.a, that.a) && Objects.equals(this.b, that.b);

    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "Tuple( " + a.toString() + ", " + b.toString() + " )";
    }

}
