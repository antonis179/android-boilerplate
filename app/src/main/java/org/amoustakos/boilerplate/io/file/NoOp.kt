package org.amoustakos.boilerplate.io.file


class NoOpFactory : Factory {

	fun make() = NoOpFile()

}



class NoOpFile : File<Unit, Unit, Unit, Unit, Unit>(
		NoOpOpenable(),
		NoOpCacheable(),
		NoOpStoreable(),
		NoOpUploadable(),
		NoOpDownloadable()
)




class NoOpOpenable : Openable<Unit> {
	override fun open() {}
}

class NoOpCacheable : Cacheable<Unit> {
	override fun cache() {}
}

class NoOpStoreable : Storeable<Unit> {
	override fun store() {}
}

class NoOpUploadable : Uploadable<Unit> {
	override fun upload() {}
}

class NoOpDownloadable : Downloadable<Unit> {
	override fun download() {}
}
