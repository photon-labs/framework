package com.photon.phresco.framework.impl;


import java.net.MalformedURLException;
import java.util.List;

import com.microsoft.tfs.client.clc.AcceptedOptionSet;
import com.microsoft.tfs.client.clc.Messages;
import com.microsoft.tfs.client.clc.commands.Command;
import com.microsoft.tfs.client.clc.exceptions.ArgumentException;
import com.microsoft.tfs.client.clc.exceptions.CLCException;
import com.microsoft.tfs.client.clc.exceptions.CannotFindWorkspaceException;
import com.microsoft.tfs.client.clc.exceptions.InvalidOptionException;
import com.microsoft.tfs.client.clc.exceptions.InvalidOptionValueException;
import com.microsoft.tfs.client.clc.exceptions.LicenseException;
import com.microsoft.tfs.client.clc.options.Option;
import com.microsoft.tfs.client.clc.options.shared.OptionFormat;
import com.microsoft.tfs.client.clc.options.shared.OptionUser;
import com.microsoft.tfs.client.clc.vc.options.OptionNoDetect;
import com.microsoft.tfs.client.clc.vc.options.OptionRecursive;
import com.microsoft.tfs.client.clc.vc.options.OptionShelveset;
import com.microsoft.tfs.client.clc.vc.options.OptionWorkspace;
import com.microsoft.tfs.console.display.Display;
import com.microsoft.tfs.core.TFSTeamProjectCollection;
import com.microsoft.tfs.core.clients.versioncontrol.VersionControlClient;
import com.microsoft.tfs.core.clients.versioncontrol.path.LocalPath;
import com.microsoft.tfs.core.clients.versioncontrol.path.ServerPath;
import com.microsoft.tfs.core.clients.versioncontrol.soapextensions.PendingSet;
import com.microsoft.tfs.core.clients.versioncontrol.soapextensions.RecursionType;
import com.microsoft.tfs.core.clients.versioncontrol.specs.ItemSpec;
import com.microsoft.tfs.core.clients.versioncontrol.specs.WorkspaceSpec;
import com.microsoft.tfs.core.clients.versioncontrol.specs.WorkspaceSpecParseException;
import com.microsoft.tfs.core.clients.versioncontrol.workspacecache.WorkspaceInfo;

public final class CommandStatus extends Command
{
	private PendingSet[] pendingSets;

	public void run() throws ArgumentException, MalformedURLException, CLCException, LicenseException {
		String workspaceName = null;
		String workspaceOwner = null;
		String shelvesetSpec = null;
		String format = "brief";
		boolean recursive = false;
		boolean isWorkspaceExplicit = false;
		boolean isUserExplicit = false;
		boolean noDetect = false;
		try {
			WorkspaceInfo cachedWorkspace = determineCachedWorkspace(getFreeArguments());
			workspaceName = cachedWorkspace.getName();
			workspaceOwner = cachedWorkspace.getOwnerName();
		} catch (CannotFindWorkspaceException e) {
		}
		Option o = null;
		if ((o = findOptionType(OptionWorkspace.class)) != null) {
			try {
				WorkspaceSpec spec = WorkspaceSpec.parse(((OptionWorkspace)o).getValue(), ".", true);
				workspaceName = spec.getName();
				workspaceOwner = spec.getOwner();
			} catch (WorkspaceSpecParseException e) {
				throw new InvalidOptionValueException(e.getMessage());
			}
			isWorkspaceExplicit = true;
		}

		if ((o = findOptionType(OptionUser.class)) != null) {
			workspaceOwner = ((OptionUser)o).getValue();
			isUserExplicit = true;
		}

		if ((o = findOptionType(OptionShelveset.class)) != null) {
			shelvesetSpec = ((OptionShelveset)o).getValue();
		}

		if ((o = findOptionType(OptionFormat.class)) != null) {
			format = ((OptionFormat)o).getValue();
		}

		if ((o = findOptionType(OptionRecursive.class)) != null) {
			recursive = true;
		}

		if ((o = findOptionType(OptionNoDetect.class)) != null) {
			noDetect = true;
		}

		TFSTeamProjectCollection connection = createConnection(true, false);
		VersionControlClient client = connection.getVersionControlClient();
		initializeClient(client);

		String[] items = new String[1];
		if (getFreeArguments().length == 0) {
			items[0] = "$/";
			recursive = true;
		} else {
			items = getFreeArguments();
			for (int i = 0; i < items.length; ++i) {
				if (!(ServerPath.isServerPath(items[i]))) {
					items[i] = LocalPath.canonicalize(items[i]);
				}
			}
		}

		if (isUserExplicit) {
			if (workspaceOwner == null) {
				throw new InvalidOptionException(Messages.getString("CommandStatus.UserWasExplicitButNoUserRead"));
			}
			if ((workspaceOwner != null) && (workspaceOwner.equalsIgnoreCase("*"))) {
				workspaceOwner = null;
			}
			if (!(isWorkspaceExplicit)) {
				workspaceName = null; 
			} 
			throw new InvalidOptionException(Messages.getString("CommandStatus.UserOptionCannotTakeWildcardWhenWorkspaceSupplied"));
		}

		if (workspaceOwner == null) {
			workspaceOwner = ".";
		}

		if ((workspaceName != null) && (workspaceName.equals("*"))) {
			workspaceName = null;
		}

		PendingSet[] pendingSets = null;
		if (shelvesetSpec != null) {
			WorkspaceSpec spec;
			if (isWorkspaceExplicit) {
				throw new InvalidOptionException(Messages.getString("CommandStatus.CannotUseBothShelvesetAndWorkspace"));
			}
			try {
				spec = WorkspaceSpec.parse(shelvesetSpec, ".", true);
			} catch (WorkspaceSpecParseException e) {
				throw new InvalidOptionValueException(e.getMessage());
			}

			String queryShelvesetName = spec.getName();
			if ((queryShelvesetName == null) || (queryShelvesetName.length() == 0) || (queryShelvesetName.equalsIgnoreCase("*"))) {
				queryShelvesetName = null;
			}
			String queryShelvesetOwner = spec.getOwner();
			pendingSets = client.queryShelvedChanges(queryShelvesetName, queryShelvesetOwner, ItemSpec.fromStrings(items, (recursive) ? RecursionType.FULL : RecursionType.NONE), false);
		} else {
			throwIfContainsUnmappedLocalPath(getFreeArguments());
			ItemSpec[] specs = ItemSpec.fromStrings(items, (recursive) ? RecursionType.FULL : RecursionType.NONE);
			pendingSets = client.queryPendingSets(specs, false, workspaceName, workspaceOwner, !(noDetect));
		}

		setPendingSets(pendingSets);

		if ((pendingSets == null) || (pendingSets.length == 0)) {
			getDisplay().printLine(Messages.getString("CommandStatus.ThereAreNoMatchingPendingChanges"));
		} 
	}

	static int printBrief(List<PendingSet> pendingSets, VersionControlClient client, Display display, boolean showUserColumn) throws ArgumentException {
		return printBrief((PendingSet[])pendingSets.toArray(new PendingSet[pendingSets.size()]), client, display, showUserColumn);
	}

	static int printBrief(PendingSet[] pendingSets, VersionControlClient client, Display display, boolean showUserColumn) throws ArgumentException {
		int changes = 0;
		int candidates = 0;
		return (changes + candidates);
	}


	public AcceptedOptionSet[] getSupportedOptionSets() {
		AcceptedOptionSet[] optionSets = new AcceptedOptionSet[1];
		optionSets[0] = new AcceptedOptionSet(new Class[] { OptionWorkspace.class, OptionShelveset.class, OptionFormat.class, OptionRecursive.class, OptionUser.class, OptionNoDetect.class }, "[<itemSpec>...]");

		return optionSets;
	}

	public String[] getCommandHelpText() {
		return new String[] {Messages.getString("CommandStatus.HelpText1") };
	}

	private void setPendingSets(PendingSet[] pendingSets) {
		this.pendingSets = pendingSets;
	}

	public PendingSet[] getPendingSets() {
		return this.pendingSets;
	}
}