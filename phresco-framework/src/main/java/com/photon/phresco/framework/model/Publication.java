package com.photon.phresco.framework.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Publication {
	
	private String publication;
	private String publicationName;
	private String publicationKey;
	private String publicationPath;
	private String publicationUrl;
	private String imageUrl;
	private String imagePath;
	private String environment;
	private String publicationType;
	private List<Map<String, String>> parentPublications;
	
	public Publication() {
	}
	
	public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}
	public String getPublicationKey() {
		return publicationKey;
	}
	public void setPublicationKey(String publicationKey) {
		this.publicationKey = publicationKey;
	}
	public String getPublicationPath() {
		return publicationPath;
	}
	public void setPublicationPath(String publicationPath) {
		this.publicationPath = publicationPath;
	}
	public String getPublicationUrl() {
		return publicationUrl;
	}
	public void setPublicationUrl(String publicationUrl) {
		this.publicationUrl = publicationUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPublicationType() {
		return publicationType;
	}

	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}

	public List<Map<String, String>> getParentPublications() {
		return parentPublications;
	}

	public void setParentPublications(List<Map<String, String>> parentPublications) {
		this.parentPublications = parentPublications;
	}
	
	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	@Override
	public String toString() {
		return "Publication [publication=" + publication + ", publicationName="
				+ publicationName + ", publicationKey=" + publicationKey
				+ ", publicationPath=" + publicationPath + ", publicationUrl="
				+ publicationUrl + ", imageUrl=" + imageUrl + ", imagePath="
				+ imagePath + ", environment=" + environment
				+ ", publicationType=" + publicationType
				+ ", parentPublications=" + parentPublications + "]";
	}
	
}
