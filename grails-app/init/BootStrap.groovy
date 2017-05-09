import org.example.SecRole
import org.example.SecUser
import org.example.SecUserSecRole

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
//
//        def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
//        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
//
//        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
//                username: 'admin',
//                password: springSecurityService.encodePassword('admin'),
//                enabled: true).save(failOnError: true)
//
//        if (!adminUser.authorities.contains(adminRole)) {
//            SecUserSecRole.create adminUser, adminRole
//        }

        def adminRole = new SecRole(authority: 'ROLE_ADMIN').save()
        def roleEmployee = new SecRole(authority: 'ROLE_EMPLOYEE').save()
        def roleManager = new SecRole(authority: 'ROLE_MANAGER').save()
        def roleHr = new SecRole(authority: 'ROLE_HR').save()

        def admin = new SecUser(username: 'admin', password: 'admin').save()
        def employee1 = new SecUser(username: 'employee1', password: 'employee1').save()
        def employee2 = new SecUser(username: 'employee2', password: 'employee2').save()

        def manager1 = new SecUser(username: 'manager1', password: 'manager1').save()
        def manager2 = new SecUser(username: 'manager2', password: 'manager2').save()

        def hr1 = new SecUser(username: 'hr1', password: 'hr1').save()
        def hr2 = new SecUser(username: 'hr2', password: 'hr2').save()

        SecUserSecRole.create admin, adminRole

        SecUserSecRole.create employee1, roleEmployee
        SecUserSecRole.create employee2, roleEmployee

        SecUserSecRole.create manager1, roleEmployee
        SecUserSecRole.create manager1, roleManager
        SecUserSecRole.create manager2, roleEmployee
        SecUserSecRole.create manager2, roleManager

        SecUserSecRole.create hr1, roleEmployee
        SecUserSecRole.create hr1, roleHr
        SecUserSecRole.create hr2, roleEmployee
        SecUserSecRole.create hr2, roleHr



        SecUserSecRole.withSession {
            it.flush()
            it.clear()
        }

//        assert SecUser.count() == 1
//        assert SecRole.count() == 2
//        assert SecUserSecRole.count() == 1

    }
    def destroy = {
    }
}
