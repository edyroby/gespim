package it.solvingteam.gespim.storico

import it.solvingteam.gespim.docobj.DocumentObject

class StoricoController {

    def index = { }
	
	def apriAllegato = {
		
		def storicoInstance = Storico.get(params.id)
		if (!storicoInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(controller:'pratica',action: "search")
			return
		}
		
		DocumentObject docObj = DocumentObject.get(params.idDoc)
		if (!storicoInstance.allegato || storicoInstance.allegato !=  docObj) {
			flash.message = "Documento non trovato."
			redirect(action: "search")
			return
		}
		
		
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${docObj.docName}.pdf")
		
		response.outputStream << docObj.fileAllegatoByteArray
		
	}
}
