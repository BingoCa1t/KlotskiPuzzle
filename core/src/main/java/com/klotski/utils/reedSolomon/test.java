package com.klotski.utils.reedSolomon;

import java.io.IOException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        try {
            SampleEncoder.encoder(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            SampleDecoder.decoder(SampleEncoder.encoder(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
