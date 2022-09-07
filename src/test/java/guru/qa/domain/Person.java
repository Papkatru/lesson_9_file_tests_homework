package guru.qa.domain;

public class Person {

    private String[] accounts;
    private String description;
    private String displayName;
    private String email;
    private String firstName;
    private String lastName;
    private Work work;

    public String[] getAccounts() {
        return accounts;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Work getWork() {
        return work;
    }

    public static class Work {
        private String office;
        private String telephone;

        public String getOffice() {
            return office;
        }

        public String getTelephone() {
            return telephone;
        }
    }
}
