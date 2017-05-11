package book

import bip.ot.OverTime
import grails.transaction.Transactional
import org.example.SecUser
import overtime.Notification

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
}
