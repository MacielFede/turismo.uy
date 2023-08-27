package controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import dataTypes.DtActivity;
import dataTypes.DtClass;
import dataTypes.DtInstitute;
import dataTypes.DtUser;
import interfaces.InstituteInterface;
import services.ActivityService;
import services.InstituteService;
import services.ServiceFactory;

public class InstituteController implements InstituteInterface {
	private ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private Map<String, DtInstitute> institutesCache 	= null;
	private Map<String, DtActivity> activitiesCache		= null;
	private Map<String, DtClass> classesCache			= null;

	public InstituteController() {
		super();
	}
	
	@Override
	public Map<String, DtInstitute> listSportInstitutes() {
		if(institutesCache == null) {
			listSportInstitutesCache();
		}
		return institutesCache;
	}

	public boolean checkActivityAvialability(String name) {
		ActivityService activityService = serviceFactory.getActivityService();
		return activityService.checkActivityAvialability(name);
	}

	@Override
	public Map<String, DtActivity> selectInstitution(String institutionName){
		if(institutesCache == null) {
			listSportInstitutesCache();
		}
		activitiesCache = institutesCache.get(institutionName).getActivities();
		return activitiesCache;
	}

	@Override
	public Map<String, DtClass> chooseActivity(String activity) {
		if(activitiesCache == null) {
			listSportInstitutesCache();
		}
		for(DtInstitute i : institutesCache.values()) {
			for(DtActivity a : i.getActivities().values()) {
				if(a.getName().equals(activity)) {
					classesCache = a.getClasses();
					return classesCache;
				}
			}
		}
		return null;
	}

	@Override
	public DtClass chooseClassByName(String className) {
		if(classesCache !=null) {
			return classesCache.get(className);
		}
		else {
			return null;
		}
	}

	@Override
	public List<DtActivity> listSportsActivitiesRanking() {
		ActivityService activityService = serviceFactory.getActivityService();
		List<DtActivity> activities = new ArrayList<DtActivity>();
		for(DtActivity dt : activityService.getAllActivity().values()) {
			activities.add(dt);
		}
		activities.sort(Comparator.comparingInt(DtActivity::getClassesQuantity).reversed());
		return activities;
	}

	@Override
	public void addNewSportActivity(DtActivity sportActivity, String nameInstitute) {
		InstituteService instituteService = serviceFactory.getInstituteService();
		instituteService.addActivityAtInstitute(nameInstitute, sportActivity);
	}

	@Override
	public boolean modiFySportInstitute(String desc, String url) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerInstitution(String name, String description, String url) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DtActivity getActivity(String activityName) {

		return null;
	}

	@Override
	public void updateActivityInfo(DtActivity dtA){

	}

	@Override
	public DtInstitute chooseSportInstitute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean registerUserToClass(DtClass rclass, DtUser user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DtClass showClassInfo(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, DtClass> listClassesDictationRanking() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createSportClass(DtClass newClass, Integer idSportActivity) {
		// TODO Auto-generated method stub
		return false;
	}

	private void listSportInstitutesCache() {
		InstituteService instituteService = serviceFactory.getInstituteService();
		institutesCache = instituteService.getAllInstitutes();
	}

	@Override
	public String[] listSportActivitiesByName() {
		// Returns an array with all the activity names and the string "<Nombres>"
		// Also renovates the cached map
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		ActivityService activityService = serviceFactory.getActivityService();
		activitiesCache = activityService.getAllActivity();
		List<String> names = new ArrayList<>();
		names.add("<Nombres>");
		for(Map.Entry<String, DtActivity> activity : cachedActivities.entrySet()){
			names.add(activity.getKey());
		}
		return names.toArray(new String[0]);
	}

}
