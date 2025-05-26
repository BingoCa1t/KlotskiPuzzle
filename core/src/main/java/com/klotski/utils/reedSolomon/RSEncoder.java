/**
 * Command-line program encodes one file using Reed-Solomon 4+2.
 *
 * Copyright 2015, Backblaze, Inc.
 */

package com.klotski.utils.reedSolomon;

import java.nio.ByteBuffer;
import java.util.Base64;

/**
 * Command-line program encodes one file using Reed-Solomon 4+2.
 *
 * The input data is encoded into four data shards and two parity shards.
 * The result is returned as a concatenated string of Base64-encoded shards.
 */
public class RSEncoder
{

    public static final int DATA_SHARDS = 4;
    public static final int PARITY_SHARDS = 2;
    public static final int TOTAL_SHARDS = 6;

    public static final int BYTES_IN_INT = 4;

    public static String encoder(String input) {
        byte[] inputData = input.getBytes();
        StringBuilder result = new StringBuilder();

        final int fileSize = inputData.length;
        final int storedSize = fileSize + BYTES_IN_INT;
        final int shardSize = (storedSize + DATA_SHARDS - 1) / DATA_SHARDS;

        final int bufferSize = shardSize * DATA_SHARDS;
        final byte [] allBytes = new byte[bufferSize];

        ByteBuffer.wrap(allBytes).putInt(fileSize);
        System.arraycopy(inputData, 0, allBytes, BYTES_IN_INT, fileSize);

        byte [] [] shards = new byte [TOTAL_SHARDS] [shardSize];

        for (int i = 0; i < DATA_SHARDS; i++) {
            System.arraycopy(allBytes, i * shardSize, shards[i], 0, shardSize);
        }

        ReedSolomon reedSolomon = ReedSolomon.create(DATA_SHARDS, PARITY_SHARDS);
        reedSolomon.encodeParity(shards, 0, shardSize);

        for (int j = 0; j < TOTAL_SHARDS; j++) {
            String base64Encoded = Base64.getEncoder().encodeToString(shards[j]);
            result.append(base64Encoded);
            result.append("*&*&");
        }

        return result.toString();
    }
}
