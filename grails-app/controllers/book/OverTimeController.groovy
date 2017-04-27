package book

import grails.plugin.springsecurity.annotation.Secured

class OverTimeController {

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index() {
        render "Manh dai ca"
    }
}
