package org.amoustakos.utils.network.retrofit.plugins.policies.appliers

import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPostPolicy
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.IPostPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.post.DefaultMapErrorReturnPolicy

class DefaultPostPolicyApplier : IPostPolicyApplier {

	private val postPolicies = listOf( DefaultMapErrorReturnPolicy() )

	override fun postPolicies(): List<IPostPolicy> = postPolicies

}