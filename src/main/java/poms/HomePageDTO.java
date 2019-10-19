package poms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class HomePageDTO extends BaseDTO {
    String register;



    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
