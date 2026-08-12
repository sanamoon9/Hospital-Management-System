package BusinessLogic;

public class Person {
    private String name;
    private String id;
    private String phoneNumber;

    public Person(String name,String id,String phoneNumber){
        this.name=name;
        this.id=id;
        this.phoneNumber=phoneNumber;
    }
    public Person(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getInfo(){
        return "Name:"+name+" " +"Id:"+id+" "+"Phonenumber:"+phoneNumber;
    }

}
