package poms;

public class Record extends BaseDTO {


    public String id;
    public String first_name;
    public String last_name;
    public String bitcoin_address;
    public String email;
    public String gender;
    public String ip_address;
    public String card_number;
    public String card_balance;
    public String card_currency;
    public String organization;
    public String full_name;
    public String employee_id;
    public String username;
    public String created_account_data;


    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", bitcoin_address='" + bitcoin_address + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", card_number='" + card_number + '\'' +
                ", card_balance='" + card_balance + '\'' +
                ", card_currency='" + card_currency + '\'' +
                ", organization='" + organization + '\'' +
                ", full_name='" + full_name + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", username='" + username + '\'' +
                ", created_account_data='" + created_account_data + '\'' +
                '}';
    }

    public String getCreated_account_data() {
        return created_account_data;
    }

    public void setCreated_account_data(String created_account_data) {
        this.created_account_data = created_account_data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_balance() {
        return card_balance;
    }

    public void setCard_balance(String card_balance) {
        this.card_balance = card_balance;
    }

    public String getCard_currency() {
        return card_currency;
    }

    public void setCard_currency(String card_currency) {
        this.card_currency = card_currency;
    }
    //    @Override
//    public String toString() {
//        return "Record{" +
//                "id=" + id +
//                ", first_name='" + first_name + '\'' +
//                ", last_name='" + last_name + '\'' +
//                ", bitcoin_address='" + bitcoin_address + '\'' +
//                ", email='" + email + '\'' +
//                ", gender='" + gender + '\'' +
//                ", ip_address='" + ip_address + '\'' +
//                ", msg='" + msg + '\'' +
//                ", link=" + link +
//                ", data='" + data + '\'' +
//                ", mime_type='" + mime_type + '\'' +
//                '}';
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBitcoin_address() {
        return bitcoin_address;
    }

    public void setBitcoin_address(String bitcoin_address) {
        this.bitcoin_address = bitcoin_address;
    }
}
