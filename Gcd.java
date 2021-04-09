
public  class Gcd {
    private long gcd;

    public long getGcd() {
        return gcd;
    }

    public void setGcd(long gcd) {
        this.gcd = gcd;
    }

     /**
     * find and set the gcd of two number
     *
     * @param num1 num1
     * @param num2 num2
     */
    public Gcd(long num1, long num2) {
        num1 = Math.abs(num1);
        num2 = Math.abs(num2);
        if (num1 == 0 || num2 == 0) {
            setGcd(1);//doesn't defined but its better this way because less error occur :)
            return;
        }
        for (long i = 1; 1 <= Math.min(num1, num2); i++) {
            long cd = Math.min(num1, num2) / i;
            if (num1 % cd == 0 &&
                    num2 % cd == 0) {
                setGcd(cd);
                break;
            }
        }

    }


}
