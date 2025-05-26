/**
 * Command-line program encodes one file using Reed-Solomon 4+2.
 *
 * Copyright 2015, Backblaze, Inc.
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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Command-line program encodes one file using Reed-Solomon 4+2.
 *
 * The one argument should be a file name, say "foo.txt".  This program
 * will create six files in the same directory, breaking the input file
 * into four data shards, and two parity shards.  The output files are
 * called "foo.txt.0", "foo.txt.1", ..., and "foo.txt.5".  Numbers 4
 * and 5 are the parity shards.
 *
 * The data stored is the file size (four byte int), followed by the
 * contents of the file, and then padded to a multiple of four bytes
 * with zeros.  The padding is because all four data shards must be
 * the same size.
 */
public class SampleEncoder {

    public static final int DATA_SHARDS = 4;
    public static final int PARITY_SHARDS = 2;
    public static final int TOTAL_SHARDS = 6;

    public static final int BYTES_IN_INT = 4;

    public static String encoder (String input) throws IOException {
        byte[] inputData = null;
        StringBuilder result = new StringBuilder();

        // 支持从控制台输入数据

            System.out.println("请输入要编码的数据:");

            inputData = input.getBytes();

        // 支持从文件输入数据

        // 支持从其他类引入数据（示例）


        // 后续使用 inputData 进行编码
        final int fileSize = inputData.length;

        // Figure out how big each shard will be.  The total size stored
        // will be the file size (8 bytes) plus the file.
        final int storedSize = fileSize + BYTES_IN_INT;
        final int shardSize = (storedSize + DATA_SHARDS - 1) / DATA_SHARDS;

        // Create a buffer holding the file size, followed by
        // the contents of the file.
        final int bufferSize = shardSize * DATA_SHARDS;
        final byte [] allBytes = new byte[bufferSize];
        ByteBuffer.wrap(allBytes).putInt(fileSize);
        System.arraycopy(inputData, 0, allBytes, BYTES_IN_INT, fileSize);
        // 删除重复的文件读取代码
        // InputStream in = new FileInputStream(inputFile);
        // int bytesRead = in.read(allBytes, BYTES_IN_INT, fileSize);
        // if (bytesRead != fileSize) {
        //     throw new IOException("错误：读取文件到缓冲区失败，实际读取字节数与文件大小不符，期望读取 " + fileSize + " 字节，实际读取 " + bytesRead + " 字节");
        // }
        // in.close();

        // Make the buffers to hold the shards.
        byte [] [] shards = new byte [TOTAL_SHARDS] [shardSize];

        // Fill in the data shards
        for (int i = 0; i < DATA_SHARDS; i++) {
            System.arraycopy(allBytes, i * shardSize, shards[i], 0, shardSize);
        }

        // Use Reed-Solomon to calculate the parity.
        ReedSolomon reedSolomon = ReedSolomon.create(DATA_SHARDS, PARITY_SHARDS);
        reedSolomon.encodeParity(shards, 0, shardSize);

        // Write out the resulting files.
        File inputFile = null; // 初始化 inputFile 变量


            // 控制台输入场景，创建一个虚拟的输入文件
            inputFile = new File("console_input.txt");

            // 类输入场景，创建一个虚拟的输入文件



            // 新增：将编码结果以 Base64 字符串形式输出到控制台并记录到文件
            try (FileWriter writer = new FileWriter(new File(inputFile.getParentFile(), inputFile.getName() + ".base64"))) {
                System.out.println("编码结果（Base64）：");
                for (int j = 0; j < TOTAL_SHARDS; j++) {String base64Encoded;
                    base64Encoded = Base64.getEncoder().encodeToString(shards[j]);
                    System.out.println("Shard " + j + ": " + base64Encoded);
                    result.append(base64Encoded);
                    result.append("|");
                }

            }


        System.out.println(result);
    return result.toString();}
}
