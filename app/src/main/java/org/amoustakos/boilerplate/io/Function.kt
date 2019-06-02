package org.amoustakos.boilerplate.io


interface Function<Request, Response> {
	fun exec(input: Request): Response
}