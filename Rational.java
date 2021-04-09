public class Rational {
    private int numerator;//top the fraction
    private int denominator;//down the fraction

//in this class infinite modify as 1/0 or any fraction with zero denominator

    public Rational() {

    }

    public Rational(int numerator, int denominator) {
        int[] temp = simplify(numerator, denominator);

        setNumerator(temp[1]);
        setDenominator(temp[2]);
    }

    /**
     * create a rational with numerator=num and denominator=1
     *
     * @param num num
     */
    public Rational(int num) {
        this(num, 1);
    }


    /**
     * received a rational type string or just a number
     * and create a rational
     * param ex. 3/9 or 4 or 7/19
     *
     * @param rationalStr rationalStr
     */
    public Rational(String rationalStr) {

        if (!isItRational(rationalStr))
            throw new ArithmeticException("\n > Error 6 : wrong type of input" + " your input { " + rationalStr + " }");


        String[] parameter = rationalStr.split("/");
        int tempNumerator = Integer.parseInt(parameter[0]);
        int tempDenominator;
        try {
            tempDenominator = Integer.parseInt(parameter[1]);
        } catch (Exception e) {
            tempDenominator = 1;
        }
        int[] temp = simplify(tempNumerator, tempDenominator);
        setNumerator(temp[1]);
        setDenominator(temp[2]);

    }

    /*is it rational*/

    /**
     * check is the string could be a rational
     *
     * @param string string
     * @return true when is it rational
     */
    public static boolean isItRational(String string) {

        try {
            Integer.parseInt(string);return true;
        }catch (Exception e) {
            String[] forCheck = string.split("/", 2);
            try {
                Integer.parseInt(forCheck[0]);
                Integer.parseInt(forCheck[1]);
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public int getNumerator() {
        return this.numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("\n> Error 1 : the denominator of the fraction cant be zero or null");
            //  System.exit(-1);
        }//else
        this.denominator = denominator;
    }



    /* simplify */

    /**
     * in this function rational simplified
     *
     * @param rational rational
     * @return the simplified rational
     */
    public Rational simplify(Rational rational) {
        int tempNumerator = rational.getNumerator();
        int tempDenominator = rational.getDenominator();
        Gcd gcd = new Gcd(tempNumerator, tempDenominator);
        Rational ans = new Rational();
        if ((tempNumerator > 0 && tempDenominator < 0) ||//like 3/-2
                (tempNumerator < 0 && tempDenominator < 0))//like -2/-3
        {
            tempNumerator *= -1;
            tempDenominator *= -1;
        }
        ans.setNumerator(tempNumerator / (int) gcd.getGcd());
        ans.setDenominator(tempDenominator / (int) gcd.getGcd());
        return ans;
    }

    /**
     * receive two number and simplified them
     *
     * @param num1 num1
     * @param num2 num2
     * @return int[0] is gcd of two number and int[1] simplify num1 and int[2] simplify num1
     */
    public int[] simplify(int num1, int num2) {
        Gcd gcd = new Gcd(num1, num2);
        int[] ans = new int[3];
        ans[0] = (int) gcd.getGcd();
        if ((num1 < 0 && num2 < 0) //like -2 and -3
                || (num1 > 0 && num2 < 0)) // like 3 and -8
        {
            num1 *= -1;
            num2 *= -1;
        }
        ans[1] = num1 / ans[0];
        ans[2] = num2 / ans[0];
        return ans;
    }

    /*add*/

    /**
     * receive other rational and added to current rational
     *
     * @param otherRational otherRational
     * @return simplified added rational
     */
    public Rational add(Rational otherRational) {
        if (otherRational.getDenominator() == 0 || this.getDenominator() == 0)
            return new Rational(1, 0);

        if (otherRational.getNumerator() == 0 && this.getNumerator() == 0) return new Rational(0, 1);
        if (otherRational.getNumerator() == 0) return new Rational(this.getNumerator(), this.getDenominator());
        if (this.getNumerator() == 0) return otherRational;

        int tempNumerator = (this.getNumerator() * otherRational.getDenominator())
                + (otherRational.getNumerator() * this.getDenominator());
        int tempDenominator = this.getDenominator() * otherRational.getDenominator();

        return new Rational(tempNumerator, tempDenominator);
    }


    /**
     * receive a rational by numerator & denominator and added current rational
     *
     * @param rationalNumerator   rationalNumerator
     * @param rationalDenominator rationalDenominator
     * @return simplified added rational
     */
    public Rational add(int rationalNumerator, int rationalDenominator) {
        return add(new Rational(rationalNumerator, rationalDenominator));
    }

    /*sub*/

    /**
     * receive other rational and subbed the current rational
     *
     * @param otherRational otherRational
     * @return simplified subbed rational
     */
    public Rational sub(Rational otherRational) {
        return add(otherRational.getNumerator() * -1, otherRational.getDenominator());
    }


    /**
     * receive a rational by numerator & denominator and rational1 minus rational2
     *
     * @param rationalNumerator   rationalNumerator
     * @param rationalDenominator rationalDenominator
     * @return simplified subbed rational
     */
    public Rational sub(int rationalNumerator, int rationalDenominator) {
        return add(rationalNumerator * -1, rationalDenominator);
    }

    /*multiply*/

    /**
     * receive other rational and multiplied the current rational
     *
     * @param otherRational otherRational
     * @return simplified multiplied rational
     */
    public Rational mul(Rational otherRational) {
        if (this.getDenominator() == 0 || otherRational.getDenominator() == 0) {
            throw new ArithmeticException("\n> Error 1 : the denominator of the fraction cant be zero or null");
        }

        int tempNumerator = this.getNumerator() * otherRational.getNumerator();
        int tempDenominator = this.getDenominator() * otherRational.getDenominator();
        return new Rational(tempNumerator, tempDenominator);
    }


    /**
     * receive a rational by numerator & denominator and multiplied with current rational
     *
     * @param rationalNumerator   rationalNumerator
     * @param rationalDenominator rationalDenominator
     * @return simplified multiplied rational
     */
    public Rational mul(int rationalNumerator, int rationalDenominator) {
        return mul(new Rational(rationalNumerator, rationalDenominator));
    }

    /*divide*/

    /**
     * receive a rational and dived current rational by this rational
     *
     * @param otherRational otherRational
     * @return simplified divided rational
     */
    public Rational div(Rational otherRational) {
        return mul(otherRational.revers());
    }


    /**
     * receive a rational by numerator & denominator and dived this rational by that
     *
     * @param rationalNumerator   rationalNumerator
     * @param rationalDenominator rationalDenominator
     * @return simplified divided rational
     */
    public Rational div(int rationalNumerator, int rationalDenominator) {
        return new Rational().mul(rationalDenominator, rationalNumerator);
    }

    /*reversing*/

    /**
     * receive current rational and return the a new reversed rational
     *
     * @return simplified reversed rational
     */
    public Rational revers() {
        return new Rational(this.getDenominator(), this.getNumerator());
    }


    /**
     * receive a rational and return the a new reversed rational
     *
     * @param rational rational
     * @return simplified reversed rational
     */
    public Rational revers(Rational rational) {
        return new Rational(rational.getDenominator(), rational.getNumerator());
    }

    /*to string*/

    /**
     * covert a rational to string
     *
     * @return rationalStr
     */
    public String toString() {
        String ans = "";
        ans += getNumerator() + "/" + getDenominator();
        return ans;
    }

    /*printing*/

    /**
     * print the rational like a fraction
     */
    public void printRational() {

        System.out.println("------------------------------------------------");
        if (getNumerator() == 0) {
            System.out.println("   zero ( 0 )  ");
            System.out.println("------------------------------------------------");
            return;
        }
        if (getDenominator() == 0) {
            System.out.println(" infinity ( \u221E )  is the answer ");
            System.out.println("------------------------------------------------");
            return;
        }
        int numNumerator = String.valueOf(getNumerator()).length();
        int numDenominator = String.valueOf(getDenominator()).length();
        int numLine = 1 + Math.max(numDenominator, numNumerator) + 1;

        String lineStr = "\u23AF";//look like ⎯

        if (getNumerator() >= 0) {
            if (getNumerator() / getDenominator() <= 0 || getNumerator() % getDenominator() == 0) {
                System.out.println("  " + " ".repeat((numLine - numNumerator) / 2) + getNumerator());

                System.out.println("  " + lineStr.repeat(numLine));

                System.out.println("  " + " ".repeat((numLine - numDenominator) / 2) + getDenominator());
            } else// getNumerator() % getDenominator()>0
            {
                int numNumeratorSimplified = String.valueOf(getNumerator() % getDenominator()).length();
                int numBeforeFractionSimplified = String.valueOf(getNumerator() / getDenominator()).length();
                //  numDenominator is numDenominatorSimplified.
                int numLineSimplified = Math.max(numDenominator, numNumeratorSimplified) + 2;
                numBeforeFractionSimplified++;

                System.out.println("  " + " ".repeat((numLine - numNumerator) / 2) + getNumerator() + " ".repeat((numLine - 1 - numNumerator))
                        + " ".repeat(4)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numNumeratorSimplified) / 2) + (getNumerator() % getDenominator()));

                System.out.println("  " + lineStr.repeat(numLine) + " = " + (getNumerator() / getDenominator()) + "(" + lineStr.repeat(numLineSimplified) + ")");

                System.out.println("  " + " ".repeat((numLine - numDenominator) / 2) + getDenominator() + " ".repeat((numLine - 1 - numDenominator))
                        + " ".repeat(4)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numDenominator) / 2) + getDenominator());

            }

        } else //getNumerator() <0
        {
            int positiveNumerator = getNumerator() * -1;
            if (positiveNumerator / getDenominator() <= 0 || getNumerator() % getDenominator() == 0)//and //getNumerator() <0
            {
                System.out.println("   " + " ".repeat((numLine - numNumerator) / 2) + positiveNumerator);

                System.out.println(" - " + lineStr.repeat(numLine));

                System.out.println("   " + " ".repeat((numLine - numDenominator) / 2) + getDenominator());
            } else// positiveNumerator % getDenominator()>0//and //getNumerator() <0
            {
                int numNumeratorSimplified = String.valueOf(positiveNumerator % getDenominator()).length();
                int numBeforeFractionSimplified = String.valueOf(positiveNumerator / getDenominator()).length();
                //  numDenominator is numDenominatorSimplified.
                int numLineSimplified = Math.max(numDenominator, numNumeratorSimplified) + 2;
                numBeforeFractionSimplified++;

                System.out.println("   " + " ".repeat((numLine - numNumerator) / 2) + positiveNumerator
                        + " ".repeat((numLine - 1 - numNumerator))
                        + " ".repeat(6)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numNumeratorSimplified) / 2) + (positiveNumerator % getDenominator()));

                System.out.println(" - " + lineStr.repeat(numLine) + " = -" + (positiveNumerator / getDenominator()) + "(" + lineStr.repeat(numLineSimplified) + ")");

                System.out.println("   " + " ".repeat((numLine - numDenominator) / 2) + getDenominator() + " ".repeat((numLine - 1 - numDenominator))
                        + " ".repeat(4)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numDenominator) / 2) + getDenominator());

            }
        }


        System.out.println("------------------------------------------------");
    }


    /**
     * print the rational like a fraction and it end with the 'is the answer'
     */
    public void printAnswerRational() {
        System.out.println("------------------------------------------------");
        if (getNumerator() == 0) {
            System.out.println("   zero ( 0 )   is the answer ");
            System.out.println("------------------------------------------------");
            return;
        }
        if (getDenominator() == 0) {
            System.out.println(" infinity ( \u221E )  is the answer ");
            System.out.println("------------------------------------------------");
            return;
        }
        int numNumerator = String.valueOf(getNumerator()).length();
        int numDenominator = String.valueOf(getDenominator()).length();
        int numLine = Math.max(numDenominator, numNumerator) + 2;
        String lineStr = "\u23AF";//look like ⎯

        if (getNumerator() >= 0) {
            if (getNumerator() / getDenominator() <= 0 || getNumerator() % getDenominator() == 0) {
                System.out.println("  " + " ".repeat((numLine - numNumerator) / 2) + getNumerator());

                System.out.println("  " + lineStr.repeat(numLine) + "  is the answer");

                System.out.println("  " + " ".repeat((numLine - numDenominator) / 2) + getDenominator());
            } else// getNumerator() / getDenominator()>0
            {
                int numNumeratorSimplified = String.valueOf(getNumerator() % getDenominator()).length();
                int numBeforeFractionSimplified = String.valueOf(getNumerator() / getDenominator()).length();
                //  numDenominator is numDenominatorSimplified.
                int numLineSimplified = Math.max(numDenominator, numNumeratorSimplified) + 2;
                numBeforeFractionSimplified++;

                System.out.println("  " + " ".repeat((numLine - numNumerator) / 2) + getNumerator() + " ".repeat((numLine - 1 - numNumerator))
                        + " ".repeat(4)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numNumeratorSimplified) / 2) + (getNumerator() % getDenominator()));

                System.out.println("  " + lineStr.repeat(numLine) + " = "
                        + (getNumerator() / getDenominator()) + "(" + lineStr.repeat(numLineSimplified) + ")  is the answer");

                System.out.println("  " + " ".repeat((numLine - numDenominator) / 2) + getDenominator() + " ".repeat((numLine - 1 - numDenominator))
                        + " ".repeat(4)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numDenominator) / 2) + getDenominator());

            }

        } else //getNumerator() <0
        {
            int positiveNumerator = getNumerator() * -1;
            if (positiveNumerator / getDenominator() <= 0 || getNumerator() % getDenominator() == 0)//and //getNumerator() <0
            {
                System.out.println("   " + " ".repeat((numLine - numNumerator) / 2) + " ".repeat(1) + positiveNumerator);

                System.out.println(" - " + lineStr.repeat(numLine) + "  is the answer");

                System.out.println("   " + " ".repeat((numLine - numDenominator) / 2) + " ".repeat(0) + getDenominator());
            } else// positiveNumerator % getDenominator()>0//and //getNumerator() <0
            {
                int numNumeratorSimplified = String.valueOf(positiveNumerator % getDenominator()).length();
                int numBeforeFractionSimplified = String.valueOf(positiveNumerator / getDenominator()).length();
                //  numDenominator is numDenominatorSimplified.
                int numLineSimplified = Math.max(numDenominator, numNumeratorSimplified) + 2;
                numBeforeFractionSimplified++;

                System.out.println("   " + " ".repeat((numLine - numNumerator) / 2) + positiveNumerator + " ".repeat((numLine - 1 - numNumerator))
                        + " ".repeat(6)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numNumeratorSimplified) / 2) + (positiveNumerator % getDenominator()));

                System.out.println(" - " + lineStr.repeat(numLine) + " = -" + (positiveNumerator / getDenominator()) + "(" + lineStr.repeat(numLineSimplified) + ")  is the answer");

                System.out.println("   " + " ".repeat((numLine - numDenominator) / 2) + getDenominator() + " ".repeat((numLine - 1 - numDenominator))
                        + " ".repeat(4)
                        + " ".repeat(numBeforeFractionSimplified)
                        + " ".repeat((numLineSimplified - numDenominator) / 2) + getDenominator());

            }
        }


        System.out.println("------------------------------------------------");

    }
}
