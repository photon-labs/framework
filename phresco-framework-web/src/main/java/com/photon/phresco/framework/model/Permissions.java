package com.photon.phresco.framework.model;

public class Permissions {

	private boolean manageApplication = false;
    private boolean importApplication = false;
    private boolean manageRepo = false;
    private boolean updateRepo = false;
    private boolean managePdfReports = false;
    private boolean manageCodeValidation = false;
    private boolean manageConfiguration = false;
    private boolean manageBuilds = false;
    private boolean manageTests = false;
    private boolean manageCIJobs = false;
    private boolean executeCIJobs = false;
    private boolean manageMavenReports = false;
    
    public void setManageApplication(boolean manageApplication) {
		this.manageApplication = manageApplication;
	}

	public boolean canManageApplication() {
		return manageApplication;
	}

	public void setImportApplication(boolean importApplication) {
		this.importApplication = importApplication;
	}

	public boolean canImportApplication() {
		return importApplication;
	}

	public void setManageRepo(boolean manageRepo) {
		this.manageRepo = manageRepo;
	}

	public boolean canManageRepo() {
		return manageRepo;
	}

	public void setUpdateRepo(boolean updateRepo) {
		this.updateRepo = updateRepo;
	}

	public boolean canUpdateRepo() {
		return updateRepo;
	}

	public void setManagePdfReports(boolean managePdfReports) {
		this.managePdfReports = managePdfReports;
	}

	public boolean canManagePdfReports() {
		return managePdfReports;
	}

	public void setManageCodeValidation(boolean manageCodeValidation) {
		this.manageCodeValidation = manageCodeValidation;
	}

	public boolean canManageCodeValidation() {
		return manageCodeValidation;
	}

	public void setManageConfiguration(boolean manageConfiguration) {
		this.manageConfiguration = manageConfiguration;
	}

	public boolean canManageConfiguration() {
		return manageConfiguration;
	}

	public void setManageBuilds(boolean manageBuilds) {
		this.manageBuilds = manageBuilds;
	}

	public boolean canManageBuilds() {
		return manageBuilds;
	}

	public void setManageTests(boolean manageTests) {
		this.manageTests = manageTests;
	}

	public boolean canManageTests() {
		return manageTests;
	}

	public void setManageCIJobs(boolean manageCIJobs) {
		this.manageCIJobs = manageCIJobs;
	}

	public boolean canManageCIJobs() {
		return manageCIJobs;
	}

	public void setExecuteCIJobs(boolean executeCIJobs) {
		this.executeCIJobs = executeCIJobs;
	}

	public boolean canExecuteCIJobs() {
		return executeCIJobs;
	}

	public void setManageMavenReports(boolean manageMavenReports) {
		this.manageMavenReports = manageMavenReports;
	}

	public boolean canManageMavenReports() {
		return manageMavenReports;
	}

	@Override
	public String toString() {
		return "Permissions [manageApplication=" + manageApplication
				+ ", importApplication=" + importApplication + ", manageRepo="
				+ manageRepo + ", updateRepo=" + updateRepo
				+ ", managePdfReports=" + managePdfReports
				+ ", manageCodeValidation=" + manageCodeValidation
				+ ", manageConfiguration=" + manageConfiguration
				+ ", manageBuilds=" + manageBuilds + ", manageTests="
				+ manageTests + ", manageCIJobs=" + manageCIJobs
				+ ", executeCIJobs=" + executeCIJobs + ", manageMavenReports="
				+ manageMavenReports + "]";
	}
}