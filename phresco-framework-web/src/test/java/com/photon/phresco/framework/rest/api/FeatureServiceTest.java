package com.photon.phresco.framework.rest.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.photon.phresco.commons.model.ArtifactInfo;
import com.photon.phresco.commons.model.RequiredOption;
import com.photon.phresco.commons.model.SelectedFeature;
import com.photon.phresco.exception.PhrescoException;

public class FeatureServiceTest extends RestBaseTest {
	
	FeatureService featureService = new FeatureService();
	
	@Test
	public void featuresList() {
		Response pass = featureService.list(customerId, "tech-phpdru7", "FEATURES", userId);
		Assert.assertEquals(200, pass.getStatus());
		Response fail = featureService.list(customerId, "tech-phpdru7", "JAVASCRIPT", "jhsrvf_jj");
		Assert.assertEquals(400, fail.getStatus());
		Response failonParamas = featureService.list(customerId, "tech-php4", "JAVASCRIPT", userId);
		Assert.assertEquals(400, failonParamas.getStatus());
	}
	
	@Test
	public void fetchDescription() {
		Response fail = featureService.getDescription("82b3cbdb-51dc-4013-9da9-baffb6d2e393", "jhsrvf_jj");
		Assert.assertEquals(400, fail.getStatus());
		Response pass = featureService.getDescription("82b3cbdb-51dc-4013-9da9-baffb6d2e393", userId);
		Assert.assertEquals(200, pass.getStatus());
//		Response failonParams = featureService.getDescription("82b3cbdb-51dc-4013-9da9-baffb6d2e3", userId);
//		Assert.assertEquals(400, failonParams.getStatus());
	}
	
	@Test
	public void fetchDependencyFeature() {
		ArtifactInfo artifactInfoPass = new ArtifactInfo();
		ArtifactInfo artifactInfoFail = new ArtifactInfo();
		List<RequiredOption> appliesTo = new ArrayList<RequiredOption>();
		RequiredOption requiredOption = new RequiredOption();
		requiredOption.setRequired(true);
		requiredOption.setTechId("tech-java-webservice");
		Date creationDate = new Date(System.currentTimeMillis());
		appliesTo.add(requiredOption);
		artifactInfoPass.setArtifactGroupId("43a419b2-98c6-4c9d-bdcf-b80f44950664");
		artifactInfoPass.setVersion("1.7.2");
		artifactInfoPass.setUsed(false);
		artifactInfoPass.setFileSize(10);
		artifactInfoPass.setAppliesTo(appliesTo);
		artifactInfoPass.setName("gson");
		artifactInfoPass.setId("43a419b2-98c6-4c9d-bdcf-b80f44950664");
		artifactInfoPass.setSystem(false);
		artifactInfoPass.setCreationDate(creationDate);
		List<String> dependencyIds = new ArrayList<String>();
		dependencyIds.add("a7b01507-380e-4ce2-a004-3eb277fce04e");
		artifactInfoPass.setDependencyIds(dependencyIds);
		artifactInfoFail.setArtifactGroupId("43a419b2-98c6-4c9d-bdcf-b80f44950664");
		artifactInfoFail.setVersion("1.7.2");
		artifactInfoFail.setUsed(false);
		artifactInfoFail.setFileSize(10);
		artifactInfoFail.setAppliesTo(appliesTo);
		artifactInfoFail.setName("gson");
		artifactInfoFail.setId("43a419b2-98c6-4c9d-bdcf-b80f44950664");
		artifactInfoFail.setSystem(false);
		artifactInfoFail.setCreationDate(creationDate);
		List<String> dependencyIdsNew = new ArrayList<String>();
		dependencyIdsNew.add("695af245-f8eb-4c6d-83b6-1402431a1bf5");
		artifactInfoFail.setDependencyIds(dependencyIdsNew);
		Response dependencyFeaturefailonLogin = featureService.getDependencyFeature("sample", "43a419b2-98c6-4c9d-bdcf-b80f44950664");
		Assert.assertEquals(400, dependencyFeaturefailonLogin.getStatus());
//		Response dependencyFeaturePass = featureService.getDependencyFeature(userId, artifactInfoPass);
//		Assert.assertEquals(200, dependencyFeaturePass.getStatus());
	}
	
	@Test
	public void updateApplicationFeatures() {
		ProjectService projectService = new ProjectService();
		List<SelectedFeature> selectedFeatures = new ArrayList<SelectedFeature>();
		SelectedFeature selectedFeature = new SelectedFeature();
		selectedFeature.setName("Spring-jms");
		selectedFeature.setDispName("Spring-jms");
		selectedFeature.setVersionID("a69c6875-0bb0-462c-86d5-e361d02157cc");
		selectedFeature.setType("FEATURE");
		selectedFeature.setModuleId("0e3cbd2a-4fe3-415f-8bc3-419c56aa6ca3");
		selectedFeature.setArtifactGroupId("0e3cbd2a-4fe3-415f-8bc3-419c56aa6ca3");
		selectedFeature.setPackaging("jar");
		selectedFeatures.add(selectedFeature);
		Response updateApplicationFeatures = projectService.updateApplicationFeatures(selectedFeatures, appDirName, userId, customerId);
		Assert.assertEquals(200, updateApplicationFeatures.getStatus());
		Response updateApplicationFeatures2 = projectService.updateApplicationFeatures(selectedFeatures, appDirName, "sample", customerId);
		Assert.assertEquals(400, updateApplicationFeatures2.getStatus());
	}
	
	@Test
	public void fetchSelectedFeatures() throws PhrescoException {
		Response selectedFeaturesLoginFail = featureService.selectedFeatures("sample", appDirName);
		Assert.assertEquals(400, selectedFeaturesLoginFail.getStatus());
		Response selectedFeatures = featureService.selectedFeatures(userId, appDirName);
		Assert.assertEquals(200, selectedFeatures.getStatus());
	}
}
