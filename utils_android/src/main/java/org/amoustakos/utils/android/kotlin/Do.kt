package org.amoustakos.utils.android.kotlin

object Do {

	/**
	 * Can be used to force exhaustive usage of 'when'.
	 *
	 * e.g. Do exhaustive when(sealedClass) {...}
	 */
	inline infix fun<reified T> exhaustive(any: T?) = any
}