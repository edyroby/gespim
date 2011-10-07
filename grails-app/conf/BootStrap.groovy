import it.solvingteam.gespim.assegnazione.AreaCompetenza;
import it.solvingteam.gespim.security.*
import it.solvingteam.gespim.tipologiche.StatoPratica
import it.solvingteam.gespim.tipologiche.TipoBeneficiario
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipoRichiedente
import it.solvingteam.gespim.tipologiche.TipologiaLegale
import it.solvingteam.gespim.tipologiche.TipoOperazione;

import grails.util.GrailsUtil

class BootStrap {
	
	def fixtureLoader
	def grailsApplication

    def init = { servletContext ->
		String webdir = servletContext.getRealPath("/")
		
		if (GrailsUtil.environment != 'test') {
            prepareTipologiche(webdir)
			prepareUserRole()
            fixtureLoader.load('pratiche')
            fixtureLoader.load('richiedenti')
            fixtureLoader.load('appuntamenti_dev')
        }

    }
    def destroy = {
    }
	
	private void prepareUserRole(){
		def adminRole = new Ruolo(authority: Ruolo.ROLE_ADMIN).save(flush: true)
		def userRole = new Ruolo(authority: Ruolo.ROLE_USER).save(flush: true)
		def protocolloRole = new Ruolo(authority: Ruolo.ROLE_PROTOCOLLO).save(flush: true)
  
		def testUser = new Utente(username: 'admin', enabled: true,
			 password: '222',nome:'admin',cognome:'adminnn')
		def area2 = AreaCompetenza.findByCodice(AreaCompetenza.COD_AREA_LEGALE)
		if(area2){
			testUser.area = area2
			area2.addToUtenti(testUser)
		}
		testUser.save(flush: true)
		
		def classicUser = new Utente(username: 'user', enabled: true,
			 password: '222',nome:'user1',cognome:'user1111')
		classicUser.save(flush: true)
		
		def testUser2 = new Utente(username: 'legale', enabled: true,
			 password: '222',nome:'legale',cognome:'legaleee')
		if(area2){
			testUser2.area = area2
			area2.addToUtenti(testUser2)
		}
		testUser2.save(flush: true)
		area2.save(flush:true)
		
		def testUser3 = new Utente(username: 'flussi', enabled: true,
			 password: '222',nome:'flussi',cognome:'flussiiiii')
		def area3 = AreaCompetenza.findByCodice(AreaCompetenza.COD_AREA_FLUSSI)
		if(area3){
			testUser3.area = area3
			
		}
		testUser3.save(flush: true)
		area3.save(flush:true)
  
		UtenteRuolo.create testUser, adminRole, true
		UtenteRuolo.create testUser, protocolloRole, true
		UtenteRuolo.create classicUser, userRole, true
		UtenteRuolo.create testUser2, userRole, true
		UtenteRuolo.create testUser3, userRole, true
		
		RequestMap.findByUrlAndConfigAttribute('/**', 'IS_AUTHENTICATED_FULLY')?:new RequestMap(url:'/**', configAttribute:'IS_AUTHENTICATED_FULLY').save()
		RequestMap.findByUrlAndConfigAttribute('/login/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/login/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/logout/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/logout/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/images/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/images/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/css/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/css/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/js/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/js/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/plugins/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/plugins/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		
	}
	
	private void prepareTipologiche(webdir){
		
		//Stato Pratica
		new StatoPratica(codice:StatoPratica.COD_STATO_NON_PRESENTE,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_NON_PRESENTE]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_LAVORATA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_LAVORATA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_IN_ATTESA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_IN_ATTESA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_IN_EVIDENZA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_IN_EVIDENZA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_ATTESA_PARERE_QUESTURA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_ATTESA_PARERE_QUESTURA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_ATTESA_PARERE_DPL,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_ATTESA_PARERE_DPL]).save()
		
		//Tipologia legale
		new TipologiaLegale(codice:TipologiaLegale.COD_ISTANZA_DI_RIESAME,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ISTANZA_DI_RIESAME]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RIGETTO_MANCATA_INTEGRAZIONE,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RIGETTO_MANCATA_INTEGRAZIONE]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_PARERE_NEGATIVO_QUESTURA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_PARERE_NEGATIVO_QUESTURA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_INAMMISSIBILITA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_INAMMISSIBILITA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RINUNCIA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RINUNCIA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_DISCONOSCIMENTO,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_DISCONOSCIMENTO]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_ASSENZA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ASSENZA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_ACCESSO_ATTI_DIFFIDE,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ACCESSO_ATTI_DIFFIDE]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RICORSI,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RICORSI]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_DOCUMENTAZIONE_FALSA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_DOCUMENTAZIONE_FALSA]).save()
		
		//AREA COMPETENZA
		def f= new File("${webdir}WEB-INF/initData/area_competenza.csv")
		f.eachLine {
			def row = it.split(",")
			new AreaCompetenza(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		//TIPO PRATICHE
		grailsApplication.config.tipoPratiche?.each{
			new TipoPratica(codice:it.key,descrizione:it.value.nome).save()
		}
		//TIPO RICHIEDENTI
		f = new File("${webdir}WEB-INF/initData/tipo_richiedente.csv")
		f.eachLine {
			def row = it.split(",")
			new TipoRichiedente(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		//TIPO BENEFICIARIO
		f = new File("${webdir}WEB-INF/initData/tipo_beneficiario.csv")
		f.eachLine {
			def row = it.split(",")
			new TipoBeneficiario(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		//TIPO OPERAZIONI
		f = new File("${webdir}WEB-INF/initData/tipo_operazione.csv")
		f.eachLine {
			def row = it.split(",")
			new TipoOperazione(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		
		
	}
	
}
