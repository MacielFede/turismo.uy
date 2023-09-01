package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dataTypes.DtProfessor;
import dataTypes.DtUser;
import entities.Class;
import entities.Member;
import entities.Professor;
import entities.User;
import repository.GenericRepository;

public class UserService {
	@PersistenceContext
	private EntityManager entityManager;
	private final GenericRepository<User> userRepository;

	public UserService(EntityManager entityManagers) {
		this.entityManager = entityManagers;
		this.userRepository = new GenericRepository<User>(entityManager, User.class);
	}

	public void addClassToProfessor(Class newClass, String professorNickname) {
		try {
			entityManager.getTransaction().begin();
			User professor = userRepository.findById(professorNickname, "nickname");

			// Dynamic casting user to professor
			if (professor instanceof Professor) {
				((Professor) professor).getClasses().put(newClass.getName(), newClass);
				// userRepository.save(professor); dont do this as it re-creates the class
			}
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public DtUser getUserByNickname(String nickname) {
		DtUser DtU = null;
		return DtU;
	}

	public DtUser getUserByEmail(String email) {
		DtUser DtU = null;
		return DtU;
	}

	public Map<String, DtUser> getAllUsers() {
		Map<String, DtUser> lDtU = new TreeMap<>();
		for (User u : userRepository.findAll()) {
			lDtU.put(u.getNickname(), u.getData());
		}
		entityManager.close();
		return lDtU;
	}

	public void updateUser(DtUser userUpdated) {
		try {
			entityManager.getTransaction().begin();
			User updatedUser = userRepository.findById(userUpdated.getNickname(), "nickname");
			updatedUser.setName(userUpdated.getName());
			updatedUser.setBornDate(userUpdated.getBornDate());
			updatedUser.setLastName(userUpdated.getLastName());
			userRepository.update(updatedUser);
			entityManager.getTransaction().commit();
			entityManager.close();
		} catch (Exception e) {
			entityManager.close();
		}
	}

	public String[] getAllEmails() {
		Map<String, DtUser> allUsers = this.getAllUsers();
		List<String> emails = new ArrayList<>();
		for (Map.Entry<String, DtUser> user : allUsers.entrySet()) {
			emails.add(user.getValue().getEmail());
		}
		return emails.toArray(new String[0]);
	}

	public void newUser(DtUser newUser) {
		entityManager.getTransaction().begin();

		if (newUser instanceof DtProfessor) {
			Professor newProfessor = new Professor(((DtProfessor) newUser).getDescription(),
					((DtProfessor) newUser).getBiography(), ((DtProfessor) newUser).getWebPage(), newUser.getNickname(),
					newUser.getName(), newUser.getLastName(), newUser.getEmail(), newUser.getBornDate());
			userRepository.save(newProfessor);
		} else {
			User newStudent = new Member(newUser.getNickname(), newUser.getName(), newUser.getLastName(),
					newUser.getEmail(), newUser.getBornDate());
			userRepository.save(newStudent);
		}
		entityManager.getTransaction().commit();

	}
}
