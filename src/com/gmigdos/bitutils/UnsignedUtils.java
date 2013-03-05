/*
 * The MIT License
 *
 * Copyright 2013 Georgios Migdos <cyberpython@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.gmigdos.bitutils;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class UnsignedUtils {

    private static final short MAX_BYTE = 0xFF;
    private static final int MAX_SHORT = 0xFFFF;
    private static final long MAX_INT = 0xFFFFFFFF;

    /**
     * Converts a byte to the corresponding unsigned byte value and returns the
     * result as a short
     *
     * @param b The signed byte value
     * @return The unsigned byte value as a short
     */
    public static short toUnsignedByte(byte b) {
        return (short) (b & MAX_BYTE);
    }

    /**
     * Converts a short to the corresponding unsigned short value and returns
     * the result as an int
     *
     * @param s The signed short value
     * @return The unsigned short value as an int
     */
    public static int toUnsignedShort(short s) {
        return (s & MAX_SHORT);
    }

    /**
     * Converts an int to the corresponding unsigned int value and returns the
     * result as a long
     *
     * @param i The signed int value
     * @return The unsigned int value as a long
     */
    public static long toUnsignedInt(int i) {
        return (long) (i & MAX_INT);
    }

    /**
     * Converts a short containing an unsigned byte value to the corresponding
     * signed byte value and returns the result as a byte
     *
     * @param unsignedByte The unsigned byte value. If the value is not in valid
     * range, an IllegalArgumentException is thrown.
     * @return The signed byte value as a byte
     */
    public static byte toByte(short unsignedByte) {
        if (unsignedByte < 0 || unsignedByte > MAX_BYTE) {
            throw new IllegalArgumentException(String.format("Unsigned byte values should be in the range 0..%1$d, got: %2$d", MAX_BYTE, unsignedByte));
        }
        return (byte) unsignedByte;
    }

    /**
     * Converts an int containing an unsigned short value to the corresponding
     * signed short value and returns the result as a short
     *
     * @param unsignedShort The unsigned short value. If the value is not in
     * valid range, an IllegalArgumentException is thrown.
     * @return The signed short value as a short
     */
    public static short toShort(int unsignedShort) {
        if (unsignedShort < 0 || unsignedShort > MAX_SHORT) {
            throw new IllegalArgumentException(String.format("Unsigned byte values should be in the range 0..%1$d, got: %2$d", MAX_SHORT, unsignedShort));
        }
        return (short) unsignedShort;
    }

    /**
     * Converts a long containing an unsigned int value to the corresponding
     * signed int value and returns the result as an int
     *
     * @param unsignedInt The unsigned int value. If the value is not in valid
     * range, an IllegalArgumentException is thrown.
     * @return The signed int value as an int
     */
    public static int toInt(long unsignedInt) {
        if (unsignedInt < 0 || unsignedInt > MAX_INT) {
            throw new IllegalArgumentException(String.format("Unsigned byte values should be in the range 0..%1$d, got: %2$d", MAX_INT, unsignedInt));
        }
        return (int) unsignedInt;
    }
}
