class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/appuntamenti"(controller:"calendario",action:"calendario")
		"/"(controller:"pratica",action:"search")
		"500"(view:'/error')
	}
}
