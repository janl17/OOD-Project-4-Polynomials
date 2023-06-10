package polynomial;

public class PolynomialImpl implements Polynomial {

    /**
     * This class represents a polynomial as a linked list of terms, where each term is represented by a coefficient and a power.
     * The terms are sorted in decreasing order of power, with the head of the list being the term with the highest power.
     */
    private static class term {
        private int coefficient;
        private final int power;
        private term next;

        /**
         Constructs a term with the given coefficient, power, and next term in the list.
         @param coefficient the coefficient of the term
         @param power the power of the term
         @param next the next term in the list
         */
        private term(int coefficient, int power, term next) {
            this.coefficient = coefficient;
            this.power = power;
            this.next = next;
        }
    }
    term head;

    /**
     * Constructs an empty polynomial.
     */
    public PolynomialImpl() {
        this.head = null;
    }

    /**
     * Constructs a polynomial from the given string representation.
     * The string should consist of space-separated terms, where each term is of the form "cx^p" or "c" (for a constant term).
     * @param polynomial the string representation of the polynomial
     * @throws IllegalArgumentException when given an invalid string
     */
    public PolynomialImpl(String polynomial) throws IllegalArgumentException {
        if (polynomial.trim().isEmpty()) {
            this.head = null;
            return;
        }
        this.head = null;
        try {
            String[] terms = polynomial.split(" ");


            for (String i : terms) {
                if (!(i.contains("x"))) {
                    this.addTerm(Integer.parseInt(i), 0);

                } else {
                    String[] parts = i.split("x\\^");
                    if (Integer.parseInt(parts[0]) != 0) {
                        this.addTerm(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid input");
        }
    }

    /**
     * Convert a polynomial to a string.
     *
     * @return result    the string converted to
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (head == null) {
            sb.append("0");
        } else {
            term current = head;
            while (current.next != null) {
                if (current == head) {
                    sb.append(current.coefficient);
                    sb.append("x^");
                    sb.append(current.power);
                    sb.append(" ");
                } else {
                    sb.append(String.format("%+d", current.coefficient));
                    sb.append("x^");
                    sb.append(current.power);
                    sb.append(" ");
                }
                current = current.next;
            }
            if (current.power != 0 && current != head) {
                sb.append(String.format("%+d", current.coefficient));
                sb.append("x^");
                sb.append(current.power);

            } else if (current.power == 0 && current != head){
                sb.append(String.format("%+d", current.coefficient));
            } else {
                if (current.power != 0 && current == head) {
                    sb.append(current.coefficient);
                    sb.append("x^");
                    sb.append(current.power);

                } else {
                    sb.append(current.coefficient);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Add this polynomial to another and return the result as another polynomial.
     *
     * @param other the other polynomial to be added
     * @return the resulting polynomial
     * @throws IllegalArgumentException if parameter is not the same concrete type
     *                                  as the current object.
     */
    public Polynomial add(Polynomial other) throws IllegalArgumentException {
        if (!(other instanceof PolynomialImpl)) {
            throw new IllegalArgumentException("Parameter must be the same concrete type as the current object.");
        }
        Polynomial result = new PolynomialImpl();
        for (int i = 0; i <= other.getDegree(); i++) {
            result.addTerm(other.getCoefficient(i), i);
        }
        for (int i = 0; i <= this.getDegree(); i++) {
            result.addTerm(this.getCoefficient(i), i);
        }
        term current = ((PolynomialImpl) result).head;
        term prev = null;
        while (current != null) {
            if (current.coefficient == 0) {
                if (prev == null) {
                    ((PolynomialImpl) result).head = current.next;
                } else {
                    prev.next = current.next;
                }
            } else {
                prev = current;
            }
            current = current.next;
        }
        return result;
    }

    /**
     * Add a term to this polynomial with the specified coefficient and power.
     *
     * @param coefficient the coefficient of the term to be added
     * @param power       the power of the term to be added
     * @throws IllegalArgumentException if the power is negative
     */
    public void addTerm(int coefficient, int power) throws IllegalArgumentException{
        if (power < 0) {
            throw new IllegalArgumentException("Power must not be negative.");
        }
        if (head == null) {
            head = new term(coefficient, power, null);
        } else {
            term current = head;
            term prev = null;
            while (current != null) {
                if (current.power == power) {
                    current.coefficient += coefficient;
                    return;
                } else if (power > current.power) {
                    term newTerm = new term(coefficient, power, current);
                    if (prev == null) {
                        head = newTerm;
                    } else {
                        prev.next = newTerm;
                    }
                    return;
                } else {
                    prev = current;
                    current = current.next;
                }
            }
            prev.next = new term(coefficient, power, null);
        }
    }

    /**
     * Determines if this polynomial is the same as the parameter polynomial.
     *
     * @param poly the polynomial to use
     * @return true if this polynomial is of the same concrete type and has the same
     * terms as the parameter, false otherwise
     */
    public boolean isSame(Polynomial poly) {
        if (!(poly instanceof PolynomialImpl)) {
            return false;
        }
        for (int i = 0; i < poly.getDegree(); i++) {
            if (poly.getCoefficient(i) != this.getCoefficient(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Evaluate the value of this polynomial at the given value of the variable.
     *
     * @param x the value at which the polynomial is to be evaluated.
     * @return the value of the polynomial at x
     */
    public double evaluate(double x) {
        double value = 0;
        term current = head;
        while (current != null) {
            value += current.coefficient * Math.pow(x, current.power);
            current = current.next;
        }
        return value;
    }

    /**
     * Return the coefficient of the term with the given power.
     *
     * @param power the power whose coefficient is sought
     * @return the coefficient at the given power
     */
    public int getCoefficient(int power) {
        term current = head;
        while (current != null) {
            if (current.power == power) {
                return current.coefficient;
            }
            current = current.next;
        }
        return 0;
    }

    /**
     * Get the degree of this polynomial.
     *
     * @return the degree of this polynomial as a whole number
     */
    public int getDegree() {
        if (head == null) {
            return 0;
        }
        return head.power;
    }
}

