/* wolfCryptMessageDigestMd5Test.java
 *
 * Copyright (C) 2006-2021 wolfSSL Inc.
 *
 * This file is part of wolfSSL. (formerly known as CyaSSL)
 *
 * wolfSSL is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * wolfSSL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */

package com.wolfssl.provider.jce.test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Assume;
import org.junit.BeforeClass;

import java.security.Security;
import java.security.Provider;
import java.security.MessageDigest;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;

import com.wolfssl.provider.jce.WolfCryptProvider;
import com.wolfssl.wolfcrypt.FeatureDetect;

public class WolfCryptMessageDigestMd5Test {

    @BeforeClass
    public static void testProviderInstallationAtRuntime()
        throws NoSuchProviderException {

        /* install wolfJCE provider at runtime */
        Security.addProvider(new WolfCryptProvider());

        Provider p = Security.getProvider("wolfJCE");
        assertNotNull(p);

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5",
                                                          "wolfJCE");
        } catch (NoSuchAlgorithmException e) {
            /* if we also detect algo is compiled out, skip tests */
            if (FeatureDetect.Md5Enabled() == false) {
				System.out.println("JSSE MD5 Test skipped");
                Assume.assumeTrue(false);
            }
        }
    }

    @Test
    public void testMd5SingleUpdate()
        throws NoSuchProviderException, NoSuchAlgorithmException {

        DigestVector vectors[] = new DigestVector[] {
            /* test vectors {input, expected output} */
            new DigestVector(
                "abc".getBytes(),
                new byte[] {
                    (byte)0x90, (byte)0x01, (byte)0x50, (byte)0x98,
                    (byte)0x3c, (byte)0xd2, (byte)0x4f, (byte)0xb0,
                    (byte)0xd6, (byte)0x96, (byte)0x3f, (byte)0x7d,
                    (byte)0x28, (byte)0xe1, (byte)0x7f, (byte)0x72
                }
            ),
            new DigestVector(
                "message digest".getBytes(),
                new byte[] {
                    (byte)0xf9, (byte)0x6b, (byte)0x69, (byte)0x7d,
                    (byte)0x7c, (byte)0xb7, (byte)0x93, (byte)0x8d,
                    (byte)0x52, (byte)0x5a, (byte)0x2f, (byte)0x31,
                    (byte)0xaa, (byte)0xf1, (byte)0x61, (byte)0xd0
                }
            ),
            new DigestVector(
                "abcdefghijklmnopqrstuvwxyz".getBytes(),
                new byte[] {
                    (byte)0xc3, (byte)0xfc, (byte)0xd3, (byte)0xd7,
                    (byte)0x61, (byte)0x92, (byte)0xe4, (byte)0x00,
                    (byte)0x7d, (byte)0xfb, (byte)0x49, (byte)0x6c,
                    (byte)0xca, (byte)0x67, (byte)0xe1, (byte)0x3b
                }
            ),
            new DigestVector(
                new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkl" +
                           "mnopqrstuvwxyz0123456789").getBytes(),
                new byte[] {
                    (byte)0xd1, (byte)0x74, (byte)0xab, (byte)0x98,
                    (byte)0xd2, (byte)0x77, (byte)0xd9, (byte)0xf5,
                    (byte)0xa5, (byte)0x61, (byte)0x1c, (byte)0x2c,
                    (byte)0x9f, (byte)0x41, (byte)0x9d, (byte)0x9f
                }
            ),
            new DigestVector(
                new String("12345678901234567890123456789012345678" +
                           "90123456789012345678901234567890123456" +
                           "7890").getBytes(),
                new byte[] {
                    (byte)0x57, (byte)0xed, (byte)0xf4, (byte)0xa2,
                    (byte)0x2b, (byte)0xe3, (byte)0xc9, (byte)0x55,
                    (byte)0xac, (byte)0x49, (byte)0xda, (byte)0x2e,
                    (byte)0x21, (byte)0x07, (byte)0xb6, (byte)0x7a
                }
            )
        };

        byte[] output;

        MessageDigest md5 = MessageDigest.getInstance("MD5", "wolfJCE");

        for (int i = 0; i < vectors.length; i++) {
            md5.update(vectors[i].getInput());
            output = md5.digest();

            assertEquals(vectors[i].getOutput().length, output.length);
            assertArrayEquals(vectors[i].getOutput(), output);
        }
    }

    @Test
    public void testMd5SingleByteUpdate()
        throws NoSuchProviderException, NoSuchAlgorithmException {

        String input = "Hello World";
        byte[] inArray = input.getBytes();
        final byte expected[] = new byte[] {
            (byte)0xb1, (byte)0x0a, (byte)0x8d, (byte)0xb1,
            (byte)0x64, (byte)0xe0, (byte)0x75, (byte)0x41,
            (byte)0x05, (byte)0xb7, (byte)0xa9, (byte)0x9b,
            (byte)0xe7, (byte)0x2e, (byte)0x3f, (byte)0xe5
        };

        byte[] output;

        MessageDigest md5 = MessageDigest.getInstance("MD5", "wolfJCE");

        for (int i = 0; i < inArray.length; i++) {
            md5.update(inArray[i]);
        }
        output = md5.digest();
        assertEquals(expected.length, output.length);
        assertArrayEquals(expected, output);
    }

    @Test
    public void testMd5Reset()
        throws NoSuchProviderException, NoSuchAlgorithmException {

        String input = "Hello World";
        byte[] inArray = input.getBytes();
        final byte expected[] = new byte[] {
            (byte)0xb1, (byte)0x0a, (byte)0x8d, (byte)0xb1,
            (byte)0x64, (byte)0xe0, (byte)0x75, (byte)0x41,
            (byte)0x05, (byte)0xb7, (byte)0xa9, (byte)0x9b,
            (byte)0xe7, (byte)0x2e, (byte)0x3f, (byte)0xe5
        };

        byte[] output;

        MessageDigest md5 = MessageDigest.getInstance("MD5", "wolfJCE");

        for (int i = 0; i < inArray.length; i++) {
            md5.update(inArray[i]);
        }

        md5.reset();

        for (int i = 0; i < inArray.length; i++) {
            md5.update(inArray[i]);
        }
        output = md5.digest();
        assertEquals(expected.length, output.length);
        assertArrayEquals(expected, output);
    }

    @Test
    public void testMd5Interop()
        throws NoSuchProviderException, NoSuchAlgorithmException {

        String input = "Bozeman, MT";
        String input2 = "wolfSSL is an Open Source Internet security " +
                        "company, focused primarily on SSL/TLS and " +
                        "cryptography. Main products include the wolfSSL " +
                        "embedded SSL/TLS library, wolfCrypt cryptography " +
                        "library, wolfMQTT, and wolfSSH. Products are " +
                        "dual licensed under both GPLv2 and a commercial" +
                        "license.";

        byte[] wolfOutput;
        byte[] interopOutput;

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Provider provider = md5.getProvider();

        /* if we have another MessageDigest provider, test against it */
        if (!provider.equals("wolfJCE")) {

            /* short message */
            md5.update(input.getBytes());
            interopOutput = md5.digest();

            MessageDigest wolfMd5 =
                MessageDigest.getInstance("MD5", "wolfJCE");

            wolfMd5.update(input.getBytes());
            wolfOutput = wolfMd5.digest();

            assertArrayEquals(wolfOutput, interopOutput);

            /* long message */
            md5.update(input2.getBytes());
            interopOutput = md5.digest();

            wolfMd5.update(input2.getBytes());
            wolfOutput = wolfMd5.digest();

            assertArrayEquals(wolfOutput, interopOutput);
        }
    }
}

