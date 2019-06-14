package org.amoustakos.utils.network.retrofit.plugins.policies.appliers

import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PostPolicy
import org.amoustakos.utils.network.retrofit.plugins.interfaces.base.PostPolicyApplier
import org.amoustakos.utils.network.retrofit.plugins.policies.post.DefaultMapErrorReturnPolicy

class DefaultPostPolicyApplier : PostPolicyApplier {

	private val postPolicies = listOf( DefaultMapErrorReturnPolicy() )

	override fun postPolicies(): List<PostPolicy> = postPolicies

}