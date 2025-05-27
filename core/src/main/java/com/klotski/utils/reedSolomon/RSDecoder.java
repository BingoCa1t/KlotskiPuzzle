/**
 * Command-line program that decodes a file using Reed-Solomon 4+2.
 *
 * Copyright 2015, Backblaze, Inc.  All rights reserved.
 */

package com.klotski.utils.reedSolomon;

import com.klotski.utils.logger.Logger;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Command-line program that decodes data using Reed-Solomon 4+2.
 *
 * The input should be a concatenated string of Base64-encoded shards (separated by '|').
 * The result is returned as the original decoded string.
 */
public class RSDecoder
{

    public static final int DATA_SHARDS = 4;
    public static final int PARITY_SHARDS = 2;
    public static final int TOTAL_SHARDS = 6;

    public static final int BYTES_IN_INT = 4;

    public static String decoder(String input) {
        // 检查输入是否包含分片分隔符
        if (!input.contains("*&*&")) {
            Logger.error( "输入格式错误：缺少分片分隔符 '*&*&':"+ input );
            return input;
        }

        byte [][] shards = new byte [TOTAL_SHARDS] [];
        boolean [] shardPresent = new boolean [TOTAL_SHARDS];
        int shardSize = 0;
        int shardCount = 0;

        // 处理 | 分隔的输入
        String[] shardStrings = input.split(Pattern.quote("*&*&"));

        // 验证分片数量
        if (shardStrings.length != TOTAL_SHARDS) {
            Logger.error("输入分片数量错误，期望 " + TOTAL_SHARDS + " 个分片，实际 " + shardStrings.length);
            return input;
        }

        // 解码Base64分片
        for (int i = 0; i < TOTAL_SHARDS; i++) {
            try {
                shards[i] = Base64.getDecoder().decode(shardStrings[i]);
                shardPresent[i] = true;
                shardCount++;
                if (shardSize == 0) {
                    shardSize = shards[i].length;
                }
            } catch (IllegalArgumentException e) {
                // 尝试修复可能缺少填充的Base64字符串
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
                } catch (IllegalArgumentException fixError) {
                    System.err.println("分片 " + i + " 解码失败：" + fixError.getMessage());
                    shardPresent[i] = false;
                }
            }
        }

        // 验证是否有足够的分片进行解码
        int presentCount = 0;
        for (boolean present : shardPresent) {
            if (present) presentCount++;
        }
        if (presentCount < DATA_SHARDS) {
             Logger.error("可用分片不足，至少需要 " + DATA_SHARDS + " 个分片，实际找到 " + presentCount);
             return null;
        }

        // 为缺失的分片创建空缓冲区
        for (int i = 0; i < TOTAL_SHARDS; i++) {
            if (!shardPresent[i]) {
                shards[i] = new byte [shardSize];
            }
        }

        // 使用Reed-Solomon算法恢复缺失的分片
        ReedSolomon reedSolomon = ReedSolomon.create(DATA_SHARDS, PARITY_SHARDS);
        reedSolomon.decodeMissing(shards, shardPresent, 0, shardSize);

        // 合并数据分片
        byte [] allBytes = new byte [shardSize * DATA_SHARDS];
        for (int i = 0; i < DATA_SHARDS; i++) {
            System.arraycopy(shards[i], 0, allBytes, shardSize * i, shardSize);
        }

        // 提取原始文件大小
        int fileSize = ByteBuffer.wrap(allBytes).getInt();

        // 验证文件大小是否合理
        if (fileSize < 0 || fileSize > allBytes.length - BYTES_IN_INT) {
            Logger.debug("无效的文件大小：" + fileSize);
        }

        // 返回解码后的原始数据
        return new String(allBytes, BYTES_IN_INT, fileSize);
    }
}
