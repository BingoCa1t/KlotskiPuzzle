/**
 * Command-line program that decodes a file using Reed-Solomon 4+2.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package com.klotski.utils.reedSolomon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Scanner;

/**
 * Command-line program that decodes a file using Reed-Solomon 4+2.
 *
 * The file name given should be the name of the file to decode, say
 * "foo.txt".  This program will expected to find "foo.txt.0" through
 * "foo.txt.5", with at most two missing.  It will then write
 * "foo.txt.decoded".
 */
public class SampleDecoder {

    public static final int DATA_SHARDS = 4;
    public static final int PARITY_SHARDS = 2;
    public static final int TOTAL_SHARDS = 6;

    public static final int BYTES_IN_INT = 4;



    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String decoder(String input) throws IOException {
    StringBuilder result = new StringBuilder();


        File originalFile = new File(input);
        // 如果输入不是有效文件路径，创建一个虚拟文件
        if (!originalFile.exists()) {
            originalFile = new File("console_input_decoded.txt");
        }
        byte [][] shards = new byte [TOTAL_SHARDS] [];
        boolean [] shardPresent = new boolean [TOTAL_SHARDS];
        int shardSize = 0;
        int shardCount = 0;

        if (originalFile.exists()) {
            // 原文件读取逻辑
            // Read in any of the shards that are present.
            // (There should be checking here to make sure the input
            // shards are the same size, but there isn't.)
            for (int i = 0; i < TOTAL_SHARDS; i++) {
                File shardFile = new File(
                        originalFile.getParentFile(),
                        originalFile.getName() + "." + i);
                if (shardFile.exists()) {
                    shardSize = (int) shardFile.length();
                    shards[i] = new byte [shardSize];
                    shardPresent[i] = true;
                    shardCount += 1;
                    InputStream in = new FileInputStream(shardFile);
                    in.read(shards[i], 0, shardSize);
                    in.close();
                    System.out.println("Read " + shardFile);
                }
            }
        } else {
            // 处理 | 分隔的输入
            String[] shardStrings = input.split("\\|");


            for (int i = 0; i < TOTAL_SHARDS; i++) {
                try {
                    shards[i] = Base64.getDecoder().decode(shardStrings[i]);
                    shardPresent[i] = true;
                    shardCount++;
                    if (shardSize == 0) {
                        shardSize = shards[i].length;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("错误：分片 " + i + " 不是有效的 Base64 编码，尝试修复...");
                    // 尝试简单修复，如添加缺失的填充字符
                    String fixedShard = shardStrings[i];
                    while (fixedShard.length() % 4 != 0) {
                        fixedShard += "=";
                    }
                    try {
                        shards[i] = Base64.getDecoder().decode(fixedShard);
                        shardPresent[i] = true;
                        shardCount++;
                        if (shardSize == 0) {
                            shardSize = shards[i].length;
                        }
                        System.out.println("分片 " + i + " 修复成功。");
                    } catch (IllegalArgumentException fixError) {
                        System.out.println("错误：分片 " + i + " 修复失败。");
                        shardPresent[i] = false;
                    }
                }
            }
        }

        // We need at least DATA_SHARDS to be able to reconstruct the file.


        // Make empty buffers for the missing shards.
        for (int i = 0; i < TOTAL_SHARDS; i++) {
            if (!shardPresent[i]) {
                shards[i] = new byte [shardSize];
            }
        }

        // Use Reed-Solomon to fill in the missing shards
        ReedSolomon reedSolomon = ReedSolomon.create(DATA_SHARDS, PARITY_SHARDS);
        reedSolomon.decodeMissing(shards, shardPresent, 0, shardSize);

        // Combine the data shards into one buffer for convenience.
        // (This is not efficient, but it is convenient.)
        byte [] allBytes = new byte [shardSize * DATA_SHARDS];
        for (int i = 0; i < DATA_SHARDS; i++) {
            System.arraycopy(shards[i], 0, allBytes, shardSize * i, shardSize);
        }

        // Extract the file length
        int fileSize = ByteBuffer.wrap(allBytes).getInt();

        // Write the decoded file
        File decodedFile = new File(originalFile.getParentFile(), originalFile.getName() + ".decoded");
        OutputStream out = new FileOutputStream(decodedFile);
        out.write(allBytes, BYTES_IN_INT, fileSize);
        out.close();
        System.out.println("Wrote " + decodedFile);

        // 输出解密后的字符串
        String decodedString = new String(allBytes, BYTES_IN_INT, fileSize);
        System.out.println("解密后的字符串: " + decodedString);
    return decodedString;
    }
}
