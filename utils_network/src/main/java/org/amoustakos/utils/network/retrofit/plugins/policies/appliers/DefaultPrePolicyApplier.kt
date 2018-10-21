package org.amoustakos.utils.network.retrofit.plugins.policies.appliers

import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPrePolicy
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPrePolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.pre.DefaultErrorLogPolicy
import org.amoustakos.utils.network.retrofit.plugins.policies.pre.DefaultJsonErrorReturnPolicy
import org.amoustakos.utils.network.retrofit.plugins.policies.pre.DefaultRetry

class DefaultPrePolicyApplier : IPrePolicyApplier {

	private val prePolicies = listOf(
			DefaultRetry(),
			DefaultErrorLogPolicy(),
			DefaultJsonErrorReturnPolicy()
	)

	override fun prePolicies(): List<IPrePolicy> = prePolicies

}