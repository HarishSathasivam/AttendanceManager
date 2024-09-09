package com.nusyn.license.util;

public class ColumnNameUtil {
	
	public static String getSortColumnNameForContent(int columnPosition)
	{
		String sortColumnName = "asset.id";
		switch (columnPosition) {
		case 0:  sortColumnName = "asset.id";
        		 break;
        case 1:  sortColumnName = "asset.name";
                 break;
        case 2:  sortColumnName = "asset.type";
                 break;
        case 3:  sortColumnName = "asset.accessGroup";
        		break;
        case 4:  sortColumnName = "asset.modifiedDate";
        		break;
        default: sortColumnName = "asset.id";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForUser(int columnPosition)
	{
		String sortColumnName = "organization.name";
		switch (columnPosition) {
		case 0:  sortColumnName = "name";
		 		 break;
		case 1:  sortColumnName = "name";
        		 break;
        case 2:  sortColumnName = "date";
                 break;
        case 4:  sortColumnName = "date";
        		break;
        default: sortColumnName = "date";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForDisplayInventory(int columnPosition)
	{
		String sortColumnName = "inventory.fileName";
		switch (columnPosition) {
		case 0:  sortColumnName = "inventory.fileName";
        		 break;
        case 1:  sortColumnName = "inventory.status";
                 break;
        case 2:  sortColumnName = "inventory.required";
        		break;
        default: sortColumnName = "inventory.fileName";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForDisplayLog(int columnPosition)
	{
		String sortColumnName = "log.timestamp";
		switch (columnPosition) {
		case 0:  sortColumnName = "log.timestamp";
        		 break;
        case 1:  sortColumnName = "log.level";
                 break;
        default: sortColumnName = "log.timestamp";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForMediaPlan(int columnPosition)
	{
		String sortColumnName = "mediaPlan.modifiedDate";
		switch (columnPosition) {
		case 0:  sortColumnName = "mediaPlan.modifiedDate";
		 		 break;
		case 1:  sortColumnName = "mediaPlan.name";
        		 break;
        case 2:  sortColumnName = "mediaPlan.planStrategy";
                 break;
        case 3:  sortColumnName = "mediaPlan.state";
                 break;
        case 4:  sortColumnName = "mediaPlan.buildStrategy";
        		break;
        default: sortColumnName = "mediaPlan.modifiedDate";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForRemoteTask(int columnPosition)
	{
		String sortColumnName = "remoteTask.createdDate";
		switch (columnPosition) {
		case 0:  sortColumnName = "remoteTask.createdDate";
		 		 break;
		case 1:  sortColumnName = "remoteTask.name";
        		 break;
        case 2:  sortColumnName = "remoteTask.scheduleType";
                 break;
        default: sortColumnName = "remoteTask.createdDate";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForRemoteTaskForDisplay(int columnPosition)
	{
		String sortColumnName = "remoteTask.createdDate";
		switch (columnPosition) {
		case 0:  sortColumnName = "remoteTask.name";
		 		 break;
		case 1:  sortColumnName = "remoteTask.createdDate";
        		 break;
        case 2:  sortColumnName = "remoteTask.scheduleType";
                 break;
        case 3:  sortColumnName = "assignments.status";
        		break;
        default: sortColumnName = "remoteTask.createdDate";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForPlaybackAuditReport(int columnPosition)
	{
		String sortColumnName = "playbackAuditReport.generatedDate";
		switch (columnPosition) {
		case 0:  sortColumnName = "playbackAuditReport.name";
		 		 break;
		case 1:  sortColumnName = "playbackAuditReport.generatedDate";
        		 break;
        case 2:  sortColumnName = "playbackAuditReport.startDateTime";
                 break;
        case 3:  sortColumnName = "playbackAuditReport.endDateTime";
                 break;
        default: sortColumnName = "playbackAuditReport.generatedDate";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForStationServer(int columnPosition)
	{
		String sortColumnName = "stationServer.name";
		switch (columnPosition) {
		case 0:  sortColumnName = "stationServer.name";
        		 break;
        case 1:  sortColumnName = "stationServer.station.name";
                 break;
        default: sortColumnName = "stationServer.name";
                 break;
		}
		return sortColumnName;
	}
	
	public static String getSortColumnNameForProject(int columnPosition)
	{
		String sortColumnName = "organization.name";
		switch (columnPosition) {
		case 0:  sortColumnName = "name";
		 		 break;
		case 1:  sortColumnName = "endCustomerName";
        		 break;
        case 2:  sortColumnName = "systemIntegratorName";
                 break;
        case 4:  sortColumnName = "Location";
        		break;
        default: sortColumnName = "name";
                 break;
		}
		return sortColumnName;
	}
	
	
	public static String getSortColumnNameForProjectGroup(int columnPosition)
	{
		String sortColumnName = "projectGroup.name";
		switch (columnPosition) {
		case 0:  sortColumnName = "name";
		 		 break;
		case 1:  sortColumnName = "name";
        		 break;
        case 2:  sortColumnName = "name";
                 break;
        case 4:  sortColumnName = "name";
        		break;
        default: sortColumnName = "name";
                 break;
		}
		return sortColumnName;
	}
}
