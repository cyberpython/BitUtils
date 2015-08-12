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

import java.io.PrintStream;
import java.nio.ByteBuffer;

/**
 *
 * @author Georgios Migdos <cyberpython@gmail.com>
 */
public class BitUtils {
    
    /**
     * Converts a byte array of 1-8 bytes to a long.
     * 
     * @param b the byte array to use
     * 
     * @return a long having the same value as the byte array. The long  is 
     * left paddded with 0s.
     * 
     * @throws IllegalArgumentException if the byte array has a length of 0 or 
     *                                  greater than 8.
     */
    public static long toLong(byte[] b){
        if((b.length < 1) || (b.length>8)){
            throw new IllegalArgumentException("Array of size "+b.length+" cannot be converted to long.");
        }
        long result = 0L;
        for(int i=0; i<b.length; i++){
            result = ( (0xFFL & b[i]) << ((b.length-i-1)*8) ) | result;
        }
        return result;
    }
    
    /**
     * Converts a byte array of 1-4 bytes to an int.
     * 
     * @param b the byte array to use
     * 
     * @return an int having the same value as the byte array. The integer is 
     * left paddded with 0s.
     * 
     * @throws IllegalArgumentException if the byte array has a length of 0 or 
     *                                  greater than 4.
     */
    public static int toInt(byte[] b){
        if((b.length < 1) || (b.length>4)){
            throw new IllegalArgumentException("Array of size "+b.length+" cannot be converted to int.");
        }
        int result = 0;
        for(int i=0; i<b.length; i++){
            result = ( (0xFF & b[i]) << ((b.length-i-1)*8) ) | result;
        }
        return result;
    }
    
    /**
     * Converts a byte array of 1-2 bytes to a short.
     * 
     * @param b the byte array to use
     * 
     * @return a short having the same value as the byte array. The short is 
     * left paddded with 0s.
     * 
     * @throws IllegalArgumentException if the byte array has a length of 0 or 
     *                                  greater than 2.
     */
    public static short toShort(byte[] b){
        if((b.length < 1) || (b.length>2)){
            throw new IllegalArgumentException("Array of size "+b.length+" cannot be converted to short.");
        }
        short result = 0;
        for(int i=0; i<b.length; i++){
            result = (short)(( (0xFF & b[i]) << ((b.length-i-1)*8) ) | result);
        }
        return result;
    }
    
    /**
     * Converts a byte array of 1-8 bytes to a long.
     * 
     * @param b the byte array to use
     * 
     * @return a long having the same value as the byte array. If the most 
     *         significant bit of b[0] is 1 then the value is left padded with 
     *         1s.
     * 
     * @throws IllegalArgumentException if the byte array has a length of 0 or 
     *                                  greater than 8.
     */
    public static long toSignedLong(byte[] b){
        if((b.length < 1) || (b.length>8)){
            throw new IllegalArgumentException("Array of size "+b.length+" cannot be converted to long.");
        }
        int bLength = b.length*8;
        long result = 0L;
        for(int i=0; i<b.length; i++){
            result = ( (0xFFL & b[i]) << ((b.length-i-1)*8) ) | result;
        }
        if(isSet(b, bLength-1)){
            result = result | 0xFFFFFFFFFFFFFFFFL << bLength;
        }
        return result;
    }
    
    /**
     * Converts a byte array of 1-4 bytes to an int.
     * 
     * @param b the byte array to use
     * 
     * @return an int having the same value as the byte array. If the most 
     *         significant bit of b[0] is 1 then the value is left padded with 
     *         1s.
     * 
     * @throws IllegalArgumentException if the byte array has a length of 0 or 
     *                                  greater than 4.
     */
    public static int toSignedInt(byte[] b){
        if((b.length < 1) || (b.length>4)){
            throw new IllegalArgumentException("Array of size "+b.length+" cannot be converted to int.");
        }
        int bLength = b.length*8;
        int result = 0;
        for(int i=0; i<b.length; i++){
            result = ( (0xFF & b[i]) << ((b.length-i-1)*8) ) | result;
        }
        if(isSet(b, bLength-1)){
            result = result | 0xFFFFFFFF << bLength;
        }
        return result;
    }
    
    /**
     * Converts a byte array of 1-2 bytes to a short.
     * 
     * @param b the byte array to use
     * 
     * @return a short having the same value as the byte array. If the most 
     *         significant bit of b[0] is 1 then the value is left padded with 
     *         1s.
     * 
     * @throws IllegalArgumentException if the byte array has a length of 0 or 
     *                                  greater than 2.
     */
    public static short toSignedShort(byte[] b){
        if((b.length < 1) || (b.length>2)){
            throw new IllegalArgumentException("Array of size "+b.length+" cannot be converted to short.");
        }
        int bLength = b.length*8;
        short result = 0;
        for(int i=0; i<b.length; i++){
            result = (short)(( (0xFF & b[i]) << ((b.length-i-1)*8) ) | result);
        }
        if(isSet(b, bLength-1)){
            result = (short) (result | 0xFFFF << bLength);
        }
        return result;
    }
    
    /**
     * Converts a long value to a byte array.
     * The most significant bit is the leftmost bit of the first element of the
     * array.
     * 
     * @param value the value to convert to a byte array
     * 
     * @return a byte array of size 8
     */
    public static byte[] fromLong(long value){
        byte[] result =  { (byte)(0xFFL & (value>>>56)), (byte)(0xFFL & (value>>>48)), (byte)(0xFFL & (value>>>40)),
                           (byte)(0xFFL & (value>>>32)), (byte)(0xFFL & (value>>>24)), (byte)(0xFFL & (value>>>16)),
                           (byte)(0xFFL & (value>>>8)), (byte)(0xFFL & value)};
        return result;
    }
    
    /**
     * Converts an integer value to a byte array.
     * The most significant bit is the leftmost bit of the first element of the
     * array.
     * 
     * @param value the value to convert to a byte array
     * 
     * @return a byte array of size 4
     */
    public static byte[] fromInt(int value){
        byte[] result =  { (byte)(0xFF & (value>>>24)), (byte)(0xFF & (value>>>16)),
                           (byte)(0xFF & (value>>>8)), (byte)(0xFF & value)};
        return result;
    }
    
    /**
     * Converts a short value to a byte array.
     * The most significant bit is the leftmost bit of the first element of the
     * array.
     * 
     * @param value the value to convert to a byte array
     * 
     * @return a byte array of size 2
     */
    public static byte[] fromShort(short value){
        byte[] result =  { (byte)(0xFF & (value>>>8)), (byte)(0xFF & value)};
        return result;
    }

    /**
     * Converts a byte array to its 8-bit byte binary representation. Bytes are
     * left padded with 0s if necessary
     *
     * @param bytes the byte array
     *
     * @return A string representing this byte array's values
     */
    public static String bytesToBinaryString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%1$8s", Integer.toBinaryString(b & 0xFF)).replaceAll(" ", "0"));
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * Converts a byte array to its 2-digit hexadecimal representation.
     *
     * @param bytes the byte array
     *
     * @return A string representing this byte array's values
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%1$2s", Integer.toHexString(b & 0xFF)).replaceAll(" ", "0").toUpperCase());
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * Prints to the specified {@link PrintStream} the given byte array
     * contents. Each byte's value is interpreted as unsigned [0-255].
     *
     * @param bytes the byte array
     * @param out the stream to print to
     *
     */
    public static void printBytesInUnsignedDecimalForm(byte[] bytes, PrintStream out) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append((short) (0xFF & b));
            sb.append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        out.println(sb.toString());
    }

    /**
     * Prints to the specified {@link PrintStream} the given byte array contents
     * in their 8-bit per byte binary representation.
     *
     * @param bytes the byte array
     * @param out the stream to print to
     *
     */
    public static void printBytesInBinaryForm(byte[] bytes, PrintStream out) {
        out.print(bytesToBinaryString(bytes));
        out.println();
    }

    /**
     * Prints to the specified {@link PrintStream} the given byte array contents
     * in their 2-digit hexadecimal representation.
     *
     * @param bytes the byte array
     * @param out the stream to print to
     *
     */
    public static void printBytesInHexForm(byte[] bytes, PrintStream out) {
        out.print(bytesToHexString(bytes));
        out.println();
    }
    
    /**
     * Checks if the bit a the specified index of b is set to 1. 
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered to be the leftmost (index = b.length*8 -1) 
     * if the whole array is treated as a series of consecutive bits.
     * 
     * @param b the byte array
     * @param index the index of the bit to check
     *
     * @return true if the bit is set to 1, false otherwise
     *
     * @throws IndexOutOfBoundsException
     */
    public static boolean isSet(byte[] b, int index) throws IndexOutOfBoundsException {
        int maxIndex = b.length * 8 - 1;
        if ((index < 0) || (index > maxIndex)) {
            throw new IndexOutOfBoundsException("Invalid bit index: " + index + " for " + b.length + "-byte array.");
        }
        int byteOffset = b.length - (index / 8) - 1;
        int bitOffset = index % 8;
        byte mask = (byte) (0xFF & (1 << bitOffset));
        return (b[byteOffset] & mask)!=0;
    }

    /**
     * Sets the bit at the specified index of b to 1. The index is calculated
     * from right to left - i.e. the least significant bit is considered to be
     * the rightmost (index = 0), while the most significant bit is considered
     * to be the leftmost (index = b.length*8 -1) if the whole array is treated
     * as a series of consecutive bits.
     *
     * @param b the byte array
     * @param index the index of the bit to set to 1
     *
     * @return the byte array with the bit at the specified index set to 1
     *
     * @throws IndexOutOfBoundsException
     */
    public static byte[] setBit(byte[] b, int index) throws IndexOutOfBoundsException {
        int maxIndex = b.length * 8 - 1;
        if ((index < 0) || (index > maxIndex)) {
            throw new IndexOutOfBoundsException("Invalid bit index: " + index + " for " + b.length + "-byte array.");
        }
        int byteOffset = b.length - (index / 8) - 1;
        int bitOffset = index % 8;
        byte mask = (byte) (0xFF & (1 << bitOffset));
        b[byteOffset] = (byte) (0xFF & (b[byteOffset] | mask));
        return b;
    }

    /**
     * Sets the bit at the specified index of b to 0. The index is calculated
     * from right to left - i.e. the least significant bit is considered to be
     * the rightmost (index = 0), while the most significant bit is considered
     * to be the leftmost (index = b.length*8 -1) if the whole array is treated
     * as a series of consecutive bits.
     *
     * @param b the byte array
     * @param index the index of the bit to set to 0
     *
     * @return the byte array with the bit at the specified index set to 0
     *
     * @throws IndexOutOfBoundsException
     */
    public static byte[] unsetBit(byte[] b, int index) {
        int maxIndex = b.length * 8 - 1;
        if ((index < 0) || (index > maxIndex)) {
            throw new IndexOutOfBoundsException("Invalid bit index: " + index + " for " + b.length + "-byte array.");
        }
        int byteOffset = b.length - (index / 8) - 1;
        int bitOffset = index % 8;
        byte mask = (byte) ~(0xFF & (1 << bitOffset));
        b[byteOffset] = (byte) (0xFF & (b[byteOffset] & mask));
        return b;
    }
    
    /**
     * Extracts the given number of bits starting at the given bit index.
     * The result is returned as a byte array left-padded with 0s as needed.
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered  to be the leftmost (index = b.length*8 -1)
     * if the whole array is treated as a series of consecutive bits.
     * 
     * @param src the original byte array
     * @param index the first bit to extract
     * @param numOfBits the number of bits to extract
     * 
     * @return a byte array containing the extracted bits
     * 
     * @throws IllegalArgumentException if the starting bit index is lower than 
     *                                  0 or larger than the source byte arrays 
     *                                  bit count. Also, if the number of bits
     *                                  to extract is lower or equal to 0 or
     *                                  when added to the starting index exceeds
     *                                  the source byte array's bit count.
     * 
     */
    public static byte[] extract(byte[] src, int index, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        return extract(src, 0, src.length, index, numOfBits);
    }

    /**
     * Extracts the given number of bits starting at the given bit index.
     * The result is returned as a byte array left-padded with 0s as needed.
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered  to be the leftmost (index = b.length*8 -1)
     * if the whole sub-array is treated as a series of consecutive bits.
     * The portion of the array to be used is determined by the startFrom and 
     * endAt byte indexes.
     * 
     * @param src the original byte array
     * @param startFrom the array index the sub-array starts at
     * @param endBefore the array index the sub-array ends before
     * @param index the first bit to extract
     * @param numOfBits the number of bits to extract
     * 
     * @return a byte array containing the extracted bits
     * 
     * @throws IllegalArgumentException if the starting bit index is lower than 
     *                                  0 or larger than the source byte arrays 
     *                                  bit count. Also, if the number of bits
     *                                  to extract is lower or equal to 0 or
     *                                  when added to the starting index exceeds
     *                                  the source byte array's bit count.
     * 
     */
    public static byte[] extract(byte[] src, int startFrom, int endBefore, int index, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        
        int subArrayLength = Math.abs(endBefore - startFrom);

        if (numOfBits <= 0) {
            throw new IllegalArgumentException("The number of bits cannot be "
                    + "negative or zero. Given: " + numOfBits);
        }
        if ((index < 0) || (index > subArrayLength * 8 - 1)) {
            throw new IllegalArgumentException("The starting bit cannot be "
                    + "negative or larger than " + (subArrayLength * 8 - 1)
                    + ". Given: " + index);
        }
        if (index+numOfBits>subArrayLength*8) {
            throw new IllegalArgumentException("Invalid number of bits: "
                    + numOfBits);
        }

        byte[] result;

        int numOfBitsToShiftBy = index % 8;
        int numOfBitsToLeftShiftBy = 8 - numOfBitsToShiftBy;
        int numOfSpanningBits = numOfBits % 8;
        int numOfBytes = (numOfBits / 8) + (numOfBits%8>0?1:0);

        if (numOfBitsToShiftBy > 0) {
            result = new byte[numOfBytes];
            int offset = endBefore - (index / 8) - 1;
            int shiftMask = (0xFF >>> numOfBitsToLeftShiftBy);
            int resultIndex;
            int srcIndex;
            for (int i = 0; i < numOfBytes; i++) {
                resultIndex = numOfBytes - i - 1;
                srcIndex = offset - i;
                result[resultIndex] = (byte) (((0xFF & src[srcIndex]) >>> numOfBitsToShiftBy));
                if (srcIndex > 0) {
                    result[resultIndex] = (byte) (((shiftMask & src[--srcIndex]) << numOfBitsToLeftShiftBy) | result[resultIndex]);
                }
            }
            int msbMask = ~(0xFF << numOfSpanningBits);
            result[0] = (byte) (result[0] & msbMask);


        } else {
            int mask = (numOfSpanningBits == 0 ? 0xFF : (0xFF >>> (8 - numOfSpanningBits)));
            result = new byte[numOfBytes];
            System.arraycopy(src, endBefore - index / 8 - numOfBytes, result, 0, numOfBytes);
            result[0] = (byte) (result[0] & mask);
        }
        return result;
    }
    
    /**
     * Extracts the given number of bits starting at the given bit index.
     * The result is returned as a byte array left-padded with 0s as needed.
     * <b>If the most significant extracted bit is 1, the result is left-padded 
     * with 1s instead of 0s.</b>
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered  to be the leftmost (index = b.length*8 -1)
     * if the whole array is treated as a series of consecutive bits.
     * 
     * @param src the original byte array
     * @param index the first bit to extract
     * @param numOfBits the number of bits to extract
     * 
     * @return a byte array containing the extracted bits
     * 
     * @throws IllegalArgumentException if the starting bit index is lower than 
     *                                  0 or larger than the source byte arrays 
     *                                  bit count. Also, if the number of bits
     *                                  to extract is lower or equal to 0 or
     *                                  when added to the starting index exceeds
     *                                  the source byte array's bit count.
     * 
     */
    public static byte[] extractSigned(byte[] src, int index, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        return extractSigned(src, 0, src.length, index, numOfBits);
    }
    
    /**
     * Extracts the given number of bits starting at the given bit index.
     * The result is returned as a byte array left-padded with 0s as needed.
     * <b>If the most significant extracted bit is 1, the result is left-padded 
     * with 1s instead of 0s.</b>
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered  to be the leftmost (index = b.length*8 -1)
     * if the whole sub-array is treated as a series of consecutive bits.
     * The portion of the array to be used is determined by the startFrom and 
     * endAt byte indexes.
     * 
     * @param src the original byte array
     * @param startFrom the array index the sub-array starts at
     * @param endBefore the array index the sub-array ends before
     * @param index the first bit to extract
     * @param numOfBits the number of bits to extract
     * 
     * @return a byte array containing the extracted bits
     * 
     * @throws IllegalArgumentException if the starting bit index is lower than 
     *                                  0 or larger than the source byte arrays 
     *                                  bit count. Also, if the number of bits
     *                                  to extract is lower or equal to 0 or
     *                                  when added to the starting index exceeds
     *                                  the source byte array's bit count.
     * 
     */
    public static byte[] extractSigned(byte[] src, int startFrom, int endBefore, int index, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        byte[] b = extract(src, startFrom, endBefore, index, numOfBits);
        if(isSet(b, numOfBits-1)){
            b[0] = (byte) ((0xFF << 8 - numOfBits % 8) | b[0]);
        }
        return b;
    }
    
    
    /**
     * Extracts the given number of bits starting at the given bit index.
     * The result is returned as a long value. Up to 64 bits can be extracted.
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered  to be the leftmost (index = b.length*8 -1)
     * if the whole sub-array is treated as a series of consecutive bits.
     * The portion of the array to be used is determined by the offset and 
     * len values.
     * 
     * @param src the original byte array
     * @param offset the array index the sub-array starts at
     * @param len the number of bytes to use (starting at offset)
     * @param startBit the index of the first bit to extract
     * @param numOfBits the number of bits to extract
     * 
     * @return a long value representing the extracted bits
     * 
     * @throws IllegalArgumentException if the starting bit index is lower than 
     *                                  0 or larger than the source byte arrays 
     *                                  bit count. Also, if the number of bits
     *                                  to extract is lower or equal to 0 or
     *                                  when added to the starting index exceeds
     *                                  the source byte array's bit count.
     * 
     */
    public static long readUnsigned(byte[] src, int offset, int len, int startBit, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        
        if (numOfBits > 64) {
            throw new IllegalArgumentException("The number of bits cannot be "
                    + "greater than 64. Given: " + numOfBits);
        }
        
        byte[] bits = extract(src, offset, offset+len, startBit, numOfBits);

        long result = 0L;
        
        result = result + (0x00000000000000FFL & bits[bits.length-1]);
        
        if(bits.length > 1){
        	result = result + (0x000000000000FF00L &  (bits[bits.length-2] << 8));
        }
        
        if(bits.length > 2){
        	result = result + (0x0000000000FF0000L & (bits[bits.length-3] << 16));
        }
        
        if(bits.length > 3){
        	result = result + (0x00000000FF000000L & (bits[bits.length-4] << 24));
        }
        
        if(bits.length > 4){
        	result = result + (0x00000000FF000000L & (bits[bits.length-5] << 32));
        }
        
        if(bits.length > 5){
        	result = result + (0x00000000FF000000L & (bits[bits.length-6] << 40));
        }
        
        if(bits.length > 6){
        	result = result + (0x00000000FF000000L & (bits[bits.length-7] << 48));
        }
        
        if(bits.length > 7){
        	result = result + (0x00000000FF000000L & (bits[bits.length-8] << 56));
        }
        
        return result;
    }
    
    public static long readUnsigned(byte[] src, int startBit, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
    	return readUnsigned(src, 0, src.length, startBit, numOfBits);
    }
    
    public static long readUnsigned(ByteBuffer src, int len, int startBit, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
    	return readUnsigned(src.array(), src.position(), len, startBit, numOfBits);
    }
    
    /**
     * Checks if the bit a the specified index of b is set to 1. 
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered to be the leftmost (index = b.length*8 -1) 
     * if the whole sub-array is treated as a series of consecutive bits.
     * The sub-array is considered to start at the index given by offset and
     * extend to offset + len. 
     * 
     * @param b the byte array
     * @param offset the index of the sub-array start
     * @param len the length pf the sub-array
     * @param index the index of the bit to check
     *
     * @return true if the bit is set to 1, false otherwise
     *
     * @throws IndexOutOfBoundsException
     */
    public static boolean isSet(byte[] b, int offset, int len, int index) throws IndexOutOfBoundsException {
        if((offset < 0) || (offset+len) > b.length){
        	throw new IndexOutOfBoundsException("Invalid sub-array bounds - offset: " + offset + ", length: " + len + " for " + b.length + "-byte array.");
        }
        	
    	int maxIndex = len * 8 - 1;
        if ((index < 0) || (index > maxIndex)) {
            throw new IndexOutOfBoundsException("Invalid bit index: " + index + " for " + b.length + "-byte array.");
        }
        int byteOffset = offset + len - (index / 8) - 1;
        int bitOffset = index % 8;
        byte mask = (byte) (0xFF & (1 << bitOffset));
        return (b[byteOffset] & mask)!=0;
    }
    
    public static boolean isSet(ByteBuffer b, int len, int index) throws IndexOutOfBoundsException {
    	return isSet(b.array(), b.position(), len, index);
    }

    // TODO: setBit
    
    /**
     * Extracts the given number of bits starting at the given bit index.
     * The result is returned as a long value. Up to 64 bits can be extracted.
     * The index is calculated from right to left - i.e. the least significant 
     * bit is considered to be the rightmost (index = 0), while the most 
     * significant bit is considered  to be the leftmost (index = b.length*8 -1)
     * if the whole sub-array is treated as a series of consecutive bits.
     * The portion of the array to be used is determined by the offset and 
     * len values.
     * 
     * @param src the original byte array
     * @param offset the array index the sub-array starts at
     * @param len the number of bytes to use (starting at offset)
     * @param startBit the index of the first bit to extract
     * @param numOfBits the number of bits to extract
     * 
     * @return a long value representing the extracted bits
     * 
     * @throws IllegalArgumentException if the starting bit index is lower than 
     *                                  0 or larger than the source byte arrays 
     *                                  bit count. Also, if the number of bits
     *                                  to extract is lower or equal to 0 or
     *                                  when added to the starting index exceeds
     *                                  the source byte array's bit count.
     * 
     */
    public static long readSigned(byte[] src, int offset, int len, int startBit, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        
        if (numOfBits > 64) {
            throw new IllegalArgumentException("The number of bits cannot be "
                    + "greater than 64. Given: " + numOfBits);
        }
        
        byte[] bits = extract(src, offset, offset+len, startBit, numOfBits);
        
        long result = 0L;
        
        if(isSet(bits, numOfBits-1)){
        	bits[0] = (byte) ((0xFF << 8 - numOfBits % 8) | bits[0]);
            result = 0xFFFFFFFFFFFFFFFFL;
        }

        result = result & 0xFFFFFFFFFFFFFF00L | (long)bits[bits.length-1];        
        
        if(bits.length > 1){
        	result = result & 0xFFFFFFFFFFFF00FFL | (long)(bits[bits.length-2] << 8);
        }
        
        if(bits.length > 2){
        	result = result & 0xFFFFFFFFFF00FFFFL | (long)(bits[bits.length-3] << 16);
        }
        
        if(bits.length > 3){
        	result = result & 0xFFFFFFFF00FFFFFFL | (long)(bits[bits.length-4] << 24);
        }
        
        if(bits.length > 4){
        	result = result & 0xFFFFFF00FFFFFFFFL | ((long)(bits[bits.length-5]) << 32);
        }
        
        if(bits.length > 5){
        	result = result & 0xFFFF00FFFFFFFFFFL | ((long)(bits[bits.length-6]) << 40);
        }
        
        if(bits.length > 6){
        	result = result & 0xFF00FFFFFFFFFFFFL | ((long)(bits[bits.length-7]) << 48);
        }
        
        if(bits.length > 7){
        	result = result & 0x00FFFFFFFFFFFFFFL | ((long)(bits[bits.length-8]) << 56);
        }
        
        return result;
    }
    
    public static long readSigned(byte[] src, int startBit, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
    	return readSigned(src, 0, src.length, startBit, numOfBits);
    }
    
    public static long readSigned(ByteBuffer src, int len, int startBit, int numOfBits)
            throws IllegalArgumentException, IndexOutOfBoundsException {
    	return readSigned(src.array(), src.position(), len, startBit, numOfBits);
    }
    
		
//    	long l1 = 0x00006f5e4d3c2b1aL;
//    	byte[] b = {(byte)0x80, 0x71, 0x6f, 0x5e, 0x4d, 0x3c, 0x2b, 0x1a};
//    	long l2 = readSigned(b, 0, 48);
    
}
