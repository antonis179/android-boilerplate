package org.amoustakos.boilerplate.examples.io.local.models

import org.amoustakos.boilerplate.ui.activities.BaseActivity




data class ActivityListingModel (
                                    val activity:Class<out BaseActivity>?,
                                    val name:String? = null,
                                    val description:String? = null
                                )
