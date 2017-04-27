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
        def userRole = new SecRole(authority: 'ROLE_USER').save()

        def testUser = new SecUser(username: 'me', password: 'password').save()
        def testUser2 = new SecUser(username: 'take', password: '12345678').save()

        SecUserSecRole.create testUser, adminRole
        SecUserSecRole.create testUser2, adminRole

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
