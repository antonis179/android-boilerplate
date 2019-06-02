package org.amoustakos.utils.network.retrofit.plugins.policies.appliers

import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PrePolicy
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PrePolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.pre.DefaultErrorLogPolicy
import org.amoustakos.utils.network.retrofit.plugins.policies.pre.DefaultJsonErrorReturnPolicy
import org.amoustakos.utils.network.retrofit.plugins.policies.pre.DefaultRetry

class DefaultPrePolicyApplier : PrePolicyApplier {

	private val prePolicies = listOf(
			DefaultRetry(),
			DefaultErrorLogPolicy(),
			DefaultJsonErrorReturnPolicy()
	)

	override fun prePolicies(): List<PrePolicy> = prePolicies

}