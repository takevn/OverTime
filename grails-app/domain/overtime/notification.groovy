package overtime

class Notification {

    String message
    String targetUser
    String status
    Date dateCreated
    Date lastUpdated

    static constraints = {
        message nullable: true
        targetUser nullable: true
        status nullable: true
    }
}
