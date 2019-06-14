package org.amoustakos.utils.android.kotlin

object Do {

	/**
	 * Used to force exhaustive usage of 'when'.
	 *
	 * e.g. Do exhaustive when(sealedClass) {...}
	 */
	inline infix fun<reified T> exhaustive(any: T?) = any


	/**
	 * Used to try/catch a block of code.
	 * Will only catch errors extending [Exception]
	 */
	inline infix fun<reified T> safe(run: () -> T): T? {
		return try {
			run()
		} catch (e: Exception) {
			null
		}
	}

}