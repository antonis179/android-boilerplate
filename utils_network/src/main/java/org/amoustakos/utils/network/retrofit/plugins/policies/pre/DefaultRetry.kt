package org.amoustakos.utils.network.retrofit.plugins.policies.pre

import org.amoustakos.utils.network.retrofit.plugins.interfaces.policies.pre.RetryPolicy


/**
 * Default network retry policy (3 retries)
 */
class DefaultRetry: RetryPolicy {

	override fun retries() = 3L

}