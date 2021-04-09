import java.util.*;

public class RationalTest {
    public static void rationalTest() {
        System.out.println("test start : \uD83D\uDE09");

        System.out.println("test 1");
        calculate("+5/3* 1/2+1/4 ").printAnswerRational();
        System.out.println("test 2");
        calculate("-5/3*((3/5)+()2)+(-5/6+1/6):(9/3)+((5/4+1)*3/5+9/4)-5:(-1/2)*2" +
                "+(2)+( 5/6 ): 9/3 +((5/4+1)*3/4)+7/3*5/2:(1/3-2/4:913/7)*15/4+100/875" + ":(-1/2)*2+(2)-(5/6+1/6):(9/3)+((5/4+1)*3/2)").printAnswerRational();
        System.out.println("test 3");
        calculate("+5/3*3/5:1/2+ +1/2 ").printAnswerRational();
        System.out.println("test 4");
        calculate("1/2*3/0").printAnswerRational();

        System.out.println("test ended  \uD83D\uDC4B ");
    }

    /**
     * received a string of fraction and mathematics symbol and
     * make some changes by going into CurationForConvert()
     * than go to calculatorBase()
     *
     * @param phrase phrase
     * @return answer of the phrase in type of rational or
     */
    public static Rational calculate(String phrase) {
        phrase = CorrectionForConvert(phrase);
        return calculatorBase(phrase);
    }

    public static Rational calculatorBase(String phrase) {
        if (phrase.contains(")") || phrase.contains("("))
            return convertP(phrase);

        return convert(phrase);
    }

    /**
     * make a adjustment needed for convert
     *
     * @param phrase phrase
     * @return Correct Convert
     */
    private static String CorrectionForConvert(String phrase) {
        int lPhrase = phrase.length(), numPLift = 0, numPRight = 0;
        for (int indexL = 0, indexR = lPhrase - 1; indexL < lPhrase; indexL++, indexR--) {
            if (phrase.charAt(indexL) == '(') numPLift++;
            if (phrase.charAt(indexR) == ')') numPRight++;
        }
        if (numPLift != numPRight) {
            throw new ArithmeticException("\n> Error 2 : number of parenthesis should be equal");
        }
        if (phrase.contains("::") || phrase.contains("**") || phrase.length() < 1) {
            throw new ArithmeticException("\n> Error 3 : phrase shouldn't have ** or :: ");
        }

        phrase = phrase.trim();
        phrase = phrase
                .replace("()", "")
                .replace(" ", "")
                .replace("\"", "")
                .replace("--", "+")
                .replace("++", "+")
                .replace("-+", "-")
                .replace("+-", "-")
                .replace("-(-", "+(")
                .replace("-(+", "-(")
                .replace("+(+", "+(")
                .replace("+(-", "-(");

        System.out.println("\n===> " + phrase);//show the original phrase

        if (phrase.charAt(0) == '-') phrase = phrase.replaceFirst("-", "FirstMinus");
        if (phrase.charAt(0) == '+') phrase = phrase.replaceFirst("\\+", "FirstPlus");
        String phraseChanged = phrase.replace("-", "M");
        phraseChanged = phraseChanged.replace("+", "P");

        phraseChanged = phraseChanged
                .replace("FirstMinus", "-")
                .replace("FirstPlus", "+");
//D is for Division
//T is for Times
//M if for Minus
//P is for Plus
        phraseChanged = phraseChanged
                .replace(":M", ":-")
                .replace("(M", "(-")
                .replace("*M", "*-")
                .replace(":P", ":+")
                .replace("(P", "(+")
                .replace("*P", "*+")
                .replace("*", "T")
                .replace(":", "D");

        return phraseChanged;
    }


    /**
     * received one string phrase without parenthesis
     * and convert to the rational and math symbol
     * than do the math
     *
     * @param phrase phrase
     * @return answer
     */
    public static Rational convertReturnable(String phrase) {
        if (Rational.isItRational(phrase))
            return new Rational(phrase);

//D is for Division
//T is for Times
//M if for Minus
//P is for Plus

        if (phrase.contains("M"))// sub
        {

            String[] phraseSplitSub = phrase.split("M");
            phraseSplitSub = MyString.removeNulls(phraseSplitSub);
            Rational ans = calculatorBase(phraseSplitSub[0]);
            for (int index = 1; index < phraseSplitSub.length; index++)
                ans = ans.sub(calculatorBase(phraseSplitSub[index]));

            return ans;
        }
        if (phrase.contains("P"))// add
        {
            String[] phraseSplitAdd = phrase.split("P");
            phraseSplitAdd = MyString.removeNulls(phraseSplitAdd);
            Rational ans = calculatorBase(phraseSplitAdd[0]);
            for (int index = 1; index < phraseSplitAdd.length; index++)
                ans = ans.add(calculatorBase(phraseSplitAdd[index]));

            return ans;
        }
        if (phrase.contains("D"))// divide
        {
            String[] phraseSplitDiv = phrase.split("D");
            phraseSplitDiv = MyString.removeNulls(phraseSplitDiv);
            Rational ans = calculatorBase(phraseSplitDiv[0]);
            for (int index = 1; index < phraseSplitDiv.length; index++)
                ans = ans.div(calculatorBase(phraseSplitDiv[index]));

            return ans;
        }
        if (phrase.contains("T"))// multiple
        {
            String[] phraseSplitMul = phrase.split("T");
            phraseSplitMul = MyString.removeNulls(phraseSplitMul);
            Rational ans = calculatorBase(phraseSplitMul[0]);
            for (int index = 1; index < phraseSplitMul.length; index++)
                ans = ans.mul(calculatorBase(phraseSplitMul[index]));

            return ans;
        }

        throw new ArithmeticException("\n> Error 4 : something went wrong");

    }

    public static Rational convert(String phrase) {
        if (Rational.isItRational(phrase))
            return new Rational(phrase);

//D is for Division
//T is for Times
//M if for Minus
//P is for Plus

        List<String> list = new ArrayList<>(Arrays.asList(
                MyString.splitStringInclude(phrase, "T", "D", "M", "P")
        ));
        for (int index = 1; index < list.size(); index++)
            switch (list.get(index)) {
                case "T": {
                    Rational rational1 = new Rational(list.get(index - 1));
                    Rational rational2 = new Rational(list.get(index + 1));
                    Rational rationalAns1and2 = rational1.mul(rational2);

                    list.set(index, rationalAns1and2.toString());
                    list.remove(index + 1);
                    list.remove(index - 1);
                    //      index = 0;
                    break;
                }
                case "D": {
                    Rational rational1 = new Rational(list.get(index - 1));
                    Rational rational2 = new Rational(list.get(index + 1));
                    Rational rationalAns1and2 = rational1.div(rational2);

                    list.set(index, rationalAns1and2.toString());
                    list.remove(index + 1);
                    list.remove(index - 1);
                    //     index = 0;
                    break;
                }
                case "P": {
                    Rational rational1 = new Rational(list.get(index - 1));
                    Rational rational2 = new Rational(list.get(index + 1));
                    Rational rationalAns1and2 = rational1.add(rational2);

                    list.set(index, rationalAns1and2.toString());
                    list.remove(index + 1);
                    list.remove(index - 1);
                    //   index = 0;
                    break;
                }
                case "M": {
                    Rational rational1 = new Rational(list.get(index - 1));
                    Rational rational2 = new Rational(list.get(index + 1));
                    Rational rationalAns1and2 = rational1.sub(rational2);

                    list.set(index, rationalAns1and2.toString());
                    list.remove(index + 1);
                    list.remove(index - 1);
                    //    index = 0;
                    break;
                }
                default:
            }

        return new Rational(list.get(0));
    }

    /**
     * received one string phrase with  parenthesis
     * and convert to the rational and math symbol
     * than do the math
     *
     * @param phraseP phraseP
     * @return answer
     */
    public static Rational convertP(String phraseP) {
        if (phraseP.charAt(0) == '(' && phraseP.charAt(phraseP.length() - 1) == ')')// be like (1/2+3/7)
        {
            phraseP = phraseP.substring(1, phraseP.length() - 1);
            return calculatorBase(phraseP);
        }

        //already shown the original phrase in CurationForConvert()
/*/
        for (int index = phraseP.length() - 1; index >= 0; index--)
            if (phraseP.charAt(index) == '(') {
                String subPhraseP = phraseP.substring(index + 1);

                if (!subPhraseP.contains(")")) {
                    throw new ArithmeticException("\n> Error 5 : the parenthesis doesn't end with ')' ");
                }

                for (int tempIndex = 0; tempIndex < subPhraseP.length(); tempIndex++) {
                    if (subPhraseP.charAt(tempIndex) == ')') {
                        subPhraseP = subPhraseP.substring(0, tempIndex);
                        break;
                    }
                }
//D is for Division
//T is for Times
//M if for Minus
//P is for Plus
                String justForShowSubPhraseP = subPhraseP.replace("M", "-");
                justForShowSubPhraseP = justForShowSubPhraseP.replace("P", "+");
                justForShowSubPhraseP = justForShowSubPhraseP.replace("T", "*");
                justForShowSubPhraseP = justForShowSubPhraseP.replace("D", ":");

                Rational tempRational = calculatorBase(subPhraseP);

                subPhraseP = "(" + subPhraseP + ")";

                int lengthLine = 1 + phraseP.indexOf(subPhraseP) + (subPhraseP.length()) / 2;
                lengthLine = (4 * lengthLine) / 5;
                System.out.println(" ".repeat("===>".length()) + "\u2BB6" + "\u21E6".repeat(lengthLine - 2) + "\u2BB0");
                System.out.println(" ".repeat("===>".length()) + "\u2BB1" + "\u21E8".repeat(4) + "  " + justForShowSubPhraseP + " = " + tempRational.toString());

                phraseP = phraseP.replace(subPhraseP, tempRational.toString());
//D is for Division
//T is for Times
//M if for Minus
//P is for Plus
                String justForShowPhraseP = phraseP.replace("M", "-");
                justForShowPhraseP = justForShowPhraseP.replace("P", "+");
                justForShowPhraseP = justForShowPhraseP.replace("T", "*");
                justForShowPhraseP = justForShowPhraseP.replace("D", ":");

                System.out.println("---> " + justForShowPhraseP);

                index = phraseP.length() - 1;
            }*/

        while (phraseP.contains(")") || phraseP.contains("(")) {
            int startPoint = phraseP.lastIndexOf("(");
            if (startPoint != -1) {
                String subPhraseP = phraseP.substring(startPoint + 1);

                if (!subPhraseP.contains(")"))
                    throw new ArithmeticException("\n> Error 5 : the phrase doesn't end with ')' " + " { " + subPhraseP + " }");

                int finishPoint = subPhraseP.indexOf(")");
                subPhraseP = subPhraseP.substring(0, finishPoint);
                Rational insideParenthesisRational = calculatorBase(subPhraseP);
//D is for Division
//T is for Times
//M if for Minus
//P is for Plus
                subPhraseP = "(" + subPhraseP + ")";


                String justForShowSubPhraseP = subPhraseP
                        .replace("M", "-")
                        .replace("P", "+")
                        .replace("T", "*")
                        .replace("D", ":");

                int lengthLine = 1 + phraseP.indexOf(subPhraseP) + (subPhraseP.length()) / 2;
                lengthLine = (4 * lengthLine) / 5;
                System.out.println("    " + "\u2BB6" + "\u21E6".repeat(lengthLine - 2-1) + "\u2BB0");
                System.out.println("    " + "\u2BB1" + "\u21E8".repeat(4) + "  " + justForShowSubPhraseP + " = " + insideParenthesisRational.toString());

                phraseP = phraseP.replace(subPhraseP, insideParenthesisRational.toString());

                String justForShowPhraseP = phraseP
                        .replace("M", "-")
                        .replace("P", "+")
                        .replace("T", "*")
                        .replace("D", ":");

                System.out.println("---> " + justForShowPhraseP);


            }
        }
        return convert(phraseP);
    }
}

