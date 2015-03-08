package com.rest.server.util;

/*
 * University of Illinois/NCSA
 * Open Source License
 *
 * Copyright (c) 2008, NCSA.  All rights reserved.
 *
 * Developed by:
 * The Automated Learning Group
 * University of Illinois at Urbana-Champaign
 * http://www.seasr.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal with the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimers.
 *
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimers in
 * the documentation and/or other materials provided with the distribution.
 *
 * Neither the names of The Automated Learning Group, University of
 * Illinois at Urbana-Champaign, nor the names of its contributors may
 * be used to endorse or promote products derived from this Software
 * without specific prior written permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
 */


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

/**
 * Provides utility function for UUIDs
 *
 * @author Boris Capitanu
 */

public abstract class UUIDUtil {

    /**
     * Converts a UUID to a byte array
     *
     * @param uuid The UUID
     * @return The byte array representing the UUID provided, or null if it can't be computed
     */
    public static byte[] toByteArray(UUID uuid) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);

        try {
            outputStream.writeLong(uuid.getMostSignificantBits());
            outputStream.writeLong(uuid.getLeastSignificantBits());
        }
        catch (IOException e) {
            // Should not happen
            return null;
        }

        return baos.toByteArray();
    }

    /**
     * Reconstructs a UUID from a byte array
     *
     * @param bytes The byte array (will be automatically padded left if size < 16)
     * @return The UUID
     */
    public static UUID fromByteArray(byte[] bytes) {
        int usableBytes = Math.min(bytes.length, 16);

        // Need exactly 16 bytes - pad the input if not enough bytes are provided
        // Use provided bytes in the least significant position; if more than 16 bytes are given,
        // then use the first 16 bytes from the array;
        byte[] barr = new byte[16];
        for (int i = 15, j = usableBytes-1; j >= 0; i--, j--)
            barr[i] = bytes[j];

        ByteArrayInputStream bais = new ByteArrayInputStream(barr);
        DataInputStream inputStream = new DataInputStream(bais);

        try {
            long msb = inputStream.readLong();
            long lsb = inputStream.readLong();

            return new UUID(msb, lsb);
        }
        catch (IOException e) {
            // Should never happen
            return null;
        }
    }

    /**
     * Converts a UUID to a BigInteger
     *
     * @param uuid The UUID
     * @return The BigInteger
     */
    public static BigInteger toBigInteger(UUID uuid) {
        return new BigInteger(toByteArray(uuid));
    }

    /**
     * Reconstructs a UUID from a BigInteger
     *
     * @param bigInteger The big integer
     * @return The UUID
     */
    public static UUID fromBigInteger(BigInteger bigInteger) {
        return fromByteArray(bigInteger.toByteArray());
    }
}