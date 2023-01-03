package br.com.petinder.backend.services;

public class CpfHandlerService {

    private int randomize(int n) {
        return (int) (Math.random() * n);
    }

    private int mod(int dividend, int divider) {
        return (int) Math.round(dividend - (Math.floor(dividend / divider) * divider));
    }

    public String cpf(boolean withDots) {
        int n = 9;
        int n1 = randomize(n);
        int n2 = randomize(n);
        int n3 = randomize(n);
        int n4 = randomize(n);
        int n5 = randomize(n);
        int n6 = randomize(n);
        int n7 = randomize(n);
        int n8 = randomize(n);
        int n9 = randomize(n);
        int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

        d1 = 11 - (mod(d1, 11));

        if (d1 >= 10)
            d1 = 0;

        int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

        d2 = 11 - (mod(d2, 11));

        String result = null;

        if (d2 >= 10)
            d2 = 0;
        result = "";

        if (withDots)
            result = "" + n1 + n2 + n3 + '.' + n4 + n5 + n6 + '.' + n7 + n8 + n9 + '-' + d1 + d2;
        else
            result = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;

        return result;
    }
}