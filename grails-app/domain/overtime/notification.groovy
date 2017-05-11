package overtime

class Notification {

    String message
    String targetUser
    String status
    Date dateCreated
    Date lastUpdated
    String masterId

    static constraints = {
        message nullable: true
        targetUser nullable: true
        status nullable: true
        masterId nullable: true
    }
}
