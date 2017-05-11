import book.NotificationService

// Place your Spring DSL code here
beans = {
    notificationService(NotificationService) {
        springSecurityService = ref("springSecurityService")
    }
}
