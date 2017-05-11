package book

import bip.ot.OverTime
import grails.transaction.Transactional
import org.example.SecUser
import overtime.Notification
import overtime.OvertimeMaster

@Transactional
class NotificationService {
    def springSecurityService
    def notificationInfo() {
        def currentUser = springSecurityService.currentUser
        def notificationUnread = Notification.createCriteria().list() {
            eq("status", "unread")
            eq("targetUser", currentUser.username)
            order("id", "desc")
        }

        def notificationUnreadList = Notification.createCriteria().list() {
            eq("status", "unread")
            eq("targetUser", currentUser.username)
            order("id", "desc")
            maxResults(10)
        }
        return [notificationUnread: notificationUnread.size(), notificationUnreadList: notificationUnreadList]
    }

    def getUnassignTaskOfHr() {
        def overTimeMasterList = OvertimeMaster.createCriteria().list() {
            eq("status", "300")
        }
        return overTimeMasterList.size()
    }

}
