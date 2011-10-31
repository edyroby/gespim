package it.solvingteam.gespim.workflow

import it.solvingteam.gespim.pratica.Pratica;

import java.util.Date;

import org.grails.activiti.ApprovalStatus;

class IterAssegnaziPresaInCaricoPratica {
	
	ApprovalStatus approvalStatus = ApprovalStatus.PENDING
	String area
	String note
	Boolean resendRequest
	Date dateCreated
	Date lastUpdated
	Pratica pratica
	String taskId
	String username

    static constraints = {
		area nullable:true
		pratica nullable:true
		note nullable:true, size:5..255
		approvalStatus nullable:true
		//approvalRemark nullable:true
		resendRequest nullable:true
		dateCreated blank:false
		lastUpdated nullable:true
		taskId nullable:true
		username nullable:true
    }
}
