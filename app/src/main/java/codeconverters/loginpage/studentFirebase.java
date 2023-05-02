package codeconverters.loginpage;

public class studentFirebase {
    private String studId;
    private String studName;
    private String email;
    private String contactNum;
    private String password;

    public studentFirebase() {
    }

    public studentFirebase(String studId, String studName, String email, String contactNum, String password) {
        this.studId = studId;
        this.studName = studName;
        this.email = email;
        this.contactNum = contactNum;
        this.password = password;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    @Override
    public String toString() {
        return "studentFirebase{" +
                "studId='" + studId + '\'' +
                ", studName='" + studName + '\'' +
                ", email='" + email + '\'' +
                ", contactNum='" + contactNum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

