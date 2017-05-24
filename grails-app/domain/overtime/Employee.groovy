package overtime

import org.example.SecUser

class Employee {

    String firstName
    String lastName
    String graduateSchool
    String filePathProfilePicture
    String birthDate
    String totalPaidLeave
    String remainPaidLeave
    SecUser secUser

    static constraints = {
        firstName blank: true
        lastName blank: true
        graduateSchool blank: true
        birthDate nullable: true
        totalPaidLeave nullable: true
        remainPaidLeave nullable: true
        filePathProfilePicture nullable: true
        firstName nullable: true
        lastName nullable: true
        graduateSchool nullable: true
        secUser nullable: true
    }
}
