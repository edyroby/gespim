package it.solvingteam.gespim.docobj

import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

/*
 * questa classe rappresenta l'associazione esistente tra il documento inteso 
 * come file che viene utilizzato
 * dagli oggetti del nostro dominio per immagazzinare documenti nel software documentale
 */
class DocumentObject {
	
	//id assegnato al file nel software documentale
	Integer idDocumentale
	String docName
	Date dataCreazione
	Date dataCancellazione
	byte[] fileAllegatoByteArray
	Blob fileAllegatoBlob
	
	static transients = ['fileAllegatoByteArray']
	
	static mapping = {
		//id	generator:'sequence',params:[sequence:'documentobject_seq']
		//fileAllegatoBlob type:'blob'
	}
	
	
	static constraints = {
		
		idDocumentale(nullable:true/*,unique:true*/)
		docName (nullable:true)
		dataCreazione (nullable:true)
		dataCancellazione (nullable:true)
		fileAllegatoByteArray(nullable:true,maxSize:1073741824)
		
	}
	
	static namedQueries = {
		validDocumentObjects {
			isNull("dataCancellazione")
		}
	}
	def getFileAllegatoByteArray() {
		if (fileAllegatoBlob == null)
		return null;
		return toByteArray(getFileAllegatoBlob());
	}
	
	def setFileAllegatoByteArray(fileAllegatoByteArray) {
		setFileAllegatoBlob(Hibernate.createBlob(fileAllegatoByteArray));
	}
	
	private byte[] toByteArray(Blob fromBlob) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			return toByteArrayImpl(fromBlob, baos);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ex) {
				}
			}
		}
	}
	
	private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos) {
		byte[] buf = new byte[4000];
		InputStream is = fromBlob.getBinaryStream();
		try {
			while (true) {
				int dataSize = is.read(buf);
				
				if (dataSize == -1)
				break;
				baos.write(buf, 0, dataSize);
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		return baos.toByteArray();
	}
	
	String extractEstensioneFromDocName(){
		if(docName && docName.indexOf('.') > 0){
			return docName.substring(docName.indexOf('.')) - '.'
		}
		return null
	}
	

	
}

