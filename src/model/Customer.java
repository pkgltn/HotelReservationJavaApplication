package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    private final String emailRegex="^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName,String email){
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email. Please fill with an appropriate e-mail.");
        }
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    String getFirstName(){
        return firstName;
    }
    String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public void setEmail(String email){
        this.email=email;
    }


    @Override
    public String toString() {
        return "First Name: "+ getFirstName() + " Last Name: " + getLastName() + " E-mail: " + getEmail();
    }
    @Override
    public int hashCode(){
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object o){
        if(this==o) {return true;}
        if(o==null||getClass()!=o.getClass()) {return false;}
        Customer c =(Customer)o;
        return email.equals(c.email);
    }
}
