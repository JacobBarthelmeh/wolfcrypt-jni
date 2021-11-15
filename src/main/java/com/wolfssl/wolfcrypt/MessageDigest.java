/* MessageDigest.java
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

package com.wolfssl.wolfcrypt;

import java.nio.ByteBuffer;

import javax.crypto.ShortBufferException;

/**
 * Common API for Message Digests.
 */
public abstract class MessageDigest extends NativeStruct {

	private WolfCryptState state = WolfCryptState.UNINITIALIZED;

	protected abstract void native_init();

	protected abstract void native_update(ByteBuffer data, int offset,
			int length);

	protected abstract void native_update(byte[] data, int offset, int length);

	protected abstract void native_final(ByteBuffer hash, int offset);

	protected abstract void native_final(byte[] hash);

	public abstract int digestSize();

	public void init() {
		native_init();
		state = WolfCryptState.READY;
	}

	public void update(ByteBuffer data, int length) {
		if (state == WolfCryptState.READY) {
			length = Math.min(length, data.remaining());

			native_update(data, data.position(), length);
			data.position(data.position() + length);
		} else {
			throw new IllegalStateException(
					"Object must be initialized before use");
		}
	}

	public void update(ByteBuffer data) {
		update(data, data.remaining());
	}

	public void update(byte[] data, int offset, int len) {
		if (state == WolfCryptState.READY) {
			if (offset >= data.length || offset < 0 || len < 0)
				return;

			if (data.length - offset < len)
				len = data.length - offset;

			native_update(data, offset, len);
		} else {
			throw new IllegalStateException(
					"Object must be initialized before use");
		}
	}

	public void update(byte[] data, int len) {
		update(data, 0, len);
	}

	public void update(byte[] data) {
		update(data, 0, data.length);
	}

	public void digest(ByteBuffer hash) throws ShortBufferException {
		if (state == WolfCryptState.READY) {
			if (hash.remaining() < digestSize())
				throw new ShortBufferException(
						"Input buffer is too small for digest size");

			native_final(hash, hash.position());
			hash.position(hash.position() + digestSize());
		} else {
			throw new IllegalStateException(
					"Object must be initialized before use");
		}
	}

	public void digest(byte[] hash) throws ShortBufferException {
		if (state == WolfCryptState.READY) {
			if (hash.length < digestSize())
				throw new ShortBufferException(
						"Input buffer is too small for digest size");

			native_final(hash);
		} else {
			throw new IllegalStateException(
					"Object must be initialized before use");
		}
	}

	public byte[] digest() {
		if (state == WolfCryptState.READY) {
			byte[] hash = new byte[digestSize()];

			native_final(hash);

			return hash;
		} else {
			throw new IllegalStateException(
					"Object must be initialized before use");
		}
	}

    @Override
    public void releaseNativeStruct() {

        /* reset state first, then free */
        state = WolfCryptState.UNINITIALIZED;
        setNativeStruct(NULL);
    }
}
