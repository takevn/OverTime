package overtime

class Employee {

    String firstName
    String lastName
    String graduateSchool
    String filePathProfilePicture
    Date birthDate



    static constraints = {
        firstName size: 5..15, blank: false
        lastName size: 5..15, blank: false
        graduateSchool size: 5..15
        birthDate max: new Date()
    }
}
