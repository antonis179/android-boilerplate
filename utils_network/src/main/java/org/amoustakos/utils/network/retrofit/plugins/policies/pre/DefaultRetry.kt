package org.amoustakos.utils.network.retrofit.plugins.policies.pre

import org.amoustakos.utils.network.retrofit.plugins.interfaces.policies.pre.IRetryPolicy


/**
 * Default network retry policy (3 retries)
 */
class DefaultRetry: IRetryPolicy {

	override fun retries() = 3L

}