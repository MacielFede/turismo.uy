package repository;

import java.io.Serializable;
import java.util.Objects;

// This class is only used to implement the table that contains both ids and the Enrollment attributes
public class EnrollmentId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String aClass;
    private String user;

    public EnrollmentId() {
    }
    public EnrollmentId(String aClass, String user) {
        this.aClass = aClass;
        this.user = user;
    }

    public String getaClass() {
        return aClass;
    }

    public void setaClass(String aClass) {
        this.aClass = aClass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(aClass, that.aClass) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aClass, user);
    }
}
